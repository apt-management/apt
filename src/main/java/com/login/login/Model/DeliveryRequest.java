package com.login.login.Model;

public class DeliveryRequest {

    private String address;  // 배송 주소
    private String status;   // 배송 상태 (예: "배송 중", "배송 완료")

    // 기본 생성자
    public DeliveryRequest() {

    }

    // 매개변수 생성자
    public DeliveryRequest(String address, String status) {
        this.address = address;
        this.status = status;
    }

    // Getter와 Setter 메서드
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
