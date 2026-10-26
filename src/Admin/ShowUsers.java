package Admin;

import Data.CounterDetails;
import Data.RequesterDetails;
import Data.UserDataSaver;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShowUsers extends JPanel {

    private ArrayList<CounterDetails> cntList = new ArrayList<>();
    private ArrayList<RequesterDetails> reqList = new ArrayList<>();

    private CardLayout cards = new CardLayout();
    private JPanel mainPanel = new JPanel(cards);
    private JPanel topBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

    private JButton reqBtn = new JButton("Requester Users");
    private JButton cntBtn = new JButton("Counter Users");
    private JButton blkBtn = new JButton("Blocked Users");
    private JButton msgBtn = new JButton("Contact Messages");

    private JPanel reqPanel = new JPanel(new BorderLayout(10, 10));
    private JPanel cntPanel = new JPanel(new BorderLayout(10, 10));
    private JPanel blkPanel = new JPanel(new GridLayout(2, 1, 10, 10));
    private JPanel msgPanel = new JPanel(new BorderLayout(10, 10));

    private DefaultTableModel reqModel = makeModel();
    private DefaultTableModel cntModel = makeModel();
    private DefaultTableModel blkReqModel = makeModel();
    private DefaultTableModel blkCntModel = makeModel();

    private DefaultTableModel msgModel = new DefaultTableModel(new String[]{"User ID", "Role", "Username", "Status", "Concern"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private JTable reqTable = new JTable(reqModel);
    private JTable cntTable = new JTable(cntModel);
    private JTable blkReqTable = new JTable(blkReqModel);
    private JTable blkCntTable = new JTable(blkCntModel);
    private JTable msgTable = new JTable(msgModel);

    private JButton blockReqBtn = new JButton("Block");
    private JButton blockCntBtn = new JButton("Block");
    private JButton verifyCntBtn = new JButton("Verify");
    private JButton unblockReqBtn = new JButton("Unblock");
    private JButton unblockCntBtn = new JButton("Unblock");

    private JTextField searchReqIDField = new JTextField(10);
    private JButton searchReqIDBtn = new JButton("Search");
    private JButton resetReqIDBtn = new JButton("Reset");

    private JTextField searchCntIDField = new JTextField(10);
    private JButton searchCntIDBtn = new JButton("Search");
    private JButton resetCntIDBtn = new JButton("Reset");

    public ShowUsers() {
        setBackground(Color.decode("#f5ee93"));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topBtns.setBackground(Color.decode("#f5ee93"));

        reqBtn.setPreferredSize(new Dimension(140, 30));
        cntBtn.setPreferredSize(new Dimension(130, 30));
        blkBtn.setPreferredSize(new Dimension(120, 30));
        msgBtn.setPreferredSize(new Dimension(150, 30));

        topBtns.add(reqBtn);
        topBtns.add(cntBtn);
        topBtns.add(blkBtn);
        topBtns.add(msgBtn);

        JPanel reqActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel cntActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel blkReqActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel blkCntActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        reqActions.setBackground(Color.WHITE);
        cntActions.setBackground(Color.WHITE);
        blkReqActions.setBackground(Color.WHITE);
        blkCntActions.setBackground(Color.WHITE);

        reqActions.add(blockReqBtn);
        cntActions.add(verifyCntBtn);
        cntActions.add(blockCntBtn);
        blkReqActions.add(unblockReqBtn);
        blkCntActions.add(unblockCntBtn);

        reqPanel.add(makeSection("Requester Accounts", reqTable), BorderLayout.CENTER);
        reqPanel.add(reqActions, BorderLayout.SOUTH);

        cntPanel.add(makeSection("Counter Accounts", cntTable), BorderLayout.CENTER);
        cntPanel.add(cntActions, BorderLayout.SOUTH);

        JPanel blkReqSection = makeBlockedSection("Blocked Requesters", blkReqTable, searchReqIDField, searchReqIDBtn, resetReqIDBtn);

        JPanel blkCntSection = makeBlockedSection("Blocked Counters", blkCntTable, searchCntIDField, searchCntIDBtn, resetCntIDBtn);

        blkReqSection.add(blkReqActions, BorderLayout.SOUTH);
        blkCntSection.add(blkCntActions, BorderLayout.SOUTH);

        blkPanel.add(blkReqSection);
        blkPanel.add(blkCntSection);

        msgPanel.add(makeSection("Messages from Requesters and Counters", msgTable), BorderLayout.CENTER);

        mainPanel.add(reqPanel, "REQ");
        mainPanel.add(cntPanel, "CNT");
        mainPanel.add(blkPanel, "BLK");
        mainPanel.add(msgPanel, "MSG");

        reqBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshUsers();
                cards.show(mainPanel, "REQ");
            }
        });

        cntBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshUsers();
                cards.show(mainPanel, "CNT");
            }
        });

        blkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshUsers();
                cards.show(mainPanel, "BLK");
            }
        });

        msgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMessages();
                cards.show(mainPanel, "MSG");
            }
        });

        searchReqIDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBlockedRequester();
            }
        });

        resetReqIDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchReqIDField.setText("");
                refreshUsers();
            }
        });

        searchCntIDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBlockedCounter();
            }
        });

        resetCntIDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCntIDField.setText("");
                refreshUsers();
            }
        });

        blockReqBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockReq();
            }
        });

        blockCntBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockCnt();
            }
        });

        verifyCntBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyCnt();
            }
        });

        unblockReqBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unblockReq();
            }
        });

        unblockCntBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unblockCnt();
            }
        });
        add(topBtns, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        refreshUsers();
        cards.show(mainPanel, "REQ");
    }

    private DefaultTableModel makeModel() {
        return new DefaultTableModel(
                new String[]{"User ID", "Full Name", "Username", "Status", "Security"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private JPanel makeSection(String title, JTable table) {
        JPanel section = new JPanel(new BorderLayout(5, 5));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        section.add(titleLabel, BorderLayout.NORTH);
        section.add(scroll, BorderLayout.CENTER);

        return section;
    }

    private JPanel makeBlockedSection(String title, JTable table,
            JTextField searchField, JButton searchBtn, JButton resetBtn) {

        JPanel section = new JPanel(new BorderLayout(5, 5));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JPanel heading = new JPanel(new BorderLayout(5, 5));
        heading.setBackground(Color.WHITE);
        heading.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("User ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(resetBtn);

        heading.add(searchPanel, BorderLayout.EAST);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        section.add(heading, BorderLayout.NORTH);
        section.add(scroll, BorderLayout.CENTER);

        return section;
    }

    private void loadMessages() {
        msgModel.setRowCount(0);

        File file = new File("contactMessage.txt");

        if (!file.exists()) {
            return;
        }

        try {
            FileReader reader = new FileReader(file);
            int character;
            String line = "";

            while ((character = reader.read()) != -1) {
                if (character == '\n') {
                    String[] data = line.split("\\|", 5);

                    if (data.length >= 5) {
                        msgModel.addRow(new Object[]{
                            data[0], data[1], data[2], data[3], data[4]
                        });
                    }

                    line = "";
                } else if (character != '\r') {
                    line += (char) character;
                }
            }

            if (!line.isEmpty()) {
                String[] data = line.split("\\|", 5);

                if (data.length >= 5) {
                    msgModel.addRow(new Object[]{
                        data[0], data[1], data[2], data[3], data[4]
                    });
                }
            }

            reader.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Unable to load contact messages.");
        }
    }

    public void refreshUsers() {
        cntList.clear();
        reqList.clear();

        UserDataSaver.loadCounters(cntList);
        UserDataSaver.loadRequesters(reqList);

        reqModel.setRowCount(0);
        cntModel.setRowCount(0);
        blkReqModel.setRowCount(0);
        blkCntModel.setRowCount(0);

        for (int i = 0; i < reqList.size(); i++) {
            RequesterDetails req = reqList.get(i);

            Object[] row = {
                req.getUserID(), req.getFullName(), req.getUserName(),
                req.getStatus(), req.getSecurity()
            };

            if (req.getStatus().equals("BLOCKED")) {
                blkReqModel.addRow(row);
            } else {
                reqModel.addRow(row);
            }
        }

        for (int i = 0; i < cntList.size(); i++) {
            CounterDetails cnt = cntList.get(i);

            Object[] row = {
                cnt.getCounterUserId(), cnt.getFullName(), cnt.getUserName(),
                cnt.getStatus(), cnt.getSecurity()
            };

            if (cnt.getStatus().equals("BLOCKED")) {
                blkCntModel.addRow(row);
            } else {
                cntModel.addRow(row);
            }
        }
    }

    private void searchBlockedRequester() {
        String searchID = searchReqIDField.getText().trim();

        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a User ID.");
            return;
        }

        blkReqModel.setRowCount(0);

        for (int i = 0; i < reqList.size(); i++) {
            RequesterDetails req = reqList.get(i);

            if (req.getStatus().equals("BLOCKED")
                    && String.valueOf(req.getUserID()).equals(searchID)) {

                blkReqModel.addRow(new Object[]{
                    req.getUserID(), req.getFullName(), req.getUserName(),
                    req.getStatus(), req.getSecurity()
                });
            }
        }

        if (blkReqModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No blocked requester found for this User ID.");
        }
    }

    private void searchBlockedCounter() {
        String searchID = searchCntIDField.getText().trim();

        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a User ID.");
            return;
        }

        blkCntModel.setRowCount(0);

        for (int i = 0; i < cntList.size(); i++) {
            CounterDetails cnt = cntList.get(i);

            if (cnt.getStatus().equals("BLOCKED")
                    && String.valueOf(cnt.getCounterUserId()).equals(searchID)) {

                blkCntModel.addRow(new Object[]{
                    cnt.getCounterUserId(), cnt.getFullName(), cnt.getUserName(),
                    cnt.getStatus(), cnt.getSecurity()
                });
            }
        }

        if (blkCntModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No blocked counter found for this User ID.");
        }
    }

    private void blockReq() {
        int row = reqTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a requester.");
            return;
        }

        String username = reqModel.getValueAt(row, 2).toString();

        for (int i = 0; i < reqList.size(); i++) {
            RequesterDetails req = reqList.get(i);

            if (req.getUserName().equals(username)) {
                req.setStatus("BLOCKED");
                UserDataSaver.saveRequesters(reqList);
                refreshUsers();
                JOptionPane.showMessageDialog(this, "Requester account blocked.");
                return;
            }
        }
    }

    private void blockCnt() {
        int row = cntTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a counter.");
            return;
        }

        String username = cntModel.getValueAt(row, 2).toString();

        for (int i = 0; i < cntList.size(); i++) {
            CounterDetails cnt = cntList.get(i);

            if (cnt.getUserName().equals(username)) {
                cnt.setStatus("BLOCKED");
                UserDataSaver.saveCounters(cntList);
                refreshUsers();
                JOptionPane.showMessageDialog(this, "Counter account blocked.");
                return;
            }
        }
    }

    private void verifyCnt() {
        int row = cntTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a counter.");
            return;
        }

        String username = cntModel.getValueAt(row, 2).toString();

        for (int i = 0; i < cntList.size(); i++) {
            CounterDetails cnt = cntList.get(i);

            if (cnt.getUserName().equals(username)) {
                if (cnt.getSecurity().equals("VERIFIED")) {
                    JOptionPane.showMessageDialog(this, "This counter is already verified.");
                    return;
                }

                cnt.setSecurity("VERIFIED");
                UserDataSaver.saveCounters(cntList);
                refreshUsers();
                JOptionPane.showMessageDialog(this, "Counter account verified.");
                return;
            }
        }
    }

    private void unblockReq() {
        int row = blkReqTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a blocked requester.");
            return;
        }

        String username = blkReqModel.getValueAt(row, 2).toString();

        for (int i = 0; i < reqList.size(); i++) {
            RequesterDetails req = reqList.get(i);

            if (req.getUserName().equals(username) && req.getStatus().equals("BLOCKED")) {

                req.setStatus("ACTIVE");
                req.setFailedAttempts(0);
                UserDataSaver.saveRequesters(reqList);
                refreshUsers();
                JOptionPane.showMessageDialog(this, "Requester account unblocked.");
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Blocked requester account was not found.");
    }

    private void unblockCnt() {
        int row = blkCntTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a blocked counter.");
            return;
        }

        String username = blkCntModel.getValueAt(row, 2).toString();

        for (int i = 0; i < cntList.size(); i++) {
            CounterDetails cnt = cntList.get(i);

            if (cnt.getUserName().equals(username) && cnt.getStatus().equals("BLOCKED")) {

                cnt.setStatus("ACTIVE");
                cnt.setFailedAttempts(0);
                UserDataSaver.saveCounters(cntList);
                refreshUsers();
                JOptionPane.showMessageDialog(this, "Counter account unblocked.");
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Blocked counter account was not found.");
    }
}
