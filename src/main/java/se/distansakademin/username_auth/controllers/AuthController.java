package se.distansakademin.username_auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.distansakademin.username_auth.models.Registration;
import se.distansakademin.username_auth.models.User;
import se.distansakademin.username_auth.services.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/sign-in") // Show both sign in and sign up form
    public String signInSignUpForms(@RequestParam(required = false) String registered, Model model){

        if(registered != null){
            model.addAttribute("success", "User registered");

        }

        return "auth/sign-in-sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(Registration registration, Model model){

        if(!registration.passwordsMatch()){
            model.addAttribute("error", "Passwords don't match");
            return "auth/sign-in-sign-up";
        }

        var username = registration.getUsername();

        if(userService.usernameExists(username)){
            model.addAttribute("error", "username exists");
            return "auth/sign-in-sign-up";
        }

        var password = registration.getPassword();

        var user = new User(username,password);

        userService.saveUser(user);

        return "redirect:/sign-in?registered=1";
    }

}
