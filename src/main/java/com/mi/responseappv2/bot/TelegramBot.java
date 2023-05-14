package com.mi.responseappv2.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
public class TelegramBot extends TelegramLongPollingBot {

    public TelegramBot() {
        super(System.getenv("BOT_TOKEN"));
    }

    @PostConstruct
    public void postConstruct() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                Message message = update.getMessage();
                if (message.getText().equals("/start")) {
                    execute(SendMessage.builder()
                            .chatId(message.getChatId())
                            .text("Будь ласка відправте наступний код: " + message.getChatId() + " менеджеру @antonmiju")
                            .build());
                }

                execute(SendMessage.builder()
                        .chatId(message.getChatId())
                        .text("Sorry, this bot doesn't receive messages, only send.")
                        .build());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_USERNAME");
    }
}
