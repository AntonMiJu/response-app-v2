package com.mi.responseappv2.bot;

import com.mi.responseappv2.dao.DirectorRepository;
import com.mi.responseappv2.dao.GasStationRepository;
import com.mi.responseappv2.models.Director;
import com.mi.responseappv2.models.GasStation;
import jakarta.annotation.PostConstruct;
import java.util.NoSuchElementException;
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
    private final DirectorRepository directorRepository;
    private final GasStationRepository gasStationRepository;

    public TelegramBot(DirectorRepository directorRepository, GasStationRepository gasStationRepository) {
        super(System.getenv("BOT_TOKEN"));
        this.directorRepository = directorRepository;
        this.gasStationRepository = gasStationRepository;
    }

    @PostConstruct
    public void postConstruct() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            try {
                if (message.getText().equals("/start")) {
                    sendSimpleMessage(message.getChatId(), "Будь ласка відправте наступний код: "
                            + message.getChatId() + " менеджеру @antonmiju");
                } else if (message.getText().startsWith("/admin") && directorRepository.existsById(message.getChatId())) {
                    String[] params = message.getText().split(" ");
                    if (params.length == 3 && params[1].equals("-d")) {
                        directorRepository.save(new Director(Long.valueOf(params[2])));
                        sendSimpleMessage(message.getChatId(), "Success");
                    } else if (params.length == 4 && params[1].equals("-g")) {
                        GasStation gasStation = gasStationRepository.findById(params[2]).orElseThrow();
                        gasStation.setChatId(Long.valueOf(params[3]));
                        gasStationRepository.save(gasStation);
                        sendSimpleMessage(message.getChatId(), "Success");
                    }
                } else {
                    sendSimpleMessage(message.getChatId(), "Вибачте, цей бот тільки відправляє відгуки.");
                }
            } catch (NoSuchElementException ex) {
                sendSimpleMessage(message.getChatId(), "АЗС з таким id не знайдено");
            }
        }
    }

    private void sendSimpleMessage(Long chatId, String text) {
        try {
            execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(text)
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_USERNAME");
    }
}
