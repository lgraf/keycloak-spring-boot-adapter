package org.lgraf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
class HomeController {
    @GetMapping( "/" )
    String root() {
        return "redirect:/home";
    }


    @GetMapping( "/home" )
    String home() {
        return "home";
    }


    @GetMapping( "/logout" )
    public String logout( HttpServletRequest req ) throws ServletException {
        req.logout();
        return "redirect:/";
    }
}
