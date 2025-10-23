package com.bloodbank.model;

import com.bloodbank.model.Enums.BloodType;

public class Recipient extends User {
    private BloodType bloodTypeNeeded;

    public Recipient(String userId, String username, String passwordHash, String firstName, String lastName, BloodType need) {
        super(userId, username, passwordHash, firstName, lastName);
        this.bloodTypeNeeded = need;
    }

    public BloodType getBloodTypeNeeded() { return bloodTypeNeeded; }

    // searchBloodAvailability in service layer
}
