package com.login.login.Repository;

import com.login.login.Model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Delivery findByTrackingNumber(String trackingNumber);

}
