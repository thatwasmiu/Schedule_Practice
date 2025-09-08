package com.huynne.project.bot;

import com.huynne.project.bot.base.MessageSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotRegister {
    private final HuyenBot huyenBot;

    private BotSession botSession;

    public static MessageSender messageSender;

    public BotRegister(HuyenBot huyenBot) {
        this.huyenBot = huyenBot;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void registerBot(){
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botSession = botsApi.registerBot(huyenBot);
            messageSender = huyenBot.getMessageSender();
            huyenBot.initBotCommand(BotConstants.COMMAND_LIST);
            huyenBot.startAllTasks();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public HuyenBot getHuyenBot() {
        return huyenBot;
    }

    public BotSession getBotSession() {
        return botSession;
    }
}
