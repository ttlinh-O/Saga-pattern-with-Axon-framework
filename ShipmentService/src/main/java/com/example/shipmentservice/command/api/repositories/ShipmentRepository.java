package com.example.shipmentservice.command.api.repositories;

import com.example.shipmentservice.command.api.data.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {
}
