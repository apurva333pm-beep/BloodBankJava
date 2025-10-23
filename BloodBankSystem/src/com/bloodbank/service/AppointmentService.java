package com.bloodbank.service;

import com.bloodbank.model.*;
import com.bloodbank.model.Enums.*;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class AppointmentService {
    private Database db = Database.getInstance();

    public Appointment schedule(String donorId, String locationId, LocalDateTime dt) {
        String id = "a" + UUID.randomUUID().toString().substring(0,6);
        Appointment ap = new Appointment(id, donorId, locationId, dt);
        db.appointments.put(id, ap);
        return ap;
    }

    public List<Appointment> listForDonor(String donorId) {
        return db.appointments.values().stream()
                .filter(a -> a.getDonorId().equals(donorId))
                .collect(Collectors.toList());
    }

    public List<Appointment> listAll() {
        return new ArrayList<>(db.appointments.values());
    }

    public boolean cancel(String appointmentId) {
        Appointment a = db.appointments.get(appointmentId);
        if (a != null) {
            a.setStatus(AppointmentStatus.CANCELLED);
            return true;
        }
        return false;
    }
}
