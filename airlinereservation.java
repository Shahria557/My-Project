import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class airlinereservation extends JFrame {

    // -------- LOGIN --------
    JTextField userField;
    JPasswordField passField;

    // -------- FLIGHT DATA --------
    String[][] flights = {
            {"101", "Dhaka", "London", "5", "$750"},
            {"102", "Dhaka", "Dubai", "8", "$450"},
            {"103", "Dhaka", "Singapore", "6", "$550"}
    };

    String[] columns = {"Flight ID", "Source", "Destination", "Seats", "Price"};
    JTable table;
    DefaultTableModel model;

    int selectedRow;

    public airlinereservation() {
        loginPage();
    }

    // -------- LOGIN PAGE --------
    void loginPage() {
        setTitle("Login - Airline Reservation");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        userField = new JTextField();
        passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> checkLogin());

        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginBtn);

        add(panel);
        setVisible(true);
    }

    void checkLogin() {
        String u = userField.getText();
        String p = String.valueOf(passField.getPassword());

        if (u.equals("admin") && p.equals("1234")) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            getContentPane().removeAll();
            airlineGUI();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Login!");
        }
    }

    // -------- AIRLINE GUI --------
    void airlineGUI() {
        setTitle("Airline Reservation System");
        setSize(650, 400);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(flights, columns);
        table = new JTable(model);

        JScrollPane sp = new JScrollPane(table);

        JButton bookBtn = new JButton("Book Ticket");
        JButton logoutBtn = new JButton("Logout");

        bookBtn.addActionListener(e -> openPassengerForm());
        logoutBtn.addActionListener(e -> logout());

        JPanel panel = new JPanel();
        panel.add(bookBtn);
        panel.add(logoutBtn);

        add(sp, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    // -------- PASSENGER FORM --------
    void openPassengerForm() {
        selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a flight first!");
            return;
        }

        int seats = Integer.parseInt(model.getValueAt(selectedRow, 3).toString());
        if (seats <= 0) {
            JOptionPane.showMessageDialog(this, "No seats available!");
            return;
        }

        JDialog dialog = new JDialog(this, "Passenger Details", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField passportField = new JTextField();
        JTextField phoneField = new JTextField();

        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");

        ButtonGroup bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);

        JButton confirmBtn = new JButton("Confirm Booking");

        confirmBtn.addActionListener(e -> {
            if (nameField.getText().isEmpty() || ageField.getText().isEmpty()
                    || passportField.getText().isEmpty() || phoneField.getText().isEmpty()
                    || (!male.isSelected() && !female.isSelected())) {

                JOptionPane.showMessageDialog(dialog, "All fields are required!");
                return;
            }

            int seatsLeft = Integer.parseInt(model.getValueAt(selectedRow, 3).toString());
            model.setValueAt(seatsLeft - 1, selectedRow, 3);

            JOptionPane.showMessageDialog(dialog,
                    "Booking Confirmed!\n\nPassenger: " + nameField.getText() +
                            "\nAge: " + ageField.getText() +
                            "\nGender: " + (male.isSelected() ? "Male" : "Female") +
                            "\nPassport: " + passportField.getText() +
                            "\nPhone: " + phoneField.getText() +
                            "\nFlight ID: " + model.getValueAt(selectedRow, 0));

            dialog.dispose();
        });

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Gender:"));

        JPanel gPanel = new JPanel();
        gPanel.add(male);
        gPanel.add(female);
        panel.add(gPanel);

        panel.add(new JLabel("Passport No:"));
        panel.add(passportField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel());
        panel.add(confirmBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // -------- LOGOUT --------
    void logout() {
        getContentPane().removeAll();
        loginPage();
        revalidate();
        repaint();
    }

    // -------- MAIN --------
    public static void main(String[] args) {
        new airlinereservation();
    }
}
