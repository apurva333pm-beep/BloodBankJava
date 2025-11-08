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
        if (instance == null)
            instance = new Database();
        return instance;
    }

    private void seedData() {
        // Add a sample donor, recipient, staff, admin
        Donor d = new Donor("u1", "donor1", "pass", "Disha", "Gupta", BloodType.O_POS);
        Recipient r = new Recipient("u2", "recipient1", "pass", "Apurva", "Bhiungade", BloodType.A_POS);
        BloodBankStaff s = new BloodBankStaff("u3", "staff1", "pass", "Ramesh", "sharma", "s001", "Inventory");
        Administrator a = new Administrator("u4", "admin", "pass", "Admin", "User");

        users.put(d.getUserId(), d);
        users.put(r.getUserId(), r);
        users.put(s.getUserId(), s);
        users.put(a.getUserId(), a);

        // sample blood unit
        BloodUnit unit = new BloodUnit("b1", BloodType.O_POS, java.time.LocalDate.now().minusDays(2),
                java.time.LocalDate.now().plusDays(28), "loc1");
        bloodUnits.put(unit.getUnitId(), unit);

        // Add other blood types
        BloodUnit unit2 = new BloodUnit("b2", BloodType.A_POS, java.time.LocalDate.now().minusDays(5),
                java.time.LocalDate.now().plusDays(25), "loc1");
        bloodUnits.put(unit2.getUnitId(), unit2);

        BloodUnit unit3 = new BloodUnit("b3", BloodType.B_POS, java.time.LocalDate.now().minusDays(1),
                java.time.LocalDate.now().plusDays(29), "loc2");
        bloodUnits.put(unit3.getUnitId(), unit3);

        BloodUnit unit4 = new BloodUnit("b4", BloodType.AB_POS, java.time.LocalDate.now().minusDays(10),
                java.time.LocalDate.now().plusDays(20), "loc1");
        bloodUnits.put(unit4.getUnitId(), unit4);

        BloodUnit unit5 = new BloodUnit("b5", BloodType.O_NEG, java.time.LocalDate.now().minusDays(3),
                java.time.LocalDate.now().plusDays(27), "loc2");
        bloodUnits.put(unit5.getUnitId(), unit5);

        BloodUnit unit6 = new BloodUnit("b6", BloodType.A_NEG, java.time.LocalDate.now().minusDays(7),
                java.time.LocalDate.now().plusDays(23), "loc1");
        bloodUnits.put(unit6.getUnitId(), unit6);

        BloodUnit unit7 = new BloodUnit("b7", BloodType.B_NEG, java.time.LocalDate.now().minusDays(4),
                java.time.LocalDate.now().plusDays(26), "loc2");
        bloodUnits.put(unit7.getUnitId(), unit7);

        BloodUnit unit8 = new BloodUnit("b8", BloodType.AB_NEG, java.time.LocalDate.now().minusDays(8),
                java.time.LocalDate.now().plusDays(22), "loc1");
        bloodUnits.put(unit8.getUnitId(), unit8);
    }
}
