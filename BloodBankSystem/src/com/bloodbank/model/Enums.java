package com.bloodbank.model;

public class Enums {
    public enum BloodType {
        A_POS, A_NEG, B_POS, B_NEG, AB_POS, AB_NEG, O_POS, O_NEG
    }

    public enum BloodStatus {
        AVAILABLE, RESERVED, ISSUED, EXPIRED
    }

    public enum AppointmentStatus {
        SCHEDULED, CONFIRMED, CANCELLED, COMPLETED
    }
}
