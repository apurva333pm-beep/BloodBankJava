package com.bloodbank.model;

import java.time.LocalDate;
import com.bloodbank.model.Enums.BloodType;

public class Donor extends User {
    private BloodType bloodType;
    private LocalDate lastDonationDate;
    private boolean medicalEligibility;

    public Donor(String userId, String username, String passwordHash, String firstName, String lastName, BloodType bloodType) {
        super(userId, username, passwordHash, firstName, lastName);
        this.bloodType = bloodType;
        this.medicalEligibility = true;
    }

    public BloodType getBloodType() { return bloodType; }
    public LocalDate getLastDonationDate() { return lastDonationDate; }
    public boolean isMedicalEligibility() { return medicalEligibility; }

    public void setLastDonationDate(LocalDate d) { this.lastDonationDate = d; }
    public void setMedicalEligibility(boolean ok) { this.medicalEligibility = ok; }

    // scheduleAppointment will be handled via AppointmentService (see service)
}
