package Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class AdminDashboard {

    private javax.swing.Timer refreshTimer;

    private JFrame dbFrame = new JFrame("Admin Dashboard");
    private JPanel dbPanel = new JPanel();

    private JLabel bgContainer = new JLabel();

    private JButton showUser = new JButton("Show Users");
    private JPanel suMarker = new JPanel();

    private JButton docToReq = new JButton("Documents to be Request");
    private JPanel dtrMarker = new JPanel();

    private JButton history = new JButton("History");
    private JPanel historyMarker = new JPanel();

    private JButton changeBG = new JButton("Change Background");
    private JPanel changeBGMarker = new JPanel();

    private JButton removeBG = new JButton("Remove Background");
    private JPanel removeBGMarker = new JPanel();

    private ShowUsers usersPanel;
    private CounterHistory historyPanel;
    private DocumentsConfigure docsPanel;

    private BackgroundPicture callBG = new BackgroundPicture();
    private String path;

    public AdminDashboard() {
        dbPanel.setLayout(null);
        dbPanel.setBounds(90, 65, 1200, 600);
        dbPanel.setBackground(Color.decode("#f2e85a"));

        bgContainer.setLayout(null);

        path = callBG.getBackground();

        if (path != null) {
            ImageIcon bgpic = new ImageIcon(path);
            Image smooth = bgpic.getImage().getScaledInstance(1380, 730, Image.SCALE_SMOOTH);
            bgContainer.setIcon(new ImageIcon(smooth));
        }

        suMarker.setLayout(null);
        suMarker.setBounds(20, 20, 200, 60);
        suMarker.setBackground(Color.decode("#f5ee93"));
        suMarker.setOpaque(true);

        showUser.setForeground(Color.BLACK);
        showUser.setBorderPainted(false);
        showUser.setContentAreaFilled(false);
        showUser.setFocusPainted(false);
        showUser.setOpaque(false);
        showUser.setBounds(0, 0, 200, 60);
        suMarker.add(showUser);

        showUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent zy) {
                showUsersPanel();
            }
        });

        dtrMarker.setLayout(null);
        dtrMarker.setBounds(240, 20, 200, 60);
        dtrMarker.setBackground(Color.decode("#d9d9d9"));
        dtrMarker.setOpaque(true);

        docToReq.setForeground(Color.BLACK);
        docToReq.setBorderPainted(false);
        docToReq.setContentAreaFilled(false);
        docToReq.setFocusPainted(false);
        docToReq.setOpaque(false);
        docToReq.setBounds(0, 0, 200, 60);
        dtrMarker.add(docToReq);

        docToReq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent zy) {
                showDocumentsPanel();
            }
        });

        historyMarker.setLayout(null);
        historyMarker.setBounds(460, 20, 200, 60);
        historyMarker.setBackground(Color.decode("#d9d9d9"));
        historyMarker.setOpaque(true);

        history.setForeground(Color.BLACK);
        history.setBorderPainted(false);
        history.setContentAreaFilled(false);
        history.setFocusPainted(false);
        history.setOpaque(false);
        history.setBounds(0, 0, 200, 60);
        historyMarker.add(history);

        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent zy) {
                showHistoryPanel();
            }
        });

        changeBGMarker.setLayout(null);
        changeBGMarker.setBounds(680, 20, 200, 60);
        changeBGMarker.setBackground(Color.decode("#d9d9d9"));
        changeBGMarker.setOpaque(true);

        changeBG.setForeground(Color.BLACK);
        changeBG.setBorderPainted(false);
        changeBG.setContentAreaFilled(false);
        changeBG.setFocusPainted(false);
        changeBG.setOpaque(false);
        changeBG.setBounds(0, 0, 200, 60);
        changeBGMarker.add(changeBG);

        changeBG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent zy) {
                callBG.setBackground();
                path = callBG.getBackground();

                if (path != null) {
                    ImageIcon bgpic = new ImageIcon(path);
                    Image smooth = bgpic.getImage().getScaledInstance(1380, 730, Image.SCALE_SMOOTH);
                    bgContainer.setIcon(new ImageIcon(smooth));
                }

                dbPanel.revalidate();
                dbPanel.repaint();
            }
        });

        removeBGMarker.setLayout(null);
        removeBGMarker.setBounds(900, 20, 200, 60);
        removeBGMarker.setBackground(Color.decode("#d9d9d9"));
        removeBGMarker.setOpaque(true);

        removeBG.setForeground(Color.BLACK);
        removeBG.setBorderPainted(false);
        removeBG.setContentAreaFilled(false);
        removeBG.setFocusPainted(false);
        removeBG.setOpaque(false);
        removeBG.setBounds(0, 0, 200, 60);
        removeBGMarker.add(removeBG);

        removeBG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent zy) {
                callBG.removeBackground();
                bgContainer.setIcon(null);

                dbPanel.revalidate();
                dbPanel.repaint();
            }
        });

        usersPanel = new ShowUsers();
        docsPanel = new DocumentsConfigure();
        historyPanel = new CounterHistory();

        showUsersPanel();

        bgContainer.add(dbPanel);
        dbFrame.add(bgContainer);

        dbFrame.setSize(1380, 730);
        dbFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dbFrame.setResizable(false);
        dbFrame.setLocationRelativeTo(null);
        dbFrame.setVisible(true);

        refreshTimer = new javax.swing.Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usersPanel.isShowing()) {
                    usersPanel.refreshUsers();
                }

                if (historyPanel.isShowing()) {
                    historyPanel.refreshHistory();
                }
            }
        });

        refreshTimer.start();

        dbFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refreshTimer.stop();
            }
        });
    }

    private void showUsersPanel() {
        dbPanel.removeAll();

        dbPanel.add(suMarker);
        dbPanel.add(dtrMarker);
        dbPanel.add(historyMarker);
        dbPanel.add(changeBGMarker);
        dbPanel.add(removeBGMarker);

        usersPanel.setBounds(20, 80, 1160, 500);
        dbPanel.add(usersPanel);

        suMarker.setBackground(Color.decode("#f5ee93"));
        dtrMarker.setBackground(Color.decode("#d9d9d9"));
        historyMarker.setBackground(Color.decode("#d9d9d9"));

        dbPanel.revalidate();
        dbPanel.repaint();
    }

    private void showDocumentsPanel() {
        dbPanel.removeAll();

        dbPanel.add(suMarker);
        dbPanel.add(dtrMarker);
        dbPanel.add(historyMarker);
        dbPanel.add(changeBGMarker);
        dbPanel.add(removeBGMarker);

        docsPanel.setBounds(20, 80, 1160, 500);
        dbPanel.add(docsPanel);

        suMarker.setBackground(Color.decode("#d9d9d9"));
        dtrMarker.setBackground(Color.decode("#f5ee93"));
        historyMarker.setBackground(Color.decode("#d9d9d9"));

        dbPanel.revalidate();
        dbPanel.repaint();
    }

    private void showHistoryPanel() {
        dbPanel.removeAll();

        dbPanel.add(suMarker);
        dbPanel.add(dtrMarker);
        dbPanel.add(historyMarker);
        dbPanel.add(changeBGMarker);
        dbPanel.add(removeBGMarker);

        historyPanel.setBounds(20, 80, 1160, 500);
        dbPanel.add(historyPanel);

        suMarker.setBackground(Color.decode("#d9d9d9"));
        dtrMarker.setBackground(Color.decode("#d9d9d9"));
        historyMarker.setBackground(Color.decode("#f5ee93"));

        dbPanel.revalidate();
        dbPanel.repaint();
    }
}