package Counter;

import Data.CounterDetails;
import Admin.BackgroundPicture;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class CounterDashboard {

    private CounterDetails loggedInCounter;
    private NewRequester newRequester;

    private JFrame dbFrame = new JFrame("Counter Dashboard");
    private JPanel dbPanel = new JPanel();
    private JLabel counterName = new JLabel();
    private JLabel bgContainer = new JLabel();

    private JPanel options = new JPanel();
    private JButton menu;
    private JButton exitMenu = new JButton("x");

    private JButton requests = new JButton("Requests");
    private JButton newUsers = new JButton("New Users");
    private JButton security = new JButton("Edit Security");
    private JButton back = new JButton("Back to Welcoming");

    private ShowRequest showRequest;
    private EditCounterSecurity editSecurityPanel;

    public CounterDashboard(CounterDetails account) {
        loggedInCounter = account;

        dbPanel.setLayout(null);
        dbPanel.setBounds(90, 65, 1200, 600);
        dbPanel.setBackground(Color.decode("#f2e85a"));

        counterName.setBounds(100, 5, 1100, 60);
        counterName.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        counterName.setText(loggedInCounter.getFullName());

        BackgroundPicture callBG = new BackgroundPicture();
        String path = callBG.getBackground();

        if (path != null) {
            ImageIcon bgpic = new ImageIcon(path);
            Image smooth = bgpic.getImage().getScaledInstance(1380, 730, Image.SCALE_SMOOTH);
            bgContainer = new JLabel(new ImageIcon(smooth));
        } else {
            bgContainer = new JLabel();
        }

        options.setLayout(null);
        options.setBounds(0, 0, 300, 600);
        options.setBackground(Color.decode("#fcf9a2"));
        options.setVisible(false);

        showRequest = new ShowRequest(loggedInCounter);
        showRequest.setBounds(20, 80, 1160, 470);

        newRequester = new NewRequester();
        newRequester.setBounds(20, 80, 1160, 470);
        newRequester.setVisible(false);

        ImageIcon profileIcon = new ImageIcon("C:\\Users\\jjc\\Pictures\\IT203\\menu.jpg");
        Image img = profileIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        javax.swing.Timer refresh = new javax.swing.Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showRequest.isVisible()) {
                    showRequest.loadRequests();
                }
            }
        });

        refresh.start();

        menu = new JButton(new ImageIcon(img));
        menu.setBounds(0, 0, 60, 60);
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                options.setVisible(true);
                exitMenu.setVisible(true);
                menu.setVisible(false);
                showRequest.disableTable();
                dbPanel.setComponentZOrder(options, 0);

                dbPanel.revalidate();
                dbPanel.repaint();
            }
        });

        exitMenu.setBounds(250, 0, 50, 50);
        exitMenu.setVisible(false);
        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                options.setVisible(false);
                exitMenu.setVisible(false);
                menu.setVisible(true);
                showRequest.enableTable();

                dbPanel.revalidate();
                dbPanel.repaint();
            }
        });

        requests.setBounds(70, 150, 180, 30);
        requests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRequest.loadRequests();
                showRequest.setVisible(true);
                newRequester.setVisible(false);

                if (editSecurityPanel != null) {
                    editSecurityPanel.setVisible(false);
                }

                options.setVisible(false);
                exitMenu.setVisible(false);
                menu.setVisible(true);

                dbPanel.setComponentZOrder(menu, 0);
                dbPanel.setComponentZOrder(options, 1);
                dbPanel.setComponentZOrder(showRequest, 2);

                dbPanel.revalidate();
                dbPanel.repaint();
            }
        });

        newUsers.setBounds(70, 200, 180, 30);
        newUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newRequester.loadRequesters();
                newRequester.setVisible(true);
                showRequest.setVisible(false);

                if (editSecurityPanel != null) {
                    editSecurityPanel.setVisible(false);
                }

                options.setVisible(false);
                exitMenu.setVisible(false);
                menu.setVisible(true);

                dbPanel.setComponentZOrder(menu, 0);
                dbPanel.setComponentZOrder(options, 1);
                dbPanel.setComponentZOrder(newRequester, 2);

                dbPanel.revalidate();
                dbPanel.repaint();
            }
        });

        security.setBounds(70, 250, 180, 30);
        security.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRequest.setVisible(false);
                newRequester.setVisible(false);

                editSecurityPanel = new EditCounterSecurity(loggedInCounter);
                editSecurityPanel.setBounds(150, 60, 900, 500);

                dbPanel.add(editSecurityPanel);

                options.setVisible(false);
                exitMenu.setVisible(false);
                menu.setVisible(true);

                dbPanel.setComponentZOrder(menu, 0);
                dbPanel.setComponentZOrder(options, 1);
                dbPanel.setComponentZOrder(editSecurityPanel, 2);

                dbPanel.revalidate();
                dbPanel.repaint();
            }
        });

        back.setBounds(70, 300, 180, 30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbFrame.dispose();
                new CounterWelcoming();
            }
        });

        options.add(exitMenu);
        options.add(requests);
        options.add(newUsers);
        options.add(security);
        options.add(back);

        dbPanel.add(showRequest);
        dbPanel.add(newRequester);
        dbPanel.add(options);
        dbPanel.add(menu);
        dbPanel.add(counterName);

        dbPanel.setComponentZOrder(menu, 0);
        dbPanel.setComponentZOrder(options, 1);
        dbPanel.setComponentZOrder(showRequest, 2);

        bgContainer.add(dbPanel);
        dbFrame.add(bgContainer);

        dbFrame.setSize(1380, 730);
        dbFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dbFrame.setResizable(false);
        dbFrame.setLocationRelativeTo(null);

        dbFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refresh.stop();
            }
        });

        dbFrame.setVisible(true);
    }
}