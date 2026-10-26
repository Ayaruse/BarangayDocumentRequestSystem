package Requester;

import Admin.*;
import Data.RequesterDetails;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RequesterDashboard {

    private RequesterDetails loggedInRequester;

    private JFrame dbFrame = new JFrame("User Dashboard");
    private JLabel userName = new JLabel();
    private JPanel dbPanel = new JPanel();
    private JLabel bgContainer;

    private JPanel options = new JPanel();
    private JButton menu;
    private JButton exitMenu = new JButton("x");

    private JButton req = new JButton("Request Document");
    private JButton myReq = new JButton("My Requests");
    private JButton profile = new JButton("Edit Profile");
    private JButton security = new JButton("Edit Security");
    private JButton back = new JButton("Back to Welcoming");

    private RequestDocument requestPanel;
    private MyRequests myRequestsPanel;
    private EditProfile editProfilePanel;
    private EditRequesterSecurity editSecurityPanel;

    public RequesterDashboard(RequesterDetails account) {
        loggedInRequester = account;

        dbPanel.setLayout(null);
        dbPanel.setBounds(90, 65, 1200, 600);
        dbPanel.setBackground(Color.decode("#faf09b"));

        userName.setBounds(100, 5, 1100, 60);
        userName.setFont(new Font("Times New Roman", Font.PLAIN, 55));

        userName.setText(account.getFullName());

        BackgroundPicture callBG = new BackgroundPicture();
        String path = callBG.getBackground();

        if (path != null) {
            ImageIcon bgpic = new ImageIcon(path);
            Image smooth = bgpic.getImage().getScaledInstance(1380, 730, Image.SCALE_SMOOTH);
            bgContainer = new JLabel(new ImageIcon(smooth));
        } else {
            bgContainer = new JLabel();
        }

        bgContainer.setLayout(null);
        bgContainer.add(dbPanel);

        options.setLayout(null);
        options.setBounds(0, 0, 300, 600);
        options.setBackground(Color.decode("#fcf9a2"));
        options.setVisible(false);

        requestPanel = new RequestDocument(loggedInRequester, this);
        requestPanel.setBounds(150, 60, 900, 500);

        ImageIcon profileIcon = new ImageIcon("C:\\Users\\jjc\\Pictures\\IT203\\menu.jpg");
        Image img = profileIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        menu = new JButton(new ImageIcon(img));
        menu.setBounds(0, 0, 60, 60);
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                options.setVisible(true);
                exitMenu.setVisible(true);
                menu.setVisible(false);

                requestPanel.disableComponents();

                dbPanel.setComponentZOrder(options, 0);
                dbPanel.setComponentZOrder(exitMenu, 0);

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

                requestPanel.enableComponents();

                dbPanel.revalidate();
                dbPanel.repaint();
            }
        });

        req.setBounds(70, 150, 180, 30);
        req.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myRequestsPanel != null) {
                    dbPanel.remove(myRequestsPanel);
                }

                if (editProfilePanel != null) {
                    dbPanel.remove(editProfilePanel);
                }

                if (editSecurityPanel != null) {
                    dbPanel.remove(editSecurityPanel);
                }

                if (requestPanel.getParent() == null) {
                    dbPanel.add(requestPanel);
                }

                requestPanel.setBounds(150, 60, 900, 500);
                requestPanel.setVisible(true);

                requestPanel.enableComponents();
                options.setVisible(false);
                exitMenu.setVisible(false);
                menu.setVisible(true);

                dbPanel.setComponentZOrder(menu, 0);
                dbPanel.setComponentZOrder(options, 1);
                dbPanel.setComponentZOrder(requestPanel, 2);

                dbPanel.revalidate();
                dbPanel.repaint();
            }
        });

        myReq.setBounds(70, 200, 180, 30);
        myReq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (requestPanel != null) {
                    dbPanel.remove(requestPanel);
                }

                if (editProfilePanel != null) {
                    dbPanel.remove(editProfilePanel);
                }

                if (editSecurityPanel != null) {
                    dbPanel.remove(editSecurityPanel);
                }

                if (myRequestsPanel != null) {
                    dbPanel.remove(myRequestsPanel);
                }

                myRequestsPanel = new MyRequests(loggedInRequester);
                myRequestsPanel.setBounds(150, 60, 900, 500);
                dbPanel.add(myRequestsPanel);

                options.setVisible(false);
                exitMenu.setVisible(false);
                menu.setVisible(true);

                dbPanel.setComponentZOrder(menu, 0);
                dbPanel.setComponentZOrder(options, 1);
                dbPanel.setComponentZOrder(myRequestsPanel, 2);

                dbPanel.revalidate();
                dbPanel.repaint();
            }
        });
        profile.setBounds(70, 250, 180, 30);
        profile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (requestPanel != null) {
                    dbPanel.remove(requestPanel);
                }

                if (myRequestsPanel != null) {
                    dbPanel.remove(myRequestsPanel);
                }

                if (editSecurityPanel != null) {
                    dbPanel.remove(editSecurityPanel);
                }

                editProfilePanel = new EditProfile(loggedInRequester);
                editProfilePanel.setBounds(150, 60, 900, 500);

                dbPanel.add(editProfilePanel);

                options.setVisible(false);
                exitMenu.setVisible(false);
                menu.setVisible(true);

                dbPanel.setComponentZOrder(menu, 0);
                dbPanel.setComponentZOrder(options, 1);
                dbPanel.setComponentZOrder(editProfilePanel, 2);

                dbPanel.revalidate();
                dbPanel.repaint();
            }
        });

        security.setBounds(70, 300, 180, 30);
        security.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (requestPanel != null) {
                    dbPanel.remove(requestPanel);
                }

                if (myRequestsPanel != null) {
                    dbPanel.remove(myRequestsPanel);
                }

                if (editProfilePanel != null) {
                    dbPanel.remove(editProfilePanel);
                }

                editSecurityPanel = new EditRequesterSecurity(loggedInRequester);
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

        back.setBounds(70, 350, 180, 30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbFrame.dispose();
                new RequesterWelcoming();
            }
        });

        options.add(exitMenu);
        options.add(req);
        options.add(myReq);
        options.add(profile);
        options.add(security);
        options.add(back);

        dbPanel.add(requestPanel);
        dbPanel.add(options);
        dbPanel.add(menu);
        dbPanel.add(userName);

        dbPanel.setComponentZOrder(menu, 0);
        dbPanel.setComponentZOrder(options, 1);
        dbPanel.setComponentZOrder(requestPanel, 2);

        dbFrame.add(bgContainer);
        dbFrame.setSize(1380, 730);
        dbFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dbFrame.setResizable(false);
        dbFrame.setLocationRelativeTo(null);
        dbFrame.setVisible(true);
    }
}
