package com.example.purchase.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "receiptPurchases")
public class ReceiptPurchase {
    @Id
    private final String id = UUID.randomUUID().toString();
    private String userId;
    private List<String> lineData;
    private String vendor;
    private Date purchaseData;
    private Date scanDate;
    private List<String> potentialPurchaseId;
    private Map<String, String> actualPurchaseIdVsWarrantyIdMap;
    private String imageCdnId;
    private String imageCdnShaHash;
    private String imageCdnUri;
}
