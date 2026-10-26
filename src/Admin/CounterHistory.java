
package Admin;

import Data.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CounterHistory extends JPanel {

    private ArrayList<CounterDetails> counters = new ArrayList<>();
    private JPanel counterPanel = new JPanel();

    public CounterHistory() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5ee93"));

        JLabel title = new JLabel("Counter History");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(title, BorderLayout.NORTH);

        counterPanel.setLayout(new BoxLayout(counterPanel, BoxLayout.Y_AXIS));

        add(new JScrollPane(counterPanel), BorderLayout.CENTER);

        refreshHistory();
    }

    public void refreshHistory() {
        counters.clear();
        UserDataSaver.loadCounters(counters);

        counterPanel.removeAll();

        for (int i = 0; i < counters.size(); i++) {
            CounterDetails counter = counters.get(i);

            JPanel row = new JPanel(new BorderLayout());
            row.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JLabel info = new JLabel(
                    counter.getFullName()
                    + " | User ID: " + counter.getCounterUserId()
            );

            JButton viewHistory = new JButton("View History");

            viewHistory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showCounterHistory(counter);
                }
            });

            row.add(info, BorderLayout.CENTER);
            row.add(viewHistory, BorderLayout.EAST);

            counterPanel.add(row);
        }

        counterPanel.revalidate();
        counterPanel.repaint();
    }

    private void showCounterHistory(CounterDetails counter) {
        ArrayList<RequestedDocuments> requests = new ArrayList<>();
        ArrayList<RequesterDetails> requesters = new ArrayList<>();

        UserDataSaver.loadRequests(requests);
        UserDataSaver.loadRequesters(requesters);

        JFrame frame = new JFrame(
                "History - " + counter.getFullName()
        );

        String[] columns = {
            "Request ID",
            "Counter ID",
            "Counter Name",
            "Requester ID",
            "Requester Name",
            "Request Document"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < requests.size(); i++) {
            RequestedDocuments request = requests.get(i);

            if (request.getCounterID() == counter.getCounterUserId()) {
                String requesterName = "Unknown";

                for (int j = 0; j < requesters.size(); j++) {
                    RequesterDetails requester = requesters.get(j);

                    if (requester.getUserID() == request.getRequesterId()) {
                        requesterName = requester.getFName() + " " + requester.getLName();
                        break;
                    }
                }

                model.addRow(new Object[]{
                    request.getRequestId(),
                    request.getCounterID(),
                    request.getCounterName(),
                    request.getRequesterId(),
                    requesterName,
                    request.getDocument()
                });
            }
        }

        JTable table = new JTable(model);

        frame.add(new JScrollPane(table));
        frame.setSize(900, 400);
        frame.setLocationRelativeTo(this);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
