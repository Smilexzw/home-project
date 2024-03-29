package com.halfmoon.home.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: xuzhangwang
 */
@Controller
public class HomeController {

    @GetMapping(value = {"/", "/index"})
    public String indexPage() {
        return "index";
    }

    @GetMapping("/404")
    public String notFoundPage() {
        return "404";
    }

    @GetMapping("/403")
    public String accessError() {
        return "403";
    }

    @GetMapping("/500")
    public String internalError() {
        return "500";
    }

    @GetMapping("/common")
    private String commonPage() {
        return "common";
    }
}
