package Requester;

import Data.RequesterDetails;
import Data.UserDataSaver;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.*;

public class EditProfile extends JPanel {

    private RequesterDetails loggedInUser;

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
    private JLabel numWarn = new JLabel("Please enter a valid phone number!");
    private String number;

    private JLabel addressLabel = new JLabel("Address");

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

    private JLabel bDayLabel = new JLabel("Birthday");

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

    private JButton saveEdit = new JButton("Save Changes");

    public EditProfile(RequesterDetails account) {

        loggedInUser = account;

        dates();

        setLayout(null);
        setPreferredSize(new Dimension(900, 500));
        setBackground(Color.decode("#fffea8"));

        JLabel title = new JLabel("Edit Profile");
        title.setFont(new Font("Times New Roman", Font.BOLD, 25));
        title.setBounds(350, 10, 250, 35);

        firstName.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        firstName.setBounds(30, 60, 240, 25);
        firstNameField.setBounds(30, 85, 350, 30);

        midName.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        midName.setBounds(30, 125, 240, 25);
        midNameField.setBounds(30, 150, 350, 30);

        lastName.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lastName.setBounds(30, 190, 240, 25);
        lastNameField.setBounds(30, 215, 350, 30);

        suffix.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        suffix.setBounds(30, 255, 350, 25);
        suffixField.setBounds(30, 280, 350, 30);

        contactNum.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        contactNum.setBounds(30, 320, 240, 25);
        contactField.setBounds(30, 345, 350, 30);

        numWarn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        numWarn.setBounds(30, 375, 350, 25);
        numWarn.setForeground(Color.RED);
        numWarn.setVisible(false);

        addressLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
        addressLabel.setBounds(540, 55, 240, 30);

        houme.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        houme.setBounds(450, 95, 350, 25);
        houmeField.setBounds(450, 120, 400, 30);

        brgy.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        brgy.setBounds(450, 160, 240, 25);
        brgyField.setBounds(450, 185, 400, 30);

        city.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        city.setBounds(450, 225, 240, 25);
        cityField.setBounds(450, 250, 400, 30);

        province.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        province.setBounds(450, 290, 240, 25);
        provinceField.setBounds(450, 315, 400, 30);

        bDayLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
        bDayLabel.setBounds(450, 355, 240, 30);

        bMonth.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        bMonth.setBounds(450, 390, 70, 25);
        bMonthField.setBounds(450, 415, 90, 30);

        bDay.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        bDay.setBounds(560, 390, 70, 25);
        bDayField.setBounds(560, 415, 70, 30);

        bDayWarn.setBounds(450, 445, 350, 20);
        bDayWarn.setForeground(Color.RED);
        bDayWarn.setVisible(false);

        bYear.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        bYear.setBounds(650, 390, 100, 25);
        bYearField.setBounds(650, 415, 120, 30);

        saveEdit.setBounds(30, 425, 180, 35);

        loadProfile();

        saveEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                buwan = (Integer) bMonthField.getSelectedItem();
                taon = (Integer) bYearField.getSelectedItem();

                boolean contak = contactChecker();
                boolean bday = bDayChecker();
                boolean fields = fieldChecker();

                if (contak && bday && fields) {

                    loggedInUser.setFName(fName);
                    loggedInUser.setMName(mName);
                    loggedInUser.setLName(lName);
                    loggedInUser.setSuffix(sufix);

                    loggedInUser.setContactNum(number);

                    loggedInUser.setFullAddress(house, barangy, municity, provin);

                    loggedInUser.setBirtMonth(buwan);
                    loggedInUser.setBirtDay(araw);
                    loggedInUser.setBirtYear(taon);

                    loggedInUser.calcAge();

                    UserDataSaver.saveRequesters(
                            RequesterMain.requesterUser);
                }
            }
        });

        add(title);
        add(firstName);
        add(firstNameField);
        add(midName);
        add(midNameField);
        add(lastName);
        add(lastNameField);
        add(suffix);
        add(suffixField);
        add(contactNum);
        add(contactField);
        add(numWarn);
        add(addressLabel);
        add(houme);
        add(houmeField);
        add(brgy);
        add(brgyField);
        add(city);
        add(cityField);
        add(province);
        add(provinceField);
        add(bDayLabel);
        add(bMonth);
        add(bMonthField);
        add(bDay);
        add(bDayField);
        add(bDayWarn);
        add(bYear);
        add(bYearField);
        add(saveEdit);
    }

    private void loadProfile() {

        firstNameField.setText(loggedInUser.getFName());
        midNameField.setText(loggedInUser.getMName());
        lastNameField.setText(loggedInUser.getLName());
        suffixField.setText(loggedInUser.getSuffix());

        contactField.setText(loggedInUser.getContactNum());

        houmeField.setText(loggedInUser.getHouseBuildingNoStrt());
        brgyField.setText(loggedInUser.getBaranggay());
        cityField.setText(loggedInUser.getCity());
        provinceField.setText(loggedInUser.getProvince());

        bMonthField.setSelectedItem(loggedInUser.getBirtMonth());
        bDayField.setText(String.valueOf(loggedInUser.getBirtDay()));
        bYearField.setSelectedItem(loggedInUser.getBirtYear());
    }

    private boolean contactChecker() {

        number = contactField.getText();

        if (!number.matches("09\\d{9}")) {
            numWarn.setVisible(true);
            return false;
        }

        numWarn.setVisible(false);
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

        if (firstNameField.getText().isEmpty()
                || lastNameField.getText().isEmpty()
                || houmeField.getText().isEmpty()
                || brgyField.getText().isEmpty()
                || cityField.getText().isEmpty()
                || provinceField.getText().isEmpty()) {
            return false;

        } else {
            fName = firstNameField.getText().trim();
            mName = midNameField.getText();
            lName = lastNameField.getText().trim();
            sufix = suffixField.getText();

            house = houmeField.getText().trim();
            barangy = brgyField.getText().trim();
            municity = cityField.getText().trim();
            provin = provinceField.getText().trim();

            return true;
        }
    }

    private void dates() {
        int currentYr = Year.now().getValue();
        for (int i = 0; i < yearList.length; i++) {
            yearList[i] = currentYr - i;
        }
        bYearField = new JComboBox<>(yearList);
    }
}
