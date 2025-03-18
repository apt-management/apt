package com.login.login.Service;

import com.login.login.Model.Delivery;
import com.login.login.Repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Override
    public void addDelivery(Delivery delivery) {
        if (delivery.getNumber() == null || delivery.getTrackingNumber().isEmpty()) {
            throw new IllegalArgumentException("전화번호와 운송장 번호는 필수입니다.");
        }
        deliveryRepository.save(delivery);
    }

    @Override
    public Page<Delivery> getDelivery(int page, int pageSize) {
        return deliveryRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Page<Delivery> searchDelivery(String name, String number, int page, int pageSize) {
        return deliveryRepository.findByNameContainingAndNumberContaining(name, number, PageRequest.of(page, pageSize));
    }

    @Override
    public long getTotalDeliveryCount() {
        return deliveryRepository.count();
    }

    @Override
    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    @Transactional
    public void changePendingToInProgress(String address) {
        int updatedCount = deliveryRepository.rosPost(address);
        System.out.println("업데이트된 배송 건수: " + updatedCount);
    }

    public boolean rosPost(String address, String status) {
        List<Delivery> deliveries = deliveryRepository.findByAddress(address);
        if (!deliveries.isEmpty()) {
            for (Delivery delivery : deliveries) {
                delivery.setStatus(status);
                deliveryRepository.save(delivery);
            }
            System.out.println("Updated " + deliveries.size() + " deliveries status to: " + status);
            return true;
        }
        return false;
    }
}
