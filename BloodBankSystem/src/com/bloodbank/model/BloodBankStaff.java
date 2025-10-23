package com.bloodbank.model;

public class BloodBankStaff extends User {
    private String staffId;
    private String department;

    public BloodBankStaff(String userId, String username, String passwordHash, String firstName, String lastName, String staffId, String dept) {
        super(userId, username, passwordHash, firstName, lastName);
        this.staffId = staffId;
        this.department = dept;
    }

    public String getStaffId() { return staffId; }
    public String getDepartment() { return department; }

    // addBloodUnit etc. done by InventoryService
}
