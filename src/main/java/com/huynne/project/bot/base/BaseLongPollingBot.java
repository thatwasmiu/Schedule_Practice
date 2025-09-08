package com.huynne.project.bot.base;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class BaseLongPollingBot extends TelegramLongPollingBot {

    private final String username;

    private final String token;

    public BaseLongPollingBot(String username, String token) {
        super(token);
        this.username = username;
        this.token = token;
    }
    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (isButtonCallback(update))
        {
            handleButtonCallback(update.getCallbackQuery());
        }
        else if (isCommand(update))
        {
            handleCommand(update.getMessage());
        }
        else if (isTextMsg(update)) {
            handleUserMsg(update.getMessage());
        }
    }

    private boolean isTextMsg(Update update) {
        Message message = update.getMessage();
        return message != null && message.isUserMessage();
    }

    private boolean isCommand(Update update) {
        Message message = update.getMessage();
        return message != null && message.isCommand();
    }

    private boolean isButtonCallback(Update update) {
        Message message = update.getMessage();
        CallbackQuery buttonCallBack = update.getCallbackQuery();
        return buttonCallBack != null && message == null;
    }

    protected boolean checkInValidTime(Integer sentDate) {
        Long date = Long.valueOf(sentDate);
        return System.currentTimeMillis() - date*1000 > 123000;    // thời điểm gửi tin trong khoảng 2 phút từ thời điểm hiện tại
    }

    protected void handleButtonCallback(CallbackQuery cbq) {
        if (checkInValidTime(cbq.getMessage().getDate())) return;
    }

    protected void handleCommand(Message msg) {
        if (checkInValidTime(msg.getDate())) return;
    }

    protected void handleUserMsg(Message msg) {
        if (checkInValidTime(msg.getDate())) return;
    }

    public void initBotCommand(List<BotCommand> botCommands) {
        SetMyCommands setMyCommands = SetMyCommands.builder().commands(botCommands).build();
        try {
            execute(setMyCommands);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
