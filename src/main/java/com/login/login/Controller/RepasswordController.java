package com.login.login.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RepasswordController {
    @RequestMapping("/repassword")
    public String repassword(){
        return "repassword";
    }
}
