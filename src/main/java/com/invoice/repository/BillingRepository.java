package com.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invoice.entity.BillingHeader;

@Repository
public interface BillingRepository extends JpaRepository<BillingHeader, Integer> {

}
