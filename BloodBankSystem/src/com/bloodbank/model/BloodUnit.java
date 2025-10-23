package com.bloodbank.model;

import java.time.LocalDate;
import com.bloodbank.model.Enums.BloodStatus;
import com.bloodbank.model.Enums.BloodType;

public class BloodUnit {
    private String unitId;
    private BloodType bloodType;
    private LocalDate collectionDate;
    private LocalDate expiryDate;
    private BloodStatus status;
    private String collectedAtLocationId;
    private String reservedByRecipientId;


    public BloodUnit(String unitId, BloodType type, LocalDate collectionDate, LocalDate expiryDate, String locationId) {
        this.unitId = unitId;
        this.bloodType = type;
        this.collectionDate = collectionDate;
        this.expiryDate = expiryDate;
        this.status = BloodStatus.AVAILABLE;
        this.collectedAtLocationId = locationId;
    }

    public String getUnitId() { return unitId; }
    public BloodType getBloodType() { return bloodType; }
    public BloodStatus getStatus() { return status; }
    public void setStatus(BloodStatus s) { this.status = s; }
    public boolean isExpired() { return LocalDate.now().isAfter(expiryDate); }

     public String getReservedByRecipientId() {
        return reservedByRecipientId;
    }

    public void setReservedByRecipientId(String reservedByRecipientId) {
        this.reservedByRecipientId = reservedByRecipientId;
    }


}

