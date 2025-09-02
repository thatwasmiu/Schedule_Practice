package com.huynne.project.bot;

import com.huynne.project.bot.base.BaseLongPollingBot;
import com.huynne.project.service.DynamicSchedulerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.List;

@Service
public class HuyenBot extends BaseLongPollingBot {
    private final DynamicSchedulerService dynamicSchedulerService;  // an example dependency

    public HuyenBot(DynamicSchedulerService dynamicSchedulerService,
                    @Value("${telegram.bot.usernaem:bot1}") String username,
                    @Value("${telegram.bot.token:6613925616:AAH71rAnVMxn5VO1aUxuoG4WpHWSP7L4xG0}") String token)
    {
        super(username, token);
        this.dynamicSchedulerService = dynamicSchedulerService;
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
                // Todo: Thêm tính năng cho bot
        }
    }


    public Message sendTextMsg(String chatId, String textContent) {
        SendMessage sm = SendMessage.builder()
                .chatId(chatId)
                .parseMode(ParseMode.HTML)
                .text(textContent).build();
        try {
            return execute(sm);  // hảm execute gửi tin
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Message sendImageMsg(String chatId, Integer msgId, String caption, InputStream image, String fileName) {
        InputFile photo = new InputFile(image, fileName);
        try {
            SendPhoto sp = SendPhoto.builder()
                    .chatId(chatId)
                    .caption(caption)
                    .replyToMessageId(msgId)
                    .parseMode(ParseMode.HTML)
                    .photo(photo).build();
            return execute(sp);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
