package Counter;

import Data.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RequestDetails extends JFrame {

    private ShowRequest showRequest;

    public RequestDetails(RequestedDocuments request, RequesterDetails requester,
            ShowRequest showRequest) {
        this.showRequest = showRequest;

        setTitle("Request Details");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea details = new JTextArea();
        details.setEditable(false);

        details.setText(
                "REQUESTER INFORMATION\n"
                + "Requester ID: " + requester.getUserID() + "\n"
                + "Name: " + requester.getFName() + " " + requester.getLName() + "\n"
                + "Contact: " + requester.getContactNum() + "\n"
                + "Address: " + requester.getFullAddress() + "\n\n"
                + "DOCUMENT REQUEST\n"
                + "Request ID: " + request.getRequestId() + "\n"
                + "Document: " + request.getDocument() + "\n"
                + "Purpose: " + request.getPurpose() + "\n"
                + "Status: " + request.getStatus()
        );

        add(new JScrollPane(details), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton accept = new JButton("Accept");
        JButton reject = new JButton("Reject");
        JButton close = new JButton("Close");

        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showRequest.acceptRequest(request)) {
                    dispose();
                }
            }
        });

        reject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showRequest.declineRequest(request)) {
                    dispose();
                }
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttons.add(accept);
        buttons.add(reject);
        buttons.add(close);
        add(buttons, BorderLayout.SOUTH);

        setVisible(true);
    }
}
