package com.fortech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by iosifvarga on 14.07.2017.
 */
public interface HomeInterface {

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String home(HttpServletRequest request, HttpServletResponse response);

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser(HttpServletRequest request, HttpServletResponse response);
}
