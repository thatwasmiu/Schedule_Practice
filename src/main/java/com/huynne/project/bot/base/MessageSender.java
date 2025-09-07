package com.huynne.project.bot.base;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;

public class MessageSender extends DefaultAbsSender {

    public MessageSender(String botToken) {
        super(new DefaultBotOptions(), botToken);
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
