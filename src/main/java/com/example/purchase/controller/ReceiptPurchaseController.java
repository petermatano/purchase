package com.example.purchase.controller;

import com.example.purchase.model.ReceiptPurchase;
import com.example.purchase.repository.ReceiptPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/receiptPurchases")
public class ReceiptPurchaseController {

    @Autowired
    private ReceiptPurchaseRepository receiptPurchaseRepository;

    @GetMapping
    public Flux<ReceiptPurchase> getAllReceiptPurchases() {
        return receiptPurchaseRepository.findAll();
    }

    @GetMapping("/{userId}")
    public Flux<ReceiptPurchase> getReceiptPurchaseByUserId(@PathVariable(value = "userId") String userId) {
        return receiptPurchaseRepository.findByUserId(userId);
    }

    @PostMapping
    public Mono<ReceiptPurchase> createReceiptPurchase(@RequestBody ReceiptPurchase receiptPurchase)  {
        return receiptPurchaseRepository.save(receiptPurchase);
    }

}
