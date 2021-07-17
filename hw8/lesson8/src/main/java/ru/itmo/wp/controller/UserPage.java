package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user/{id}")
    public String getProfile(@PathVariable String id,
                             HttpSession httpSession) {
        long numericId;
        try {
            numericId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return "UserPage";
        }

        httpSession.setAttribute("userData", userService.findById(numericId));
        return "UserPage";
    }
}
