package com.mi.responseappv2.controllers;

import com.mi.responseappv2.bot.TelegramResponseSender;
import com.mi.responseappv2.dao.GasStationRepository;
import com.mi.responseappv2.models.GasStation;
import com.mi.responseappv2.models.Response;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MvcController {
    private final GasStationRepository gasStationRepository;
    private final TelegramResponseSender telegramResponseSender;

    @GetMapping("{id}/form")
    public String getResponseForm(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "form";
    }

    @PostMapping("{id}/response")
    public String createResponse(@PathVariable String id, Response response) {
        Optional<GasStation> optionalGasStation = gasStationRepository.findById(id);
        if (optionalGasStation.isPresent()) {
            response.setGasStation(optionalGasStation.get());
            telegramResponseSender.sendResponse(response);

            return "success";
        } else {
            return "error";
        }
    }
}
