package com.example.diplomskiBackend.repository;

import com.example.diplomskiBackend.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
