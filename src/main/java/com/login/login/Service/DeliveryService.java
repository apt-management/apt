package com.login.login.Service;

import com.login.login.Model.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeliveryService {
    List<Delivery> getAllDeliveries();

    Page<Delivery> getDeliveries(Pageable pageable);

    void addDelivery(Delivery delivery);
}
