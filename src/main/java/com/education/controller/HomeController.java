package com.education.controller;

import com.education.service.AbstractService;
import com.education.utils.Mail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RequestMapping("/")
@RestController
public class HomeController  {


    @GetMapping("/")
    public String Home() throws MessagingException {
        new Mail().sendMessage();
        return "Salom";
    }
}
