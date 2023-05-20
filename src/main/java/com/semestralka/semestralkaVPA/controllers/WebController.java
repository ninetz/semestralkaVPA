package com.semestralka.semestralkaVPA.controllers;

import com.semestralka.semestralkaVPA.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @Autowired
    FileRepository fileRepository;

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("filesModels", fileRepository.findAll());
        return "index";
    }
}