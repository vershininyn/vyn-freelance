package dev.projects.profsouz.opcuaclient.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Tag(name = "It is the controller for main page", description = "Main page")
    @GetMapping(value = "/")
    public ModelAndView getDefaultData() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("index");

        return mv;
    }

}
