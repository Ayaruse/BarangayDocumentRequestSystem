package Requester;

import Data.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MyRequests extends JPanel {

    private RequesterDetails loggedInUser;
    private JTable requestsTable;
    private DefaultTableModel tableModel;
    private JPanel preparingPanel = new JPanel();
    private JPanel messagesPanel = new JPanel();
    private JScrollPane messageScroll;
    private Timer refresh;

    public MyRequests(RequesterDetails account) {
        loggedInUser = account;

        setLayout(null);
        setBackground(Color.decode("#f2e85a"));

        JLabel title = new JLabel("My Requests");
        title.setFont(new Font("Times New Roman", Font.BOLD, 28));
        title.setBounds(40, 25, 400, 40);
        add(title);

        preparingPanel.setLayout(new BorderLayout(5, 5));
        preparingPanel.setBackground(Color.WHITE);
        preparingPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        preparingPanel.setBounds(20, 80, 860, 120);

        JLabel preparingTitle = new JLabel("Documents Being Prepared");
        preparingTitle.setFont(new Font("Arial", Font.BOLD, 16));
        preparingPanel.add(preparingTitle, BorderLayout.NORTH);

        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        messagesPanel.setBackground(Color.WHITE);

        messageScroll = new JScrollPane(messagesPanel);
        messageScroll.setBorder(null);
        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        preparingPanel.add(messageScroll, BorderLayout.CENTER);

        preparingPanel.setVisible(false);
        add(preparingPanel);

        String[] columns = {"Request ID", "Document", "Purpose", "Status"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        requestsTable = new JTable(tableModel);
        requestsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        requestsTable.setRowHeight(28);
        requestsTable.setBackground(Color.WHITE);
        requestsTable.setShowVerticalLines(false);

        JScrollPane tableScroll = new JScrollPane(requestsTable);
        tableScroll.setBounds(20, 210, 860, 235);
        add(tableScroll);

        loadRequests();
        refresh = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRequests();
            }
        });
        refresh.start();;
    }

    public void loadRequests() {
        ArrayList<RequestedDocuments> savedRequests = new ArrayList<>();
        UserDataSaver.loadRequests(savedRequests);

        for (int i = 0; i < savedRequests.size(); i++) {
            RequestedDocuments saved = savedRequests.get(i);
            boolean found = false;

            for (int j = 0; j < RequesterMain.documentRequests.size(); j++) {
                RequestedDocuments current = RequesterMain.documentRequests.get(j);

                if (current.getRequestId() == saved.getRequestId()) {
                    current.setStatus(saved.getStatus());
                    current.setCounterID(saved.getCounterID());
                    current.setCounterName(saved.getCounterName());
                    found = true;
                    break;
                }
            }

            if (!found) {
                RequesterMain.documentRequests.add(saved);
            }
        }

        tableModel.setRowCount(0);
        messagesPanel.removeAll();

        int preparingCount = 0;

        for (int i = 0; i < RequesterMain.documentRequests.size(); i++) {
            RequestedDocuments request = RequesterMain.documentRequests.get(i);

            if (request.getRequesterId() == loggedInUser.getUserID()) {
                String status = request.getStatus();
                String displayedStatus = status;

                if ("Preparing".equals(status)) {
                    displayedStatus = "Preparing - Pass the required document";

                    JPanel messagePanel = new JPanel(new BorderLayout());
                    messagePanel.setBackground(Color.decode("#fff9cc"));
                    messagePanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
                    messagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
                    messagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    JLabel message = new JLabel(
                            "Your document " + request.getDocument()
                            + " is preparing. Please pass the required document."
                    );
                    message.setFont(new Font("Arial", Font.PLAIN, 14));
                    messagePanel.add(message, BorderLayout.CENTER);

                    messagesPanel.add(messagePanel);
                    messagesPanel.add(Box.createVerticalStrut(5));

                    preparingCount++;
                }

                tableModel.addRow(new Object[]{request.getRequestId(), request.getDocument(), request.getPurpose(), displayedStatus});
            }
        }

        preparingPanel.setVisible(preparingCount > 0);

        messagesPanel.revalidate();
        messagesPanel.repaint();
        revalidate();
        repaint();
    }
}
