package com.login.login.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String number;
    private String address;
    private String trackingNumber;
    private String status;

    public Delivery() {}

    public Delivery(String name, String number, String address, String trackingNumber, String status) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.trackingNumber = trackingNumber;
        this.status = status;
    }
}
