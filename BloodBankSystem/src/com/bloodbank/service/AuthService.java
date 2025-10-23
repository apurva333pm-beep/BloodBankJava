package com.bloodbank.service;

import com.bloodbank.model.*;
import java.util.UUID;

public class AuthService {
    private Database db = Database.getInstance();

    // Very simple "hash" (for prototype only)
    private String fakeHash(String password) { return password; }

    public User login(String username, String password) {
    for (User u : db.users.values()) {
        if (u.getUsername().equals(username) && u.getPasswordHash().equals(fakeHash(password))) {
            return u;
        }
    }
    return null;
}


    public User registerDonor(String username, String password, String firstName, String lastName, com.bloodbank.model.Enums.BloodType btype) {
        String id = "u" + UUID.randomUUID().toString().substring(0,6);
        Donor d = new Donor(id, username, fakeHash(password), firstName, lastName, btype);
        db.users.put(id, d);
        return d;
    }

    public User registerRecipient(String username, String password, String firstName, String lastName, com.bloodbank.model.Enums.BloodType btype) {
        String id = "u" + UUID.randomUUID().toString().substring(0,6);
        // Create a new Recipient object
        Recipient r = new Recipient(id, username, fakeHash(password), firstName, lastName, btype);
        db.users.put(id, r);
        return r;
    }
}
