package com.bloodbank.model;

import java.time.LocalDate;

public class User {
    protected String userId;
    protected String username;
    protected String passwordHash;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String contactNumber;
    protected String address;
    protected LocalDate registrationDate;

    public User(String userId, String username, String passwordHash, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = LocalDate.now();
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getFullName() { return firstName + " " + lastName; }

    // updateProfile-like method
    public void updateProfile(String newAddress, String newContact) {
        this.address = newAddress;
        this.contactNumber = newContact;
    }

    public String getPasswordHash() {
    return passwordHash;
}

}
