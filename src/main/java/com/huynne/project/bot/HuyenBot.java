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

@Service
public class HuyenBot extends BaseLongPollingBot {
    private final DynamicSchedulerService dynamicSchedulerService;  // an example dependency

    @Autowired
    private TeleChatRepository teleChatRepository;
    public static MessageSender messageSender;

    public HuyenBot(DynamicSchedulerService dynamicSchedulerService,
                    @Value("${telegram.bot.usernaem:bot1}") String username,
                    @Value("${telegram.bot.token:6613925616:AAH71rAnVMxn5VO1aUxuoG4WpHWSP7L4xG0}") String token)
    {
        super(username, token);
        this.dynamicSchedulerService = dynamicSchedulerService;
        messageSender = new MessageSender(token);
    }

    @Override
    protected void handleUserMsg(Message message) {
        super.handleUserMsg(message);
    }

    @Override
    protected void handleCommand(Message message) {
        super.handleCommand(message);
        String chatId = message.getChatId().toString();
        String command = message.getText().replaceAll("@.*", "");

        switch (command) {
            case "/id":
                sendTextMsg(chatId, "ID nhóm chat: " + chatId);
                break;
            case "/hello_huyen":
                sendTextMsg(chatId, "Chào Huyền");
                break;
            case "/init":
                initChat(message);
        }
    }

    public void startAllTasks() {
        dynamicSchedulerService.startAllTask();
    }

    public void initChat(Message message) {
        TeleChat teleChat = new TeleChat();
        teleChat.setChatId(message.getChatId().toString());
        teleChat.setName(message.getChat().getTitle());
        teleChat.setDescription(message.getChat().getDescription());
        teleChatRepository.save(teleChat);
//        dynamicSchedulerService.addTask(new ScheduleTask());
    }


    public Message sendTextMsg(String chatId, String textContent) {
        return messageSender.sendTextMsg(chatId, textContent);
    }

    public Message sendImageMsg(String chatId, Integer msgId, String caption, InputStream image, String fileName) {
        return messageSender.sendImageMsg(chatId, msgId, caption, image, fileName);
    }
}
