package Counter;

import Data.CounterDetails;
import Data.UserDataSaver;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class CounterCreateAccount {

    private JFrame AccountCreation = new JFrame("Create Account");
    private JPanel accCrtPanel = new JPanel();

    private JButton back = new JButton("Back");

    private JLabel nameLabel = new JLabel("Username");
    private JTextField nameField = new JTextField();
    private String username;

    private JLabel passText = new JLabel("Password");
    private JPasswordField passField = new JPasswordField();
    private String password;

    private JLabel must = new JLabel("Password must be 8 Characters long.");

    private JLabel confirmPassText = new JLabel("Confirm Password");
    private JPasswordField confirmPassField = new JPasswordField();
    private String confirmPass;

    private JButton crtButton = new JButton("Continue");

    private JFrame userInfoFrame = new JFrame("User Information");
    private JScrollPane userInfoContainer = new JScrollPane();
    private JPanel userInfoPanel = new JPanel();

    private JLabel firstName = new JLabel("First Name");
    private JTextField firstNameField = new JTextField();
    private String fName;

    private JLabel midName = new JLabel("Middle Name");
    private JTextField midNameField = new JTextField();
    private String mName;

    private JLabel lastName = new JLabel("Last Name");
    private JTextField lastNameField = new JTextField();
    private String lName;

    private JLabel suffix = new JLabel("Suffix (if applicable)");
    private JTextField suffixField = new JTextField();
    private String sufix;

    private String fulname;

    public CounterCreateAccount() {
        resetField();

        back.setBounds(420, 0, 70, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountCreation.dispose();
                new CounterWelcoming();
            }
        });

        accCrtPanel.setLayout(null);
        accCrtPanel.setPreferredSize(new Dimension(500, 380));
        accCrtPanel.setBackground(Color.decode("#fffea8"));

        nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        nameLabel.setBounds(70, 30, 240, 30);
        nameField.setBounds(70, 60, 350, 30);

        passText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        passText.setBounds(70, 100, 240, 30);
        passField.setBounds(70, 130, 350, 30);

        must.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        must.setBounds(70, 155, 350, 30);

        confirmPassText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        confirmPassText.setBounds(70, 180, 240, 30);
        confirmPassField.setBounds(70, 210, 350, 30);

        crtButton.setBounds(170, 270, 130, 30);
        crtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = nameField.getText().trim();
                password = new String(passField.getPassword());
                confirmPass = new String(confirmPassField.getPassword());

                int lngth = password.length();

                if (username.isEmpty() || password.trim().isEmpty()
                        || confirmPass.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please fill in all the fields.");
                } else if (usernameChecker()) {
                    JOptionPane.showMessageDialog(null,
                            "Username already exists! Please choose another!");
                } else if (lngth < 8) {
                    must.setForeground(Color.RED);
                } else if (!password.equals(confirmPass)) {
                    JOptionPane.showMessageDialog(null, "Password Mismatch!");
                } else {
                    counterInfo();
                    AccountCreation.setVisible(false);
                }
            }
        });

        accCrtPanel.add(back);
        accCrtPanel.add(nameLabel);
        accCrtPanel.add(nameField);
        accCrtPanel.add(passText);
        accCrtPanel.add(passField);
        accCrtPanel.add(must);
        accCrtPanel.add(confirmPassText);
        accCrtPanel.add(confirmPassField);
        accCrtPanel.add(crtButton);

        AccountCreation.add(accCrtPanel);
        AccountCreation.setSize(500, 380);
        AccountCreation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        AccountCreation.setResizable(false);
        AccountCreation.setLocationRelativeTo(null);
        AccountCreation.setVisible(true);
    }

    private void counterInfo() {
        userInfoPanel.setLayout(null);
        userInfoPanel.setPreferredSize(new Dimension(465, 1180));
        userInfoPanel.setBackground(Color.decode("#fffea8"));

        firstName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        firstName.setBounds(70, 30, 240, 30);
        firstNameField.setBounds(70, 60, 350, 30);

        midName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        midName.setBounds(70, 110, 240, 30);
        midNameField.setBounds(70, 140, 350, 30);

        lastName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lastName.setBounds(70, 190, 240, 30);
        lastNameField.setBounds(70, 220, 350, 30);

        suffix.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        suffix.setBounds(70, 270, 350, 30);
        suffixField.setBounds(70, 300, 40, 30);

        JButton tuloy = new JButton("Create Account");
        tuloy.setBounds(170, 350, 150, 30);

        tuloy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (firstNameField.getText().trim().isEmpty()
                        || lastNameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(userInfoFrame,
                            "Please fill in all required fields!");
                } else {
                    fName = firstNameField.getText().trim();
                    mName = midNameField.getText().trim();
                    lName = lastNameField.getText().trim();
                    sufix = suffixField.getText().trim();

                    if (!mName.isEmpty() && !sufix.isEmpty()) {
                        fulname = fName + " " + mName.charAt(0)
                                + ". " + lName + " " + sufix;
                    } else if (!mName.isEmpty()) {
                        fulname = fName + " " + mName.charAt(0)
                                + ". " + lName;
                    } else if (!sufix.isEmpty()) {
                        fulname = fName + " " + lName + " " + sufix;
                    } else {
                        fulname = fName + " " + lName;
                    }

                    CounterDetails newCounter = new CounterDetails(
                            username,
                            password,
                            "COUNTER",
                            idGenerator(),
                            "ACTIVE",
                            fName,
                            mName,
                            lName,
                            sufix
                    );
                    CounterMain.counterUser.add(newCounter);
                    UserDataSaver.saveCounters(CounterMain.counterUser);

                    JOptionPane.showMessageDialog(null,
                            "Account Created Successfully!"
                            + "\nWait for Admin to Verify your Account");

                    AccountCreation.dispose();
                    userInfoFrame.dispose();
                    new CounterWelcoming();
                }
            }
        });

        userInfoPanel.add(firstName);
        userInfoPanel.add(firstNameField);
        userInfoPanel.add(midName);
        userInfoPanel.add(midNameField);
        userInfoPanel.add(lastName);
        userInfoPanel.add(lastNameField);
        userInfoPanel.add(suffix);
        userInfoPanel.add(suffixField);
        userInfoPanel.add(tuloy);

        userInfoFrame.add(userInfoPanel);
        userInfoFrame.setSize(500, 470);
        userInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userInfoFrame.setResizable(false);
        userInfoFrame.setLocationRelativeTo(null);
        userInfoFrame.setVisible(true);
    }

    private boolean usernameChecker() {
        for (int i = 0; i < CounterMain.counterUser.size(); i++) {
            if (CounterMain.counterUser.get(i).getUserName()
                    .equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    private int idGenerator() {
        Random random = new Random();
        int genNum = 0;
        boolean existing = true;

        while (existing) {
            genNum = random.nextInt(300000) + 2_000_000;
            System.out.println("This is the Generated Number: " + genNum);
            existing = false;

            for (int i = 0; i < CounterMain.counterUser.size(); i++) {
                if (genNum == CounterMain.counterUser.get(i).getCounterUserId()) {
                    existing = true;
                    break;
                }
            }
        }
        return genNum;
    }

    private void resetField() {
        nameField.setText("");
        passField.setText("");
        confirmPassField.setText("");

        firstNameField.setText("");
        midNameField.setText("");
        lastNameField.setText("");
        suffixField.setText("");

        must.setForeground(Color.BLACK);
    }
}