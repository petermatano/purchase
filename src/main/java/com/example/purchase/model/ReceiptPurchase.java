package com.example.purchase.model;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@Document(collection = "receiptPurchases")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReceiptPurchase {
    @Id
    private String id;
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

    public ReceiptPurchase(String userId, List<String> lineData, String imageCdnId,
            String imageCdnShaHash, String imageCdnUri) {
        super();
        this.userId = userId;
        this.lineData = lineData;
        this.imageCdnId = imageCdnId;
        this.imageCdnShaHash = imageCdnShaHash;
        this.imageCdnUri = imageCdnUri;
    }
}
