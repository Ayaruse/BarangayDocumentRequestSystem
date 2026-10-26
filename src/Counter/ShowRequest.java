
package Counter;

import Data.*;
import Requester.RequesterMain;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class ShowRequest extends JPanel {

    private JTable requestTable, preparingTable;
    private DefaultTableModel tableModel, preparingModel;
    private RequestQueue requestQueue = new RequestQueue();
    private CounterDetails loggedInCounter;
    private JButton receivedBtn = new JButton("Mark Documents Received");

    private JPanel pendingPanel = new JPanel(new BorderLayout(10, 10));
    private JPanel preparingPanel = new JPanel(new BorderLayout(10, 10));
    private JPanel tablesPanel = new JPanel(new GridLayout(2, 1, 10, 10));

    public ShowRequest(CounterDetails account) {
        loggedInCounter = account;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.decode("#f5ee93"));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Document Requests");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Request ID", "Requester ID", "Document", "Purpose", "Status"};

        tableModel = createTableModel(columns);
        preparingModel = createTableModel(columns);

        requestTable = createTable(tableModel);
        preparingTable = createTable(preparingModel);

        pendingPanel.setBackground(Color.WHITE);
        pendingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel pendingTitle = new JLabel("Pending Requests");
        pendingTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
        pendingPanel.add(pendingTitle, BorderLayout.NORTH);
        pendingPanel.add(new JScrollPane(requestTable), BorderLayout.CENTER);

        preparingPanel.setBackground(Color.WHITE);
        preparingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel preparingTitle = new JLabel("Requests Being Prepared");
        preparingTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
        preparingPanel.add(preparingTitle, BorderLayout.NORTH);
        preparingPanel.add(new JScrollPane(preparingTable), BorderLayout.CENTER);

        tablesPanel.setBackground(Color.decode("#f5ee93"));
        tablesPanel.add(pendingPanel);
        tablesPanel.add(preparingPanel);
        add(tablesPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(Color.WHITE);
        receivedBtn.setEnabled(false);
        actionPanel.add(receivedBtn);
        preparingPanel.add(actionPanel, BorderLayout.SOUTH);

        requestTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = requestTable.rowAtPoint(e.getPoint());
                if (row == -1) return;

                int requestId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                RequestedDocuments selectedRequest = findRequest(requestId);

                if (selectedRequest == null || !selectedRequest.getStatus().equals("PENDING")) return;

                UserDataSaver.loadRequesters(RequesterMain.requesterUser);

                for (int i = 0; i < RequesterMain.requesterUser.size(); i++) {
                    RequesterDetails requester = RequesterMain.requesterUser.get(i);

                    if (requester.getUserID() == selectedRequest.getRequesterId()) {
                        new RequestDetails(selectedRequest, requester, ShowRequest.this);
                        return;
                    }
                }

                JOptionPane.showMessageDialog(ShowRequest.this, "Requester information not found.");
            }
        });

        preparingTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) return;
            receivedBtn.setEnabled(preparingTable.getSelectedRow() != -1);
        });

        receivedBtn.addActionListener(e -> markDocumentsReceived());

        loadRequests();
    }

    private DefaultTableModel createTableModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setFont(new Font("Arial", Font.PLAIN, 14));
                return cell;
            }
        });
        return table;
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

            if (!found) RequesterMain.documentRequests.add(saved);
        }

        requestQueue.syncPendingRequests(RequesterMain.documentRequests);
        tableModel.setRowCount(0);
        preparingModel.setRowCount(0);

        for (int i = 0; i < RequesterMain.documentRequests.size(); i++) {
            RequestedDocuments request = RequesterMain.documentRequests.get(i);
            String status = request.getStatus();

            Object[] rowData = {
                request.getRequestId(),
                request.getRequesterId(),
                request.getDocument(),
                request.getPurpose(),
                status
            };

            if (status.equals("PENDING")) {
                tableModel.addRow(rowData);
            } else if (status.equals("Preparing")) {
                preparingModel.addRow(rowData);
            }
        }

        requestTable.clearSelection();
        preparingTable.clearSelection();
        receivedBtn.setEnabled(false);
    }

    private RequestedDocuments findRequest(int requestId) {
        for (int i = 0; i < RequesterMain.documentRequests.size(); i++) {
            RequestedDocuments request = RequesterMain.documentRequests.get(i);
            if (request.getRequestId() == requestId) return request;
        }
        return null;
    }

    public boolean isNextRequest(int requestId) {
        return requestQueue.isNextRequest(requestId);
    }

    public boolean acceptRequest(RequestedDocuments request) {
        if (!requestQueue.isNextRequest(request.getRequestId())) {
            JOptionPane.showMessageDialog(this, "Please process the oldest pending request first.");
            return false;
        }

        requestQueue.dequeue();
        request.setStatus("Preparing");
        request.setCounterID(loggedInCounter.getCounterUserId());
        request.setCounterName(loggedInCounter.getFullName());

        UserDataSaver.saveRequests(RequesterMain.documentRequests);
        loadRequests();

        JOptionPane.showMessageDialog(this, "Request accepted");
        return true;
    }

    public boolean declineRequest(RequestedDocuments request) {
        if (!requestQueue.isNextRequest(request.getRequestId())) {
            JOptionPane.showMessageDialog(this, "Please process the oldest pending request first");
            return false;
        }

        requestQueue.dequeue();
        request.setStatus("Declined");
        request.setCounterID(loggedInCounter.getCounterUserId());
        request.setCounterName(loggedInCounter.getFullName());

        UserDataSaver.saveRequests(RequesterMain.documentRequests);
        loadRequests();

        JOptionPane.showMessageDialog(this, "Request declined.");
        return true;
    }

    private void markDocumentsReceived() {
        int row = preparingTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request from the Requests Being Prepared table.");
            return;
        }

        int requestId = Integer.parseInt(preparingModel.getValueAt(row, 0).toString());
        RequestedDocuments request = findRequest(requestId);

        if (request == null) {
            JOptionPane.showMessageDialog(this, "Request not found.");
            loadRequests();
            return;
        }

        if (!request.getStatus().equals("Preparing")) {
            JOptionPane.showMessageDialog(this, "Only requests with Preparing status can be marked as received");
            loadRequests();
            return;
        }

        request.setStatus("DOCUMENTS RECEIVED");
        UserDataSaver.saveRequests(RequesterMain.documentRequests);
        loadRequests();

        JOptionPane.showMessageDialog(this, "Required supporting documents marked as received");
    }

    public void disableTable() {
        requestTable.setEnabled(false);
        preparingTable.setEnabled(false);
        receivedBtn.setEnabled(false);
    }

    public void enableTable() {
        requestTable.setEnabled(true);
        preparingTable.setEnabled(true);
    }
}
