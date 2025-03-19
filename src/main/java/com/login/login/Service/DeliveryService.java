package com.login.login.Service;

import com.login.login.Model.Delivery;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DeliveryService {
    List<Delivery> getAllDeliveries();

    void addDelivery(Delivery delivery);

    Page<Delivery> getDelivery(int page, int pageSize);

    Page<Delivery> searchDelivery(String name, String number, int page, int pageSize);

    long getTotalDeliveryCount();

    void changePendingToInProgress(String address);

    boolean rosPost(String address, String status);

//    Page<Delivery> getDelivering(String address, String status);
//
//    Page<Delivery> serchDelivering(String status);
}
