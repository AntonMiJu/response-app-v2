package com.mi.responseappv2.controllers;

import com.mi.responseappv2.bot.TelegramResponseSender;
import com.mi.responseappv2.dao.GasStationRepository;
import com.mi.responseappv2.models.GasStation;
import com.mi.responseappv2.models.Response;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    private final GasStationRepository gasStationRepository;
    private final TelegramResponseSender telegramResponseSender;

    public ApiController(GasStationRepository gasStationRepository, TelegramResponseSender telegramResponseSender) {
        this.gasStationRepository = gasStationRepository;
        this.telegramResponseSender = telegramResponseSender;
    }

    @PostMapping("{id}/response")
    public void createResponse(HttpServletResponse servletResponse, @PathVariable String id, Response response)
            throws IOException {
        Optional<GasStation> optionalGasStation = gasStationRepository.findById(id);
        if (optionalGasStation.isPresent()) {
            response.setGasStation(optionalGasStation.get());
            telegramResponseSender.sendResponse(response);

            servletResponse.sendRedirect("/success");
        } else {
            servletResponse.sendRedirect("/error");
        }
    }
}
