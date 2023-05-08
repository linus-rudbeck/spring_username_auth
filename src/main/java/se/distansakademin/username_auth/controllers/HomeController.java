package se.distansakademin.username_auth.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String getHomepage(Authentication auth, Model model){

        if (auth == null){
            model.addAttribute("loggedIn", false);
        }else{
            var username = auth.getName();

            model.addAttribute("loggedIn", true);
            model.addAttribute("username", username);
        }

        return "home";
    }
}
