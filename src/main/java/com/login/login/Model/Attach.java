package com.login.login.Model;

import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Date;

@Getter
public class Attach {

    @Id
    private int id;

    private String label; // AWS 에서 이미지 주소를 불러와 사진을 띄워 주도록 하려함

    private Date date;

}
