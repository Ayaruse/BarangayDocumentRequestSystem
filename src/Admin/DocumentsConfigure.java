package Admin;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DocumentsConfigure extends JPanel {

    private ArrayList<DC_DocsToRequest> docsToreq = new ArrayList<>();
    private ArrayList<DC_Requirements> currentRequirements = new ArrayList<>();

    private JPanel configurePanel = new JPanel();
    private JLabel documentLabel = new JLabel("Document Name");
    private JTextField documentField = new JTextField();
    private JButton addRequirement = new JButton("+");
    private JPanel requirementsPanel = new JPanel();
    private JButton saveDocument = new JButton("Save Document");
    private JScrollPane configureScroll = new JScrollPane(configurePanel);

    private JPanel documentsPanel = new JPanel();
    private JPanel selectPanel = new JPanel();
    private JPanel savedReqPanel = new JPanel();
    private JPanel savedButtonsPanel = new JPanel();

    private JComboBox<String> documentChoice = new JComboBox<>();
    private JScrollPane documentsScroll = new JScrollPane(savedReqPanel);

    private JButton addSavedReq = new JButton("Add Requirement");
    private JButton deleteDocument = new JButton("Delete Document");

    public DocumentsConfigure() {
        setBackground(Color.decode("#f5ee93"));
        setLayout(new GridLayout(1, 2, 20, 0));

        configurePanel.setLayout(new BoxLayout(configurePanel, BoxLayout.Y_AXIS));
        configurePanel.setBackground(Color.WHITE);

        configurePanel.add(documentLabel);
        configurePanel.add(documentField);
        configurePanel.add(addRequirement);

        requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.Y_AXIS));
        requirementsPanel.setBackground(Color.WHITE);
        configurePanel.add(requirementsPanel);
        configurePanel.add(saveDocument);

        configureScroll.setBorder(null);
        configureScroll.getViewport().setBackground(Color.WHITE);

        documentsPanel.setLayout(new BorderLayout(10, 10));
        documentsPanel.setBackground(Color.WHITE);

        selectPanel.setLayout(new BorderLayout(5, 5));
        selectPanel.setBackground(Color.WHITE);

        JLabel savedDocumentLabel = new JLabel("Select Saved Document");

        selectPanel.add(savedDocumentLabel, BorderLayout.NORTH);
        selectPanel.add(documentChoice, BorderLayout.CENTER);

        savedReqPanel.setLayout(new BoxLayout(savedReqPanel, BoxLayout.Y_AXIS));
        savedReqPanel.setBackground(Color.WHITE);

        documentsScroll.setBorder(BorderFactory.createTitledBorder("Requirements"));
        documentsScroll.getViewport().setBackground(Color.WHITE);

        savedButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        savedButtonsPanel.setBackground(Color.WHITE);
        savedButtonsPanel.add(addSavedReq);
        savedButtonsPanel.add(deleteDocument);

        documentsPanel.add(selectPanel, BorderLayout.NORTH);
        documentsPanel.add(documentsScroll, BorderLayout.CENTER);
        documentsPanel.add(savedButtonsPanel, BorderLayout.SOUTH);

        add(configureScroll);
        add(documentsPanel);

        loadDocuments();

        addRequirement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String requirement = JOptionPane.showInputDialog(DocumentsConfigure.this, "Enter Requirement:");

                if (requirement != null && !requirement.trim().isEmpty()) {

                    requirement = requirement.trim();

                    DC_Requirements newRequirement = new DC_Requirements(requirement);
                    currentRequirements.add(newRequirement);

                    JPanel requirementRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    requirementRow.setBackground(Color.WHITE);

                    JLabel requirementLabel = new JLabel(requirement);
                    JButton removeRequirement = new JButton("Remove");

                    requirementRow.add(requirementLabel);
                    requirementRow.add(removeRequirement);
                    requirementsPanel.add(requirementRow);

                    removeRequirement.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            currentRequirements.remove(newRequirement);
                            requirementsPanel.remove(requirementRow);

                            requirementsPanel.revalidate();
                            requirementsPanel.repaint();
                        }
                    });

                    requirementsPanel.revalidate();
                    requirementsPanel.repaint();
                }
            }
        });

        saveDocument.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = documentField.getText().trim();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            DocumentsConfigure.this, "Enter a document name.");
                    return;
                }

                for (int i = 0; i < docsToreq.size(); i++) {

                    if (docsToreq.get(i).getDocsToReq().equalsIgnoreCase(name)) {
                        JOptionPane.showMessageDialog(
                                DocumentsConfigure.this, "This document already exists.");
                        return;
                    }
                }

                DC_DocsToRequest document = new DC_DocsToRequest(name);

                for (int i = 0; i < currentRequirements.size(); i++) {
                    document.addRequirement(currentRequirements.get(i));
                }

                docsToreq.add(document);
                saveDocuments();

                documentChoice.addItem(name);
                documentChoice.setSelectedItem(name);

                documentField.setText("");
                currentRequirements.clear();

                requirementsPanel.removeAll();
                requirementsPanel.revalidate();
                requirementsPanel.repaint();

                showSavedRequirements();
            }
        });

        documentChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSavedRequirements();
            }
        });

        addSavedReq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selected = documentChoice.getSelectedIndex();

                if (selected == -1) {
                    JOptionPane.showMessageDialog(
                            DocumentsConfigure.this, "Select a document first.");
                    return;
                }

                String requirement = JOptionPane.showInputDialog(
                        DocumentsConfigure.this, "Enter Requirement:");

                if (requirement != null && !requirement.trim().isEmpty()) {

                    docsToreq.get(selected).addRequirement(
                            new DC_Requirements(requirement.trim()));

                    saveDocuments();
                    showSavedRequirements();
                }
            }
        });

        deleteDocument.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selected = documentChoice.getSelectedIndex();

                if (selected == -1) {
                    JOptionPane.showMessageDialog(
                            DocumentsConfigure.this, "Select a document first.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        DocumentsConfigure.this,
                        "Delete this document and all its requirements?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {

                    docsToreq.remove(selected);
                    saveDocuments();

                    documentChoice.removeItemAt(selected);
                    showSavedRequirements();
                }
            }
        });
    }

    public void showSavedRequirements() {

        savedReqPanel.removeAll();

        int selected = documentChoice.getSelectedIndex();

        if (selected == -1) {

            savedReqPanel.add(new JLabel("No document selected."));

        } else {

            DC_DocsToRequest document = docsToreq.get(selected);

            if (document.getRequired().isEmpty()) {

                savedReqPanel.add(new JLabel("No requirements listed."));

            } else {

                for (int i = 0; i < document.getRequired().size(); i++) {

                    DC_Requirements requirement = document.getRequired().get(i);

                    JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    row.setBackground(Color.WHITE);

                    JLabel requirementLabel = new JLabel(
                            (i + 1) + ". " + requirement.getRequirements());

                    JButton delete = new JButton("Delete");

                    row.add(requirementLabel);
                    row.add(delete);
                    savedReqPanel.add(row);

                    delete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            document.getRequired().remove(requirement);
                            saveDocuments();
                            showSavedRequirements();
                        }
                    });
                }
            }
        }

        savedReqPanel.revalidate();
        savedReqPanel.repaint();

        documentsPanel.revalidate();
        documentsPanel.repaint();
    }

    public void saveDocuments() {

        try (BufferedWriter write = new BufferedWriter(
                new FileWriter("availableDocument.txt"))) {

            for (int i = 0; i < docsToreq.size(); i++) {

                DC_DocsToRequest document = docsToreq.get(i);

                write.write("D|" + document.getDocsToReq());
                write.newLine();

                for (int j = 0; j < document.getRequired().size(); j++) {

                    write.write("R|" + document.getRequired().get(j).getRequirements());
                    write.newLine();
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving documents.");
        }
    }

    public void loadDocuments() {

        docsToreq.clear();
        documentChoice.removeAllItems();

        try (BufferedReader read = new BufferedReader(
                new FileReader("availableDocument.txt"))) {

            String line;
            DC_DocsToRequest document = null;

            while ((line = read.readLine()) != null) {

                String[] data = line.split("\\|", 2);

                if (data.length == 2) {

                    if (data[0].equals("D")) {

                        document = new DC_DocsToRequest(data[1]);

                        docsToreq.add(document);
                        documentChoice.addItem(data[1]);

                    } else if (data[0].equals("R") && document != null) {

                        document.addRequirement(new DC_Requirements(data[1]));
                    }
                }
            }

        } catch (IOException e) {
        }

        showSavedRequirements();
    }
}
