package com.bloodbank.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.bloodbank.service.AuthService;
import com.bloodbank.service.InventoryService;
import com.bloodbank.service.AppointmentService;
import com.bloodbank.model.*;
import com.bloodbank.model.Enums.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class MainFrame extends JFrame {
    private CardLayout cards = new CardLayout();
    private JPanel root = new JPanel(cards);

    private AuthService auth = new AuthService();
    private InventoryService inv = new InventoryService();
    private AppointmentService appt = new AppointmentService();

    private User loggedIn;

    public MainFrame() {
        super("Blood Bank System - Prototype");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        root.add(new LoginPanel(), "login");
        root.add(new DashboardPanel(), "dashboard");

        add(root);
        cards.show(root, "login");
    }

    // -------- Login panel --------
    class LoginPanel extends JPanel {
        public LoginPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(8,8,8,8);
            c.fill = GridBagConstraints.HORIZONTAL;
            JLabel userL = new JLabel("Username:");
            JLabel passL = new JLabel("Password:");
            JTextField userF = new JTextField(15);
            JPasswordField passF = new JPasswordField(15);
            JButton loginB = new JButton("Login");
            JButton regB = new JButton("Register as Donor (quick)");
            JButton btnRegisterRecipient = new JButton("Register as Recipient");

            c.gridx=0; c.gridy=0; add(userL,c);
            c.gridx=1; add(userF,c);
            c.gridx=0; c.gridy=1; add(passL,c);
            c.gridx=1; add(passF,c);
            c.gridy=2; c.gridx=0; add(loginB,c);
            c.gridx=1; add(regB,c);
            c.gridy = 4; // <-- ADD THESE
            add(btnRegisterRecipient, c);

            loginB.addActionListener(e -> {
                String u = userF.getText().trim();
                String p = new String(passF.getPassword());
                User user = auth.login(u, p);
                if (user != null) {
                    loggedIn = user;
                    JOptionPane.showMessageDialog(this, "Welcome " + user.getFullName());
                    cards.show(root, "dashboard");
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Login failed. Try 'donor1' or 'admin' (seeded). Passwords are 'pass'.");
                }
            });

            regB.addActionListener(e -> {
                String username = JOptionPane.showInputDialog(this, "username");
                String pwd = JOptionPane.showInputDialog(this, "password");
                String fname = JOptionPane.showInputDialog(this, "first name");
                String lname = JOptionPane.showInputDialog(this, "last name");
                Object[] opts = BloodType.values();
                BloodType type = (BloodType) JOptionPane.showInputDialog(this, "Choose blood type", "Blood type",
                        JOptionPane.PLAIN_MESSAGE, null, opts, opts[0]);
                if (username!=null) {
                    User d = auth.registerDonor(username, pwd, fname, lname, type);
                    JOptionPane.showMessageDialog(this, "Registered donor: " + d.getFullName());
                }
            });

            // ADD THIS ENTIRE BLOCK
btnRegisterRecipient.addActionListener(e -> {
    try {
        String user = JOptionPane.showInputDialog(this, "Enter username:");
        if (user == null || user.trim().isEmpty()) return;

        String pass = JOptionPane.showInputDialog(this, "Enter password:");
        if (pass == null || pass.trim().isEmpty()) return;

        String first = JOptionPane.showInputDialog(this, "Enter First Name:");
        String last = JOptionPane.showInputDialog(this, "Enter Last Name:");

        // Ask for blood type
        Object[] types = Enums.BloodType.values();
        Enums.BloodType selectedType = (Enums.BloodType) JOptionPane.showInputDialog(this,
                "Select blood type NEEDED:", "Recipient Registration",
                JOptionPane.PLAIN_MESSAGE, null, types, types[0]);

        if (selectedType == null) return; // User cancelled

        // Use the new method from AuthService.java
        User newUser = auth.registerRecipient(user, pass, first, last, selectedType);

        if (newUser != null) {
            JOptionPane.showMessageDialog(this, "Recipient registered successfully! You can now log in.");
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed.");
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
});
        }
    }

    // -------- Dashboard panel (role-aware) --------
    class DashboardPanel extends JPanel {
        JLabel header = new JLabel();
        JTextArea area = new JTextArea();
        JButton logout = new JButton("Logout");

        public DashboardPanel() {
            setLayout(new BorderLayout());
            JPanel top = new JPanel(new BorderLayout());
            top.add(header, BorderLayout.WEST);
            top.add(logout, BorderLayout.EAST);
            add(top, BorderLayout.NORTH);

            area.setEditable(false);
            add(new JScrollPane(area), BorderLayout.CENTER);

            JPanel controls = new JPanel();

            JButton btnSearchBlood = new JButton("Search Blood");
            JButton btnListUnits = new JButton("List All Units");
            JButton btnSchedule = new JButton("Schedule Appointment (tomorrow 10am)");
            controls.add(btnSearchBlood);
            controls.add(btnSchedule);
            controls.add(btnListUnits);
            JButton btnRequest = new JButton("Request Blood");
            controls.add(btnRequest);
            JButton btnAddUnit = new JButton("Add Blood Unit (Staff only)");
JButton btnIssueUnit = new JButton("Issue Reserved Unit (Staff only)");
JButton btnViewUsers = new JButton("View All Users (Admin only)");

controls.add(btnAddUnit);
controls.add(btnIssueUnit);
controls.add(btnViewUsers);


           




            add(controls, BorderLayout.SOUTH);

            logout.addActionListener(e -> {
                loggedIn = null;
                cards.show(root, "login");
            });

           btnSearchBlood.addActionListener(e -> {
                // Get all possible blood types from the Enum
                Object[] types = Enums.BloodType.values();
                
                // Show a dialog box to ask the user
                Enums.BloodType selectedType = (Enums.BloodType) JOptionPane.showInputDialog(this,
                        "Select blood type to search for:", "Search Blood",
                        JOptionPane.PLAIN_MESSAGE, null, types, types[0]);
    
                // If the user selected a type (didn't cancel)
                if (selectedType != null) {
                    // Use the *selected* type for the search
                    List<BloodUnit> list = inv.searchByType(selectedType);
                    area.setText("Search results for " + selectedType + ":\n");
                    
                    if (list.isEmpty()) {
                        area.append("No available units found.");
                    } else {
                        for (BloodUnit u : list) {
                            area.append(u.getUnitId() + " status:" + u.getStatus() + "\n");
                        }
                    }
                }
            });

            btnListUnits.addActionListener(e -> {
                List<BloodUnit> list = inv.listAll();
                area.setText("All units:\n");
                for (BloodUnit u : list) {
                    area.append(u.getUnitId() + " type:" + u.getBloodType() + " status:" + u.getStatus() + "\n");
                }
            });

            btnSchedule.addActionListener(e -> {
                if (!(loggedIn instanceof Donor)) {
                    JOptionPane.showMessageDialog(this, "Only donors can schedule appointments. Register/login as donor.");
                    return;
                }
                Donor d = (Donor) loggedIn;
                LocalDateTime dt = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);
                Appointment a = appt.schedule(d.getUserId(), "loc1", dt);
                area.setText("Scheduled appointment: " + a.getAppointmentId() + " at " + a.getDateTime());
            });

            btnRequest.addActionListener(e -> {
    if (!(loggedIn instanceof Recipient)) {
        JOptionPane.showMessageDialog(this, "Only recipients can request blood. Please login as a recipient.");
        return;
    }

    Recipient rec = (Recipient) loggedIn;
    List<BloodUnit> available = inv.searchByType(rec.getBloodTypeNeeded());

    if (available.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No available units of type " + rec.getBloodTypeNeeded());
        return;
    }

    BloodUnit unit = available.get(0); // pick first available for simplicity
    boolean ok = inv.reserveBlood(unit.getUnitId(), rec.getUserId());
    if (ok) {
        JOptionPane.showMessageDialog(this, "Blood unit " + unit.getUnitId() + " reserved for you!");
    } else {
        JOptionPane.showMessageDialog(this, "Could not reserve unit.");
    }
}); 

    // STAFF: Add a new blood unit
btnAddUnit.addActionListener(e -> {
    if (!(loggedIn instanceof BloodBankStaff)) {
        JOptionPane.showMessageDialog(this, "Only staff can add blood units.");
        return;
    }

    Object[] types = Enums.BloodType.values();
    Enums.BloodType type = (Enums.BloodType) JOptionPane.showInputDialog(this,
            "Select blood type:", "Add Unit", JOptionPane.PLAIN_MESSAGE, null, types, types[0]);

    if (type == null) return;

    String id = "b" + System.currentTimeMillis();
    BloodUnit newUnit = new BloodUnit(id, type,
            java.time.LocalDate.now(), java.time.LocalDate.now().plusDays(30), "loc1");

    inv.addBloodUnit(newUnit);
    JOptionPane.showMessageDialog(this, "Added unit: " + id + " (" + type + ")");
});


// STAFF: Issue a reserved blood unit
// STAFF: Issue a reserved blood unit
btnIssueUnit.addActionListener(e -> {
    if (!(loggedIn instanceof BloodBankStaff)) {
        JOptionPane.showMessageDialog(this, "Only staff can issue units.");
        return;
    }

    List<BloodUnit> reserved = inv.listAll().stream()
        .filter(u -> u.getStatus() == Enums.BloodStatus.RESERVED)
        .collect(Collectors.toList());

    if (reserved.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No reserved units to issue.");
        return;
    }

    // Create a list of strings for the dialog
    Object[] options = reserved.stream()
        .map(u -> u.getUnitId() + " (" + u.getBloodType() + ") for user " + u.getReservedByRecipientId())
        .toArray();

    // Show a dialog to let staff choose
    String selectedOption = (String) JOptionPane.showInputDialog(this,
            "Select reserved unit to issue:", "Issue Unit",
            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

    if (selectedOption != null) {
        // Get the unitId from the selected string (it's the first word)
        String unitId = selectedOption.split(" ")[0];
        
        // Try to issue the selected unit
        boolean ok = inv.markIssued(unitId);
        
        if (ok) {
            JOptionPane.showMessageDialog(this, "Issued blood unit " + unitId);
        } else {
            JOptionPane.showMessageDialog(this, "Could not issue unit " + unitId + ". (Check InventoryService logic)");
        }
    }
});
    // ADMIN: View all users
btnViewUsers.addActionListener(e -> {
    if (!(loggedIn instanceof Administrator)) {
        JOptionPane.showMessageDialog(this, "Only admin can view all users.");
        return;
    }

    StringBuilder sb = new StringBuilder("All Users:\n\n");
    for (User u : com.bloodbank.service.Database.getInstance().users.values()) {
        sb.append(u.getUserId()).append(" - ").append(u.getUsername())
          .append(" (").append(u.getClass().getSimpleName()).append(")\n");
    }

    area.setText(sb.toString());
});
   
// RECIPIENT: Request Blood
btnRequest.addActionListener(e -> {
    if (!(loggedIn instanceof Recipient)) {
        JOptionPane.showMessageDialog(this, "Only recipients can request blood. Please login as a recipient.");
        return;
    }

    Recipient rec = (Recipient) loggedIn;
    List<BloodUnit> available = inv.searchByType(rec.getBloodTypeNeeded());

    if (available.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No available units of type " + rec.getBloodTypeNeeded());
        return;
    }

    // pick the first available blood unit
    BloodUnit unit = available.get(0);
    boolean ok = inv.reserveBlood(unit.getUnitId(), rec.getUserId());

    if (ok) {
        JOptionPane.showMessageDialog(this, "Blood unit " + unit.getUnitId() + " reserved successfully!");
    } else {
        JOptionPane.showMessageDialog(this, "Could not reserve blood unit.");
    }
});


        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (loggedIn != null) {
                header.setText("Logged in: " + loggedIn.getFullName() + " (" + loggedIn.getClass().getSimpleName() + ")");
            } else {
                header.setText("Not logged in");
            }
        }
    }
}
