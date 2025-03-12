package com.login.login.Service;

import com.login.login.Model.Delivery;
import com.login.login.Repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    // 택배 정보 추가
    public void addDelivery(Delivery delivery) {
        if (delivery.getNumber() == null || delivery.getTrackingNumber().isEmpty()) {
            throw new IllegalArgumentException("전화번호와 운송장 번호는 필수입니다.");
        }

        // DB에 저장
        deliveryRepository.save(delivery);
    }
}
