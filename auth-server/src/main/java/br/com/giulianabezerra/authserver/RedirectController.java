package br.com.giulianabezerra.authserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class RedirectController {

    @GetMapping
    public String login() {
        return "redirect:http://127.0.0.1:3000/login"; // Redirecionar para a página de login do front-end
    }
}
