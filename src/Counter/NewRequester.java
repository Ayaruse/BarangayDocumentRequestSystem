package Counter;

import Data.*;
import Requester.RequesterMain;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class NewRequester extends JPanel {

    private JTable requesterTable;
    private DefaultTableModel tableModel;

    public NewRequester() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5ee93"));

        JLabel title = new JLabel("New Requesters");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(title, BorderLayout.NORTH);

        String[] columns = {"User ID", "First Name", "Last Name", "Username"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        requesterTable = new JTable(tableModel);
        requesterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadRequesters();

        add(new JScrollPane(requesterTable), BorderLayout.CENTER);

        requesterTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = requesterTable.getSelectedRow();

                if (row != -1) {
                    int userID = Integer.parseInt(tableModel.getValueAt(row, 0).toString());

                    for (int i = 0; i < RequesterMain.requesterUser.size(); i++) {
                        RequesterDetails requester
                                = RequesterMain.requesterUser.get(i);

                        if (requester.getUserID() == userID) {
                            showRequesterDetails(requester);
                            break;
                        }
                    }
                }
            }
        });
    }

    public void loadRequesters() {
        RequesterMain.requesterUser.clear();
        UserDataSaver.loadRequesters(RequesterMain.requesterUser);

        tableModel.setRowCount(0);

        for (int i = 0; i < RequesterMain.requesterUser.size(); i++) {
            RequesterDetails requester = RequesterMain.requesterUser.get(i);

            if (!requester.getSecurity().equalsIgnoreCase("VERIFIED")) {
                Object[] details = {requester.getUserID(),requester.getFName(),
                    requester.getLName(),
                    requester.getUserName()
                };

                tableModel.addRow(details);
            }
        }
    }

    private void showRequesterDetails(RequesterDetails requester) {
        JFrame frame = new JFrame("Requester Details");
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(this);
        frame.setLayout(new BorderLayout());

        String[] columns = {"Information", "Details"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[][] details = {
            {"User ID", String.valueOf(requester.getUserID())},
            {"First Name", requester.getFName()},
            {"Middle Name", requester.getMName()},
            {"Last Name", requester.getLName()},
            {"Suffix", requester.getSuffix()},
            {"Username", requester.getUserName()},
            {"Contact", requester.getContactNum()},
            {"Address", requester.getFullAddress()},
            {"Birthday", requester.getBirtMonth() + "/"
                + requester.getBirtDay() + "/" + requester.getBirtYear()}
        };

        for (int i = 0; i < details.length; i++) {
            model.addRow(details[i]);
        }

        JTable detailsTable = new JTable(model);
        detailsTable.setRowHeight(30);

        JPanel docsButton = new JPanel();

        JButton frontID = new JButton("Front ID");
        JButton backID = new JButton("Back ID");
        JButton birthCert = new JButton("Birth Certificate");

        frontID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPicture("Front ID", requester.getFrontID());
            }
        });

        backID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPicture("Back ID", requester.getBackID());
            }
        });

        birthCert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPicture("Birth Certificate", requester.getBrthCerti());
            }
        });

        docsButton.add(frontID);
        docsButton.add(backID);
        docsButton.add(birthCert);

        JPanel acceptDecline = new JPanel();

        JButton accept = new JButton("Accept");
        JButton decline = new JButton("Decline");

        accept.setPreferredSize(new Dimension(120, 35));
        decline.setPreferredSize(new Dimension(120, 35));

        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requester.setSecurity("VERIFIED");

                UserDataSaver.saveRequesters(RequesterMain.requesterUser);
                loadRequesters();

                frame.dispose();

                JOptionPane.showMessageDialog(NewRequester.this, "Requester verified successfully");
            }
        });

        decline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to decline this requester?",
                        "Confirm Decline",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < RequesterMain.requesterUser.size(); i++) {
                        if (RequesterMain.requesterUser.get(i).getUserID() == requester.getUserID()) {
                            RequesterMain.requesterUser.remove(i);
                            break;
                        }
                    }

                    UserDataSaver.saveRequesters(RequesterMain.requesterUser);
                    loadRequesters();

                    frame.dispose();

                    JOptionPane.showMessageDialog(NewRequester.this, "Requester declined and record deleted");
                }
            }
        });

        acceptDecline.add(accept);
        acceptDecline.add(decline);

        JPanel bottomButtons = new JPanel(new BorderLayout());
        bottomButtons.add(docsButton, BorderLayout.NORTH);
        bottomButtons.add(acceptDecline, BorderLayout.SOUTH);

        frame.add(new JScrollPane(detailsTable), BorderLayout.CENTER);
        frame.add(bottomButtons, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void showPicture(String title, String path) {
        JFrame frame = new JFrame(title);
        frame.setSize(500, 700);
        frame.setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage().getScaledInstance(450, 620, Image.SCALE_SMOOTH);

        JLabel picture = new JLabel(new ImageIcon(image), JLabel.CENTER);

        frame.add(new JScrollPane(picture));
        frame.setVisible(true);
    }
}
