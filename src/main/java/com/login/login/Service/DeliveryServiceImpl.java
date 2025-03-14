package com.login.login.Service;

import com.login.login.Model.Delivery;
import com.login.login.Repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Override
    public Page<Delivery> getDeliveries(Pageable pageable) {
        return deliveryRepository.findAll(pageable);
    }

    @Override
    public void addDelivery(Delivery delivery) {
        if (delivery.getNumber() == null || delivery.getTrackingNumber().isEmpty()) {
            throw new IllegalArgumentException("전화번호와 운송장 번호는 필수입니다.");
        }
        deliveryRepository.save(delivery);
    }

    @Override
    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }
}
