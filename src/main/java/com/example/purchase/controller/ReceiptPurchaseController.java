package com.example.purchase.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.purchase.model.ReceiptPurchase;
import com.example.purchase.repository.ReceiptPurchaseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/receiptPurchases", consumes = "application/json",
        produces = "application/json")
public class ReceiptPurchaseController {

    @Autowired
    private ReceiptPurchaseRepository receiptPurchaseRepository;

    @GetMapping("/{userId}")
    @ResponseBody
    public Flux<ReceiptPurchase> getReceiptPurchaseByUserId(ServerHttpRequest request,
            @PathVariable(value = "userId") String userId) {
        final List<String> vendors = request.getQueryParams().get("vendor");

        final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        final List<String> purchaseDate = request.getQueryParams().get("purchaseDate");

        final List<String> sku = request.getQueryParams().get("sku");

        return receiptPurchaseRepository.findByUserId(userId);

        // .filter(purchase -> {
        // if (!vendors.isEmpty() && !purchase.getVendor().equalsIgnoreCase(vendors.get(0))) {
        // return false;
        // }
        //
        // try {
        // if (!purchaseDate.isEmpty()
        // && !purchase.getPurchaseData().equals(sdf.parse(purchaseDate.get(0)))) {
        // return false;
        // }
        // } catch (ParseException e) {
        // }
        //
        // if (!sku.isEmpty() && !purchase.getPotentialPurchaseId().contains(sku.get(0))) {
        // return false;
        // }
        //
        // return true;
        // });
    }

    @PostMapping("/{userId}")
    @ResponseBody
    public Mono<ReceiptPurchase> createReceiptPurchase(
            @PathVariable(value = "userId") String userId, ServerHttpRequest request,
            @RequestBody List<String> lineData) {

        final Optional<String> imageCdnId =
                request.getQueryParams().get("imageCdnId").stream().findFirst();
        final Optional<String> imageCdnShaHash =
                request.getQueryParams().get("imageCdnShaHash").stream().findFirst();
        final Optional<String> imageCdnUri =
                request.getQueryParams().get("imageCdnUri").stream().findFirst();

        if (!imageCdnId.isPresent() || !imageCdnShaHash.isPresent() || !imageCdnUri.isPresent()) {
            return Mono.empty();
        }

        ReceiptPurchase purchase = new ReceiptPurchase(userId, lineData, imageCdnId.get(),
                imageCdnShaHash.get(), imageCdnUri.get());

        purchase.setVendor("Best Buy");
        purchase.setScanDate(Calendar.getInstance().getTime());
        purchase.setPurchaseData(Calendar.getInstance().getTime());

        return receiptPurchaseRepository.save(purchase);
    }

}
