package Requester;

import Admin.*;
import Data.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class RequestDocument extends JPanel {

    private RequesterDetails loggedInUser;
    private RequesterDashboard dashboard;

    private JComboBox<String> documentChoice = new JComboBox<>();
    private JTextArea purposeField = new JTextArea();
    private JTextArea requirementsField = new JTextArea();
    private JButton next = new JButton("Continue");

    private ArrayList<DC_DocsToRequest> availableDocuments = new ArrayList<>();

    public RequestDocument(RequesterDetails account, RequesterDashboard dashboard) {
        loggedInUser = account;
        this.dashboard = dashboard;

        setLayout(null);
        setBackground(Color.decode("#f2e85a"));

        JLabel title = new JLabel("Request a Document");
        title.setFont(new Font("Times New Roman", Font.BOLD, 28));
        title.setBounds(40, 20, 400, 40);
        add(title);

        JLabel documentLabel = new JLabel("Select Document:");
        documentLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        documentLabel.setBounds(40, 85, 200, 30);
        add(documentLabel);

        documentChoice.setBounds(40, 120, 400, 35);
        add(documentChoice);

        JLabel purposeLabel = new JLabel("Purpose of Request:");
        purposeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        purposeLabel.setBounds(40, 175, 250, 30);
        add(purposeLabel);

        purposeField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        purposeField.setLineWrap(true);
        purposeField.setWrapStyleWord(true);

        JScrollPane purposeScroll = new JScrollPane(purposeField);
        purposeScroll.setBounds(40, 210, 400, 100);
        add(purposeScroll);

        JLabel requirementLabel = new JLabel("Required Documents:");
        requirementLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        requirementLabel.setBounds(500, 85, 300, 30);
        add(requirementLabel);

        requirementsField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        requirementsField.setEditable(false);
        requirementsField.setLineWrap(true);
        requirementsField.setWrapStyleWord(true);

        JScrollPane requirementsScroll = new JScrollPane(requirementsField);
        requirementsScroll.setBounds(500, 120, 400, 300);
        add(requirementsScroll);

        next.setBounds(780, 450, 120, 35);
        next.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (documentChoice.getSelectedIndex() == -1
                        || "No document to request".equals(documentChoice.getSelectedItem())) {

                    JOptionPane.showMessageDialog(null,
                            "There are no documents available to request.");
                    return;
                }

                if (purposeField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter the purpose of your request.");
                    return;
                }
                UserDataSaver.loadRequests(RequesterMain.documentRequests);

                int requestId = 1;
                for (int i = 0; i < RequesterMain.documentRequests.size(); i++) {
                    int existingId = RequesterMain.documentRequests.get(i).getRequestId();

                    if (existingId >= requestId) {
                        requestId = existingId + 1;
                    }
                }
                RequestedDocuments request = new RequestedDocuments(
                        requestId,
                        loggedInUser.getUserID(),
                        documentChoice.getSelectedItem().toString(),
                        purposeField.getText().trim(),
                        "PENDING"
                );

                RequesterMain.documentRequests.add(request);
                UserDataSaver.saveRequests(RequesterMain.documentRequests);
                JOptionPane.showMessageDialog(null,
                        "Request submitted successfully!"
                        + "\nRequest ID: " + request.getRequestId()
                        + "\nDocument: " + request.getDocument()
                        + "\nStatus: " + request.getStatus());
            }
        });
        add(next);

        loadAvailableDocuments();

        documentChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRequirements();
            }
        });
    }

    public void loadAvailableDocuments() {
        availableDocuments.clear();
        documentChoice.removeAllItems();

        try (BufferedReader read = new BufferedReader(new FileReader("availableDocument.txt"))) {

            String line;
            DC_DocsToRequest document = null;

            while ((line = read.readLine()) != null) {
                String[] data = line.split("\\|", 2);

                if (data.length == 2) {
                    if (data[0].equals("D")) {
                        document = new DC_DocsToRequest(data[1]);
                        availableDocuments.add(document);
                        documentChoice.addItem(data[1]);

                    } else if (data[0].equals("R") && document != null) {
                        document.addRequirement(new DC_Requirements(data[1]));
                    }
                }
            }

        } catch (IOException e) {
        }
        if (availableDocuments.isEmpty()) {
            documentChoice.addItem("No document to request");
        }
        showRequirements();
    }

    public void showRequirements() {
        requirementsField.setText("");

        int selected = documentChoice.getSelectedIndex();

        if (selected == -1 || availableDocuments.isEmpty() || "No document to request".equals(documentChoice.getSelectedItem())) {

            requirementsField.setText("No documents available.");
            return;
        }

        DC_DocsToRequest document = availableDocuments.get(selected);

        if (document.getRequired().isEmpty()) {
            requirementsField.setText("No specific requirements listed.");
            return;
        }

        for (int i = 0; i < document.getRequired().size(); i++) {
            requirementsField.append((i + 1) + ". " + document.getRequired().get(i).getRequirements() + "\n");
        }
    }

    public void disableComponents() {
        documentChoice.setEnabled(false);
        purposeField.setEnabled(false);
        requirementsField.setEnabled(false);
        next.setEnabled(false);
    }

    public void enableComponents() {
        documentChoice.setEnabled(true);
        purposeField.setEnabled(true);
        requirementsField.setEnabled(true);
        next.setEnabled(true);
    }
}
