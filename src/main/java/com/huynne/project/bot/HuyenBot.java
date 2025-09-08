package com.huynne.project.bot;

import com.huynne.project.bot.base.BaseLongPollingBot;
import com.huynne.project.bot.base.MessageSender;
import com.huynne.project.model.ScheduleTask;
import com.huynne.project.model.TeleChat;
import com.huynne.project.repository.TeleChatRepository;
import com.huynne.project.service.DynamicSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.InputStream;
import java.util.Optional;

@Service
public class HuyenBot extends BaseLongPollingBot {
    private final DynamicSchedulerService dynamicSchedulerService;  // an example dependency

    @Autowired
    private TeleChatRepository teleChatRepository;
    private MessageSender messageSender;

    public HuyenBot(DynamicSchedulerService dynamicSchedulerService,
                    @Value("${telegram.bot.usernaem:bot1}") String username,
                    @Value("${telegram.bot.token:6613925616:AAH71rAnVMxn5VO1aUxuoG4WpHWSP7L4xG0}") String token)
    {
        super(username, token);
        this.dynamicSchedulerService = dynamicSchedulerService;
        this.messageSender = new MessageSender(token);
    }

    @Override
    protected void handleUserMsg(Message message) {
        if (super.checkInValidTime(message.getDate())) return;
    }

    @Override
    protected void handleCommand(Message message) {
        if (super.checkInValidTime(message.getDate())) return;

        String chatId = message.getChatId().toString();
        String rawValue = message.getText().replaceAll("@.*", "");

        if (rawValue.isEmpty()) return;

        String[] lines = rawValue.split("\\r?\\n");

        String command = lines[0].trim();
        switch (command) {
            case "/id":
                sendTextMsg(chatId, "ID nhóm chat: " + chatId);
                break;
            case "/hello_huyen":
                sendTextMsg(chatId, "Chào Huyền");
                break;
            case "/init":
                initChat(message);
                break;
            case "/create_schedule":
                createScheduleTask(lines);
                break;
        }
    }

    private void createScheduleTask(String[] message) {
        if (message.length < 3) return;

        String cron = message[1].trim();
        if (!dynamicSchedulerService.isValidTime(cron)) return;

        ScheduleTask task = new ScheduleTask();
        task.setCronValue(cron);
        String name = message[2].trim();
        task.setName(name);
        if (message.length > 3) {
            Optional<TeleChat> chatOptional = teleChatRepository.findById(message[3].trim());
            if (chatOptional.isPresent())
                task.getChatList().add(chatOptional.get());
        }
        dynamicSchedulerService.addTask(task);
    }

    public void startAllTasks() {
        dynamicSchedulerService.startAllTask();
    }

    public void initChat(Message message) {
        TeleChat teleChat = new TeleChat();
        if (message.isUserMessage()) {
            teleChat.setChatId(message.getChatId().toString());
            teleChat.setName(message.getFrom().getUserName());
            teleChat.setDescription(message.getFrom().getFirstName());
        } else if (message.isGroupMessage()) {
            teleChat.setChatId(message.getChatId().toString());
            teleChat.setName(message.getChat().getTitle());
            teleChat.setDescription(message.getChat().getDescription());
        }
        teleChatRepository.save(teleChat);

//        dynamicSchedulerService.addTask(new ScheduleTask());
    }


    public Message sendTextMsg(String chatId, String textContent) {
        return messageSender.sendTextMsg(chatId, textContent);
    }

    public Message sendImageMsg(String chatId, Integer msgId, String caption, InputStream image, String fileName) {
        return messageSender.sendImageMsg(chatId, msgId, caption, image, fileName);
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }
}
