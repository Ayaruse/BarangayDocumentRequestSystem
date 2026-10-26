package Requester;

import Data.RequesterDetails;
import Data.UserDataSaver;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.*;
import java.util.Random;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RequesterCreateAccount {

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

    private JFrame userInfoFrame = new JFrame("User Information");//----------------------------------------
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

    private JLabel contactNum = new JLabel("Contact Number");
    private JTextField contactField = new JTextField();
    private String number;

    private JLabel addressLabel = new JLabel("Address");//dsssssssssssssssssssss-------

    private JLabel houme = new JLabel("House/Building Number & Street");
    private JTextField houmeField = new JTextField();
    private String house;

    private JLabel brgy = new JLabel("Barangay");
    private JTextField brgyField = new JTextField();
    private String barangy;

    private JLabel city = new JLabel("City/Municipality");
    private JTextField cityField = new JTextField();
    private String municity;

    private JLabel province = new JLabel("Province");
    private JTextField provinceField = new JTextField();
    private String provin;

    private JLabel bDayLabel = new JLabel("Birthday");//dsssssssssssssssssssss-------

    private JLabel bMonth = new JLabel("Month");
    private Integer monthList[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private JComboBox<Integer> bMonthField = new JComboBox<>(monthList);
    private int buwan;

    private JLabel bDay = new JLabel("Day");
    private JTextField bDayField = new JTextField();
    private JLabel bDayWarn = new JLabel("Please enter a valid number of day!");
    private int araw;

    private JLabel bYear = new JLabel("Year");
    private Integer yearList[] = new Integer[127];
    private JComboBox<Integer> bYearField;
    private int taon;

    private JFrame verificationFrame = new JFrame("Document Verification");
    private JPanel verificationPanel = new JPanel();

    private JLabel frontIDLabel = new JLabel("Front ID: Not Selected");
    private JLabel backIDLabel = new JLabel("Back ID: Not Selected");
    private JLabel birthCertLabel = new JLabel("Birth Certificate: Not Selected");

    private String frontIDPath = "";
    private String backIDPath = "";
    private String birthCertPath = "";


    public RequesterCreateAccount() {
        dates();
        resetField();
        back.setBounds(420, 0, 70, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ey) {
                AccountCreation.dispose();
                RequesterWelcoming call = new RequesterWelcoming();
            }
        });

        accCrtPanel.setLayout(null);
        accCrtPanel.setPreferredSize(new Dimension(500, 380));
        accCrtPanel.setBackground(Color.decode("#fffea8"));

        nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        nameLabel.setBounds(70, 30, 240, 30);
        nameField.setBounds(70, 60, 350, 30);
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
                username = nameField.getText();
                password = new String(passField.getPassword());
                confirmPass = new String(confirmPassField.getPassword());

                int lngth = password.length();

                if (username.trim().isEmpty() || password.trim().isEmpty() || confirmPass.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
                } else if (usernameChecker()) {
                    JOptionPane.showMessageDialog(null, "Username already exists! Please choose another!");
                } else if (lngth < 8) {
                    must.setForeground(Color.RED);
                } else if (!password.equals(confirmPass)) {
                    JOptionPane.showMessageDialog(null, "Password Mismatch!");
                } else {
                    JOptionPane.showMessageDialog(null, "Continue to filling the User Information");
                    userInfo();
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

    private void userInfo() {
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

        contactNum.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        contactNum.setBounds(70, 350, 240, 30);
        contactField.setBounds(70, 380, 350, 30);
        contactField.setText("09");

        addressLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        addressLabel.setBounds(190, 460, 240, 30);

        houme.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        houme.setBounds(70, 510, 300, 30);
        houmeField.setBounds(70, 540, 350, 30);

        brgy.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        brgy.setBounds(70, 590, 240, 30);
        brgyField.setBounds(70, 620, 350, 30);

        city.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        city.setBounds(70, 670, 240, 30);
        cityField.setBounds(70, 700, 350, 30);

        province.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        province.setBounds(70, 750, 240, 30);
        provinceField.setBounds(70, 780, 350, 30);

        bDayLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        bDayLabel.setBounds(190, 810, 240, 30);//------------------------------------------------

        bMonth.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        bMonth.setBounds(70, 870, 240, 30);
        bMonthField.setBounds(70, 900, 350, 30);

        bDay.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        bDay.setBounds(70, 960, 240, 30);
        bDayField.setBounds(70, 990, 350, 30);
        bDayWarn.setBounds(70, 1020, 350, 25);
        bDayWarn.setForeground(Color.RED);
        bDayWarn.setVisible(false);

        bYear.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        bYear.setBounds(70, 1050, 240, 30);
        bYearField.setBounds(70, 1080, 350, 30);

        JButton tuloy = new JButton("Continue");
        tuloy.setBounds(170, 1130, 150, 30);
        tuloy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buwan = (Integer) bMonthField.getSelectedItem();
                taon = (Integer) bYearField.getSelectedItem();

                boolean contak = contactChecker();
                boolean bday = bDayChecker();
                boolean fields = fieldChecker();

                if (contak && bday && fields) {
                    verification();
                    userInfoFrame.setVisible(false);
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
        userInfoPanel.add(contactNum);
        userInfoPanel.add(contactField);
        userInfoPanel.add(addressLabel);
        userInfoPanel.add(houme);
        userInfoPanel.add(houmeField);
        userInfoPanel.add(brgy);
        userInfoPanel.add(brgyField);
        userInfoPanel.add(city);
        userInfoPanel.add(cityField);
        userInfoPanel.add(province);
        userInfoPanel.add(provinceField);
        userInfoPanel.add(bDayLabel);
        userInfoPanel.add(bMonth);
        userInfoPanel.add(bMonthField);
        userInfoPanel.add(bDay);
        userInfoPanel.add(bDayField);
        userInfoPanel.add(bDayWarn);
        userInfoPanel.add(bYear);
        userInfoPanel.add(bYearField);
        userInfoPanel.add(tuloy);

        userInfoContainer.setViewportView(userInfoPanel);

        userInfoFrame.add(userInfoContainer);
        userInfoFrame.setSize(500, 680);
        userInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userInfoFrame.setResizable(false);
        userInfoFrame.setLocationRelativeTo(null);
        userInfoFrame.setVisible(true);
    }

    private void verification() {
        verificationPanel.setLayout(null);
        verificationPanel.setPreferredSize(new Dimension(500, 350));
        verificationPanel.setBackground(Color.decode("#fffea8"));

        JLabel title = new JLabel("Upload Verification Documents");
        title.setFont(new Font("Times New Roman", Font.BOLD, 22));
        title.setBounds(100, 20, 380, 35);

        JButton frontButton = new JButton("Choose Front ID");
        frontButton.setBounds(70, 80, 150, 35);
        frontIDLabel.setBounds(70, 115, 360, 30);

        JButton backButton = new JButton("Choose Back ID");
        backButton.setBounds(70, 155, 150, 35);
        backIDLabel.setBounds(70, 190, 360, 30);

        JButton birthCertButton = new JButton("Choose Birth Certificate");
        birthCertButton.setBounds(70, 230, 200, 35);
        birthCertLabel.setBounds(70, 265, 380, 30);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(180, 300, 120, 35);

        frontButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = chooseFile();
                if (selected != null) {
                    frontIDPath = selected;
                    frontIDLabel.setText("Front ID: " + new java.io.File(selected).getName());
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = chooseFile();

                if (selected != null) {
                    backIDPath = selected;
                    backIDLabel.setText("Back ID: " + new java.io.File(selected).getName());
                }
            }
        });

        birthCertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = chooseFile();

                if (selected != null) {
                    birthCertPath = selected;
                    birthCertLabel.setText("Birth Certificate: " + new java.io.File(selected).getName());
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frontIDPath.isEmpty() || backIDPath.isEmpty() || birthCertPath.isEmpty()) {
                    JOptionPane.showMessageDialog(verificationFrame, "Please select all three documents.");
                } else {
                    RequesterDetails newRequester = new RequesterDetails(
                            username,
                            password,
                            "Requester",
                            idGenerator(),
                            LocalDateTime.now(),
                            "ACTIVE",
                            fName,
                            mName,
                            lName,
                            sufix,
                            number,
                            house,
                            barangy,
                            municity,
                            provin,
                            buwan,
                            araw,
                            taon,
                            frontIDPath,
                            backIDPath,
                            birthCertPath);
                    RequesterMain.requesterUser.add(newRequester);
                    UserDataSaver.saveRequesters(RequesterMain.requesterUser);
                    JOptionPane.showMessageDialog(null, "Account Created Successfully!"
                            + "\nWait for Counter to verify your Account");
                    AccountCreation.dispose();
                    userInfoFrame.dispose();
                    verificationFrame.dispose();
                    new RequesterWelcoming();
                    System.out.println("Total registered users: " + RequesterMain.requesterUser.size());
                    System.out.println("Registered username: " + newRequester.getUserName());
                }
            }
        });

        verificationPanel.add(title);
        verificationPanel.add(frontButton);
        verificationPanel.add(frontIDLabel);
        verificationPanel.add(backButton);
        verificationPanel.add(backIDLabel);
        verificationPanel.add(birthCertButton);
        verificationPanel.add(birthCertLabel);
        verificationPanel.add(submitButton);

        verificationFrame.setContentPane(verificationPanel);
        verificationFrame.setSize(500, 390);
        verificationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        verificationFrame.setResizable(false);
        verificationFrame.setLocationRelativeTo(null);
        verificationFrame.setVisible(true);
    }
