package com.mi.responseappv2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MvcController {
    @GetMapping("{id}/form")
    public String getResponseForm(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "form";
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
