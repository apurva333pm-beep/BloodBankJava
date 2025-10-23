package com.bloodbank.model;

import java.time.LocalDateTime;
import com.bloodbank.model.Enums.AppointmentStatus;

public class Appointment {
    private String appointmentId;
    private String donorId;
    private String locationId;
    private LocalDateTime dateTime;
    private AppointmentStatus status;

    public Appointment(String appointmentId, String donorId, String locationId, LocalDateTime dateTime) {
        this.appointmentId = appointmentId;
        this.donorId = donorId;
        this.locationId = locationId;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.SCHEDULED;
    }

    public String getAppointmentId() { return appointmentId; }
    public String getDonorId() { return donorId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus s) { this.status = s; }
}