//===========================================

    private String chooseFile() {
        JFileChooser fileSelect = new JFileChooser();
        fileSelect.setCurrentDirectory(new File("C:\\Users\\jjc\\Pictures\\IT203"));

        FileNameExtensionFilter filler = new FileNameExtensionFilter("Image Files (*.png, *.jpg, *.jpeg)",
                "png", "jpg", "jpeg");

        fileSelect.setFileFilter(filler);
        fileSelect.setAcceptAllFileFilterUsed(false);

        int result = fileSelect.showDialog(fileSelect, "Upload");

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileSelect.getSelectedFile().getAbsolutePath();
        }

        return null;
    }
//-----------------------------------------------------------

    private boolean contactChecker() {
        number = contactField.getText();

        if (!number.matches("09\\d{9}")) {
            JOptionPane.showMessageDialog(null, "Invalid Contact Number! Please Enter Valid 11-digit Number Starting with 09");
            return false;
        }

        return true;
    }

    private boolean bDayChecker() {
        try {
            araw = Integer.parseInt(bDayField.getText());
            bDayWarn.setVisible(false);

        } catch (NumberFormatException ex) {
            bDayWarn.setVisible(true);
            return false;
        }

        switch (buwan) {
            case 1, 3, 5, 7, 8, 10, 12:
                if (araw < 1 || araw > 31) {
                    bDayWarn.setVisible(true);
                    return false;
                }
                break;

            case 4, 6, 9, 11:
                if (araw < 1 || araw > 30) {
                    bDayWarn.setVisible(true);
                    return false;
                }
                break;

            case 2:
                if (araw < 1 || araw > 29) {
                    bDayWarn.setVisible(true);
                    return false;
                }
                break;
        }

        return true;
    }

    private boolean fieldChecker() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()
                || houmeField.getText().isEmpty() || brgyField.getText().isEmpty()
                || cityField.getText().isEmpty() || provinceField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(userInfoFrame, "Please fill in all required fields!");
            return false;
        } else {
            fName = firstNameField.getText().trim();
            mName = midNameField.getText().trim();
            lName = lastNameField.getText().trim();
            sufix = suffixField.getText().trim();
            house = houmeField.getText().trim();
            barangy = brgyField.getText().trim();
            municity = cityField.getText().trim();
            provin = provinceField.getText().trim();

            return true;
        }
    }
