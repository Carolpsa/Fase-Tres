package br.com.fiap.fasetres.controller;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class HomeController implements ErrorController {

    @GetMapping("/")
    public String hello() {
        return "Hello, World!";
    }

    @RequestMapping("/error")
    public String error() {
        return "Error page";
    }
    
    @GetMapping("/login")
    public String login() {
        return "Login page";
    }

    @GetMapping ("/paciente")
    public String home() {
        return "Paciente page";
    }

    @GetMapping ("/medico")
    public String admin() {
        return "Medico page";
    }

    @GetMapping ("/enfermeiro")
    public String nurse() {
        return "Enfermeiro page";
    }
}
