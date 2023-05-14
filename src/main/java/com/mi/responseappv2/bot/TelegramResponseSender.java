package com.mi.responseappv2.bot;

import com.mi.responseappv2.dao.DirectorRepository;
import com.mi.responseappv2.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j2
@Service
@RequiredArgsConstructor
public class TelegramResponseSender {
    private final TelegramBot telegramBot;
    private final DirectorRepository directorRepository;

    public void sendResponse(Response response) {
        try {
            telegramBot.execute(SendMessage.builder()
                    .chatId(response.getGasStation().getChatId())
                    .parseMode("HTML")
                    .text(responseToMessage(response))
                    .build());

            directorRepository.findAll().forEach(director -> {
                try {
                    telegramBot.execute(SendMessage.builder()
                            .chatId(response.getGasStation().getChatId())
                            .parseMode("HTML")
                            .text(responseToDirectMessage(response))
                            .build());
                } catch (TelegramApiException e) {
                    log.error(e);
                }
            });
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }

    private String responseToMessage(Response response) {
        StringBuilder builder = new StringBuilder();
        builder.append(!StringUtils.hasText(response.getName()) ? "" : (response.getName() + System.lineSeparator()));
        builder.append(!StringUtils.hasText(response.getPhone()) ? "" : (response.getPhone() + System.lineSeparator()));
        builder.append(response);
        return builder.toString();
    }

    private String responseToDirectMessage(Response response) {
        StringBuilder builder = new StringBuilder();
        builder.append("<b>").append(response.getGasStation().getAddress()).append("</b>").append(System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append(responseToMessage(response));
        return builder.toString();
    }
}
