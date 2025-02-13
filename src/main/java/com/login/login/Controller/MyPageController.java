package com.login.login.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    // 기본 마이페이지
    @GetMapping
    public String myPage() {
        return "mypage";
    }

    // 배송 목록 페이지
    @GetMapping("/deliver")
    public String deliver() {
        return "mypage/deliver";
    }

    // 배송 완료 목록 페이지
    @GetMapping("/deliver_completed")
    public String deliverCompleted() {
        return "mypage/deliver_completed";
    }

    // 정보 수정 페이지
    @GetMapping("/modify_info")
    public String modifyInfo() {
        return "mypage/modify_info";
    }

    // 새로운 택배 등록 페이지
    @GetMapping("/new_parcel")
    public String newParcel() {
        return "mypage/new_parcel";
    }

    // 알림 페이지
    @GetMapping("/notify")
    public String notifyPage() {
        return "mypage/notify";
    }

    // 회원 탈퇴 페이지
    @GetMapping("/withdrawal")
    public String withdrawal() {
        return "mypage/withdrawal";
    }
}
