package com.semestralka.semestralkaVPA.controllers;

import com.semestralka.semestralkaVPA.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class WebController {
    @Autowired
    FileRepository fileRepository;

    @GetMapping("/home")
    public String getHome(Model model, UriComponentsBuilder uriComponentsBuilder) {
        model.addAttribute("templatePath", uriComponentsBuilder.path("/api/file/").build().toString());
        model.addAttribute("filesModels", fileRepository.findAll());
        return "index";
    }
}