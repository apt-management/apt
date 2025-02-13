package com.login.login.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    private String number;
    private String userid;
    private String password;
    private String name;
    private String address;
    
}
