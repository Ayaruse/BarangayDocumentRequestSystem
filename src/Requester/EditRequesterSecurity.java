package Requester;

import Data.RequesterDetails;
import Data.UserDataSaver;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EditRequesterSecurity extends JPanel {

    private RequesterDetails loggedInUser;

    private JLabel nameLabel = new JLabel("Username");
    private JTextField nameField = new JTextField();
    private String username;

    private JLabel currentPassText = new JLabel("Current Password");
    private JPasswordField currentPassField = new JPasswordField();
    private String currentPassword;

    private JLabel passText = new JLabel("New Password");
    private JPasswordField passField = new JPasswordField();
    private String password;

    private JLabel confirmPassText = new JLabel("Confirm New Password");
    private JPasswordField confirmPassField = new JPasswordField();
    private String confirmPass;

    private JLabel must = new JLabel("Password must be at least 8 characters long.");
    private JLabel passWarn = new JLabel("Current password is incorrect.");
    private JLabel confirmWarn = new JLabel("Passwords do not match.");
    private JButton saveSecurity = new JButton("Save Changes");

    public EditRequesterSecurity(RequesterDetails account) {
        loggedInUser = account;

        setLayout(null);
        setPreferredSize(new Dimension(900, 500));
        setBackground(Color.decode("#fffea8"));

        JLabel title = new JLabel("Edit Security");
        title.setFont(new Font("Times New Roman", Font.BOLD, 25));
        title.setBounds(350, 20, 250, 35);

        nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        nameLabel.setBounds(250, 80, 300, 25);
        nameField.setBounds(250, 105, 400, 30);
        nameField.setEditable(false);

        currentPassText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        currentPassText.setBounds(250, 150, 300, 25);
        currentPassField.setBounds(250, 175, 400, 30);

        passText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        passText.setBounds(250, 220, 300, 25);
        passField.setBounds(250, 245, 400, 30);

        must.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        must.setBounds(250, 275, 400, 20);
        must.setForeground(Color.GRAY);

        confirmPassText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        confirmPassText.setBounds(250, 305, 300, 25);
        confirmPassField.setBounds(250, 330, 400, 30);

        passWarn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        passWarn.setBounds(250, 360, 400, 20);
        passWarn.setForeground(Color.RED);
        passWarn.setVisible(false);

        confirmWarn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        confirmWarn.setBounds(250, 380, 400, 20);
        confirmWarn.setForeground(Color.RED);
        confirmWarn.setVisible(false);

        saveSecurity.setBounds(350, 425, 180, 35);

        loadSecurity();

        saveSecurity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean currentValid = currentPassChecker();
                boolean newValid = newPassChecker();
                boolean confirmValid = confirmPassChecker();

                if (currentValid && newValid && confirmValid) {
                    loggedInUser.setPassword(password);
                    UserDataSaver.saveRequesters(RequesterMain.requesterUser);

                    currentPassField.setText("");
                    passField.setText("");
                    confirmPassField.setText("");

                    passWarn.setVisible(false);
                    confirmWarn.setVisible(false);

                    JOptionPane.showMessageDialog(EditRequesterSecurity.this,"Password changed successfully!");
                }

                revalidate();
                repaint();
            }
        });

        add(title);
        add(nameLabel);
        add(nameField);
        add(currentPassText);
        add(currentPassField);
        add(passText);
        add(passField);
        add(must);
        add(confirmPassText);
        add(confirmPassField);
        add(passWarn);
        add(confirmWarn);
        add(saveSecurity);
    }

    private void loadSecurity() {
        nameField.setText(loggedInUser.getUserName());
    }

    private boolean currentPassChecker() {
        currentPassword = new String(currentPassField.getPassword());

        if (!currentPassword.equals(loggedInUser.getPassword())) {
            passWarn.setText("Current password is incorrect.");
            passWarn.setVisible(true);
            return false;
        }

        passWarn.setVisible(false);
        return true;
    }

    private boolean newPassChecker() {
        password = new String(passField.getPassword());

        if (password.length() < 8) {
            passWarn.setText("Password must be at least 8 characters long.");
            passWarn.setVisible(true);
            return false;
        }

        if (password.equals(currentPassword)) {
            passWarn.setText("New password must be different from current password.");
            passWarn.setVisible(true);
            return false;
        }

        passWarn.setVisible(false);
        return true;
    }

    private boolean confirmPassChecker() {
        confirmPass = new String(confirmPassField.getPassword());

        if (!password.equals(confirmPass)) {
            confirmWarn.setVisible(true);
            return false;
        }

        confirmWarn.setVisible(false);
        return true;
    }
}
