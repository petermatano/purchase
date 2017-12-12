package com.example.purchase.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import com.example.purchase.model.ReceiptPurchase;
import com.example.purchase.repository.ReceiptPurchaseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/receiptPurchases")
public class ReceiptPurchaseController {

    @Autowired
    private ReceiptPurchaseRepository receiptPurchaseRepository;

    @GetMapping("/{userId}")
    @ResponseBody
    public Flux<ReceiptPurchase> getReceiptPurchaseByUserId(ServerRequest request,
            @PathVariable(value = "userId") String userId) {
        final Optional<String> vendor = request.queryParam("vendor");

        final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        final Optional<Date> purchaseDate = request.queryParam("purchaseDate").flatMap(date -> {
            try {
                return Optional.ofNullable(sdf.parse(date));
            } catch (ParseException e) {
                return Optional.empty();
            }
        });

        final Optional<String> sku = request.queryParam("sku");

        return receiptPurchaseRepository.findByUserId(userId).filter(purchase -> {
            if (vendor.isPresent() && !purchase.getVendor().equalsIgnoreCase(vendor.get())) {
                return false;
            }

            if (purchaseDate.isPresent()
                    && !purchase.getPurchaseData().equals(purchaseDate.get())) {
                return false;
            }

            if (sku.isPresent() && !purchase.getPotentialPurchaseId().contains(sku.get())) {
                return false;
            }

            return true;
        });
    }

    @PostMapping("/{userId}")
    @ResponseBody
    public Mono<ReceiptPurchase> createReceiptPurchase(
            @PathVariable(value = "userId") String userId, ServerRequest request,
            @RequestBody ReceiptPurchase receiptPurchase) {

        final Optional<String> imageCdnId = request.queryParam("imageCdnId");
        final Optional<String> imageCdnShaHash = request.queryParam("imageCdnShaHash");
        final Optional<String> imageCdnUri = request.queryParam("imageCdnUri");

        if (!imageCdnId.isPresent() || !imageCdnShaHash.isPresent() || !imageCdnUri.isPresent()) {
            return Mono.empty();
        }

        ReceiptPurchase purchase = new ReceiptPurchase(userId, receiptPurchase.getLineData(),
                imageCdnId.get(), imageCdnShaHash.get(), imageCdnUri.get());

        return receiptPurchaseRepository.save(purchase);
    }

}
