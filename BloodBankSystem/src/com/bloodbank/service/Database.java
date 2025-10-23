package com.bloodbank.service;

import java.util.*;
import com.bloodbank.model.*;
import com.bloodbank.model.Enums.*;

public class Database {
    private static Database instance;
    public Map<String, User> users = new HashMap<>();
    public Map<String, BloodUnit> bloodUnits = new HashMap<>();
    public Map<String, Appointment> appointments = new HashMap<>();

    private Database() {
        seedData();
    }

    public static synchronized Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    private void seedData() {
        // Add a sample donor, recipient, staff, admin
        Donor d = new Donor("u1", "donor1", "pass", "Alice", "Doe", BloodType.O_POS);
        Recipient r = new Recipient("u2", "recipient1", "pass", "Bob", "Lee", BloodType.A_POS);
        BloodBankStaff s = new BloodBankStaff("u3", "staff1", "pass", "Carol", "Smith", "s001", "Inventory");
        Administrator a = new Administrator("u4", "admin", "pass", "Admin", "User");

        users.put(d.getUserId(), d);
        users.put(r.getUserId(), r);
        users.put(s.getUserId(), s);
        users.put(a.getUserId(), a);

        // sample blood unit
        BloodUnit unit = new BloodUnit("b1", BloodType.O_POS, java.time.LocalDate.now().minusDays(2),
                java.time.LocalDate.now().plusDays(28), "loc1");
        bloodUnits.put(unit.getUnitId(), unit);
    }
}
