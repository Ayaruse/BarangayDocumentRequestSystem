package Counter;

import Data.CounterDetails;
import Data.UserDataSaver;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class CounterLogin {
    private JFrame loginWindow = new JFrame("Counter Log-In");
    private JPanel loginPanel = new JPanel();
    private JLabel username = new JLabel("Username");
    private JTextField userField = new JTextField();
    private JLabel password = new JLabel("Password");
    private JPasswordField passField = new JPasswordField();
    private JButton pressLogin = new JButton("Log-in");
    private JButton contactAdmin = new JButton("Contact Admin");
    private JButton back = new JButton("Back");

    public CounterLogin() {
        back.setBounds(420, 0, 65, 30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ey) {
                loginWindow.dispose();
                new CounterWelcoming();
            }
        });

        username.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        username.setBounds(70, 50, 240, 30);
        userField.setBounds(70, 85, 350, 30);
        password.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        password.setBounds(70, 115, 240, 30);
        passField.setBounds(70, 145, 350, 30);
        pressLogin.setBounds(170, 220, 120, 30);
        pressLogin.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        pressLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent zy) {
                UserDataSaver.loadCounters(CounterMain.counterUser);
                String name = userField.getText().trim();
                String pssword = new String(passField.getPassword());
                CounterDetails account = null;

                for (int i = 0; i < CounterMain.counterUser.size(); i++) {
                    CounterDetails counter = CounterMain.counterUser.get(i);
                    if (!name.equals(counter.getUserName())) {
                        continue;
                    }
                    account = counter;
                    break;
                }

                if (account == null) {
                    JOptionPane.showMessageDialog(loginWindow, "Wrong Username or Password");
                    return;
                }
                if ((account.getStatus()).equals("BLOCKED")) {
                    JOptionPane.showMessageDialog(loginWindow, "Your account has been blocked.");
                    return;
                }
                if (!pssword.equals(account.getPassword())) {
                    int attempts = account.getFailedAttempts() + 1;
                    account.setFailedAttempts(attempts);

                    if (attempts >= 3) {
                        account.setStatus("BLOCKED");
                        UserDataSaver.saveCounters(CounterMain.counterUser);
                        JOptionPane.showMessageDialog(loginWindow, "You have entered the wrong password 3 times. Your account has been blocked.");
                    } else {
                        UserDataSaver.saveCounters(CounterMain.counterUser);
                        JOptionPane.showMessageDialog(loginWindow, "Wrong Username or Password. Attempts remaining: " + (3 - attempts));
                    }
                    return;
                }
                if (!(account.getSecurity()).equals("VERIFIED")) {
                    JOptionPane.showMessageDialog(loginWindow, "Your account is not yet verified.");
                    return;
                }

                account.setFailedAttempts(0);
                UserDataSaver.saveCounters(CounterMain.counterUser);
                JOptionPane.showMessageDialog(loginWindow, "Account Successfully Logged In!");
                new CounterDashboard(account);
                loginWindow.dispose();
            }
        });

        contactAdmin.setBounds(120, 300, 220, 35);
        contactAdmin.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        contactAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDataSaver.loadCounters(CounterMain.counterUser);
                String name = userField.getText().trim();
                String pssword = new String(passField.getPassword());
                CounterDetails account = null;

                for (int i = 0; i < CounterMain.counterUser.size(); i++) {
                    CounterDetails counter = CounterMain.counterUser.get(i);
                    if (!name.equals(counter.getUserName())) {
                        continue;
                    }
                    if (!pssword.equals(counter.getPassword())) {
                        continue;
                    }
                    account = counter;
                    break;
                }

                if (account == null) {
                    JOptionPane.showMessageDialog(loginWindow, "Enter your correct username and password first.");
                    return;
                }

                JTextArea concernField = new JTextArea(6, 30);
                concernField.setLineWrap(true);
                concernField.setWrapStyleWord(true);
                JScrollPane concernScroll = new JScrollPane(concernField);
                JPanel messagePanel = new JPanel(new BorderLayout(5, 5));
                messagePanel.add(new JLabel("Describe your concern for the admin:"), BorderLayout.NORTH);
                messagePanel.add(concernScroll, BorderLayout.CENTER);

                int choice = JOptionPane.showConfirmDialog(loginWindow, messagePanel, "Contact Admin",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (choice != JOptionPane.OK_OPTION) {
                    return;
                }

                String concern = concernField.getText().trim();
                if (concern.isEmpty()) {
                    JOptionPane.showMessageDialog(loginWindow, "Please enter your concern");
                    return;
                }

                try {
                    FileWriter writer = new FileWriter("contactMessage.txt", true);
                    writer.write(account.getCounterUserId() + "|Counter|" + account.getUserName() + "|Pending|" + concern + "\n");
                    writer.close();
                    JOptionPane.showMessageDialog(loginWindow, "Your concern has been sent to the admin");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(loginWindow, "Unable to save your message. Please try again.");
                }
            }
        });

        loginPanel.setLayout(null);
        loginPanel.setBounds(0, 0, 500, 400);
        loginPanel.setBackground(Color.decode("#fffea8"));
        loginPanel.add(username);
        loginPanel.add(userField);
        loginPanel.add(password);
        loginPanel.add(passField);
        loginPanel.add(pressLogin);
        loginPanel.add(contactAdmin);
        loginPanel.add(back);

        loginWindow.add(loginPanel);
        loginWindow.setSize(500, 440);
        loginWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginWindow.setResizable(false);
        loginWindow.setLocationRelativeTo(null);
        loginWindow.setVisible(true);
    }
}