//-----------------------------------------------------------

    private void dates() {
        int currentYr = Year.now().getValue();
        for (int i = 0; i < yearList.length; i++) {
            yearList[i] = currentYr - i;
        }
        bYearField = new JComboBox<>(yearList);
    }

    private int idGenerator() {
        Random random = new Random();
        int genNum = 0;
        boolean existing = true;

        while (existing) {
            genNum = random.nextInt(300000) + 1_000_000;
            System.out.println("This is the Generated Number: " + genNum);
            existing = false;
            for (int i = 0; i < RequesterMain.requesterUser.size(); i++) {

                if (genNum == RequesterMain.requesterUser.get(i).getUserID()) {
                    existing = true;
                    break;
                }
            }
        }
        return genNum;
    }

    private boolean usernameChecker() {
        for (int i = 0; i < RequesterMain.requesterUser.size(); i++) {
            if (RequesterMain.requesterUser.get(i).getUserName().equalsIgnoreCase(username)) {
                return true;
            }
        }

        return false;
    }

    private void resetField() {
        nameField.setText("");
        passField.setText("");
        confirmPassField.setText("");

        firstNameField.setText("");
        midNameField.setText("");
        lastNameField.setText("");
        suffixField.setText("");

        contactField.setText("09");
        houmeField.setText("");
        brgyField.setText("");
        cityField.setText("");
        provinceField.setText("");

        bMonthField.setSelectedIndex(0);
        bDayField.setText("");
        bYearField.setSelectedIndex(0);

        frontIDPath = "";
        backIDPath = "";
        birthCertPath = "";

        frontIDLabel.setText("Front ID: Not Selected");
        backIDLabel.setText("Back ID: Not Selected");
        birthCertLabel.setText("Birth Certificate: Not Selected");

        bDayWarn.setVisible(false);
        must.setForeground(Color.BLACK);
    }

//    public static void main(String[] args) {
//    }
}
