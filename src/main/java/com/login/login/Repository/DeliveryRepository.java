package com.login.login.Repository;

import com.login.login.Model.Delivery;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Delivery findByTrackingNumber(String trackingNumber);

    Page<Delivery> findByNameContainingAndNumberContaining(String name, String number, org.springframework.data.domain.Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Delivery d SET d.status = '배송 중' WHERE d.status = '배송 전' AND d.address = :address")
    int rosPost(@Param("address") String address);

    List<Delivery> findByAddress(String address);
}
