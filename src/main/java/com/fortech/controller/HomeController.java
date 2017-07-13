package com.fortech.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by iosifvarga on 10.07.2017.
 */
@CrossOrigin
@RestController
public class HomeController {

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String home(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        new SecurityContextLogoutHandler().logout(request,response,authentication);

        return "Logout " + name;
    }

    @RequestMapping(value="/user",method = RequestMethod.GET)
    public String getUser(HttpServletRequest request, HttpServletResponse response)
    {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}