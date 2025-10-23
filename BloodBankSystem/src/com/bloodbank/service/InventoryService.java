package com.bloodbank.service;

import com.bloodbank.model.*;
import com.bloodbank.model.Enums.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class InventoryService {
    private Database db = Database.getInstance();

    public List<BloodUnit> searchByType(BloodType type) {
        return db.bloodUnits.values().stream()
                .filter(u -> u.getBloodType() == type && u.getStatus() == BloodStatus.AVAILABLE && !u.isExpired())
                .collect(Collectors.toList());
    }

    public void addBloodUnit(BloodUnit unit) {
        db.bloodUnits.put(unit.getUnitId(), unit);
    }

    public boolean markIssued(String unitId) {
        BloodUnit u = db.bloodUnits.get(unitId);
        // This check is the fix: it now accepts RESERVED units
        if (u != null && u.getStatus() == BloodStatus.RESERVED) {
            u.setStatus(BloodStatus.ISSUED);
            return true;
        }
        return false;
    }

    public List<BloodUnit> listAll() {
        return new ArrayList<>(db.bloodUnits.values());
    }

    public boolean reserveBlood(String unitId, String recipientId) {
    BloodUnit unit = db.bloodUnits.get(unitId);
    if (unit != null && unit.getStatus() == BloodStatus.AVAILABLE) {
        unit.setStatus(BloodStatus.RESERVED);
        unit.setReservedByRecipientId(recipientId);
        return true;
    }
    return false;
}

    

}
