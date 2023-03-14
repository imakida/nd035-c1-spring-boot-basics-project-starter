package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;


@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping()
    public String getSignupPage(User user) {
        return "signup";
    }

    @PostMapping()
    public String signupNewUser(@ModelAttribute User user, Model model) {
        String signupErrorMessage = null;

        if (userService.getUserByUsername(user.getUsername()) != null){
            //record found with username
            signupErrorMessage = "Account with username " + "\"" + user.getUsername() + "\""
                    + " already exists.";

        }
        else {
            int rowsAdded = this.userService.createUser(user);
            if (rowsAdded < 0){
                signupErrorMessage = "An error occurred creating user account";
            }
            else{
                //Signup successful. Redirect to login page.
//                model.addAttribute("signupSuccess", true);
                return "redirect:/login?signupSuccessful";
            }
        }

        model.addAttribute("signupErrorMessage", signupErrorMessage);
        return "signup";
    }
}
