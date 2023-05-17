package com.mi.responseappv2.controllers;

import com.mi.responseappv2.bot.TelegramResponseSender;
import com.mi.responseappv2.dao.GasStationRepository;
import com.mi.responseappv2.models.GasStation;
import com.mi.responseappv2.models.Response;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MvcController {
    private final GasStationRepository gasStationRepository;
    private final TelegramResponseSender telegramResponseSender;

    public MvcController(GasStationRepository gasStationRepository, TelegramResponseSender telegramResponseSender) {
        this.gasStationRepository = gasStationRepository;
        this.telegramResponseSender = telegramResponseSender;
    }
    @GetMapping("{id}/form")
    public String getResponseForm(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("response", new Response());
        return "form";
    }

    @PostMapping("{id}/response")
    public void createResponse(HttpServletResponse servletResponse, @RequestParam String id, @ModelAttribute("response") Response responseDto)
            throws IOException {
        Optional<GasStation> optionalGasStation = gasStationRepository.findById(id);
        if (optionalGasStation.isPresent()) {
            Response response = new Response(responseDto.getName(), responseDto.getPhone(), responseDto.getResponse());
            response.setGasStation(optionalGasStation.get());
            telegramResponseSender.sendResponse(response);

            servletResponse.sendRedirect("/success");
        } else {
            servletResponse.sendRedirect("/error");
        }
    }

    @GetMapping("success")
    public String getSuccess() {
        return "success";
    }

    @GetMapping("error")
    public String getError() {
        return "error";
    }
}
