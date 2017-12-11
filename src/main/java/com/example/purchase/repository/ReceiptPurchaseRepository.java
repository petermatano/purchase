package com.example.purchase.repository;

import com.example.purchase.model.ReceiptPurchase;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ReceiptPurchaseRepository extends ReactiveMongoRepository<ReceiptPurchase, String> {
    Flux<ReceiptPurchase> findByUserId(String userId);
}
