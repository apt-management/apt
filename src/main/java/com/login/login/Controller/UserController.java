package com.login.login.Controller;

import com.login.login.Model.User;
import com.login.login.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("number") String number,
            @RequestParam("password") String password,
            HttpSession session,
            Model model
    ) {
        User user = service.findByNumber(number);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            model.addAttribute("loginError", "전화번호 또는 비밀번호가 잘못되었습니다.");
            return "login";
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestParam("number") String number,
                         @RequestParam("userid") String userid,
                         @RequestParam("password") String password,
                         @RequestParam("name") String name,
                         @RequestParam("address") String address,
                         Model model) {
        User user = new User();
        user.setNumber(number);
        user.setUserid(userid);
        user.setPassword(password);
        user.setName(name);
        user.setAddress(address);

        boolean success = service.signup(user);

        if (success) {
            return "redirect:/login";
        } else {
            model.addAttribute("signupError", "이미 등록된 휴대폰 번호입니다.");
            return "signup";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("number") String number,
                             @RequestParam("userid") String userid,
                             @RequestParam("password") String password,
                             HttpSession session, Model model
    ) {
        User user = service.findByNumber(number);
        if (user != null && user.getNumber().equals(number) && user.getUserid().equals(userid) && user.getPassword().equals(password)) {
            service.deleteUser(number);
            session.invalidate();
            return "redirect:/";
        } else {
            model.addAttribute("withdrawalError", "사용자 정보가 일치하지 않습니다.");
            return "mypage/withdrawal";
        }
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user,HttpSession session, Model model) {
        String number = user.getNumber();
        if (number == null) {
            return "redirect:/";
        }
        user.setPassword(user.getPassword());
        user.setName(user.getName());
        user.setAddress(user.getAddress());

        service.updateUser(user);
        session.invalidate();
        model.addAttribute("modifySuccess", "회원 정보 수정이 완료되었습니다. 다시 로그인 해 주세요.");
        return "login";
    }

}
