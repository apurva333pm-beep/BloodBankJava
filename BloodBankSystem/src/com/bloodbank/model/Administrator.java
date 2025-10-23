package com.bloodbank.model;

public class Administrator extends User {
    public Administrator(String userId, String username, String passwordHash, String firstName, String lastName) {
        super(userId, username, passwordHash, firstName, lastName);
    }

    // manageUserAccounts, configureSystemSettings would be service-level operations
}
