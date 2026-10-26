package Admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AdminLogin {

    private JFrame loginWindow = new JFrame("Administrator System");
    private JPanel loginPanel = new JPanel();

    private JLabel username = new JLabel("Username");
    private JTextField userField = new JTextField();

    private JLabel password = new JLabel("Password");
    private JPasswordField passField = new JPasswordField();

    private JButton pressLogin = new JButton("Log-in");

    public AdminLogin() {
        username.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        username.setBounds(70, 50, 240, 30);
        userField.setBounds(70, 85, 350, 30);
        userField.setText("");

        password.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        password.setBounds(70, 115, 240, 30);
        passField.setBounds(70, 145, 350, 30);
        passField.setText("");

        pressLogin.setBounds(170, 250, 120, 30);
        pressLogin.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        pressLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent zy) {
                boolean loginSuccess = false;
                String name = userField.getText();
                String pssword = new String(passField.getPassword());

                if (name.equals(AdminMain.admin.getUsername())
                        && pssword.equals(AdminMain.admin.getPassword())) {
                    loginSuccess = true;
                }

                if (loginSuccess) {
                    JOptionPane.showMessageDialog(null, "Account Succesfully Login!");
                    loginWindow.dispose();
                    new AdminDashboard();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Username or Password");
                }
            }
        });

        loginPanel.setLayout(null);
        loginPanel.setBounds(0, 0, 500, 380);
        loginPanel.setBackground(Color.decode("#fffea8"));

        loginPanel.add(username);
        loginPanel.add(userField);
        loginPanel.add(password);
        loginPanel.add(passField);
        loginPanel.add(pressLogin);

        loginWindow.add(loginPanel);
        loginWindow.setSize(500, 380);
        loginWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginWindow.setResizable(false);
        loginWindow.setLocationRelativeTo(null);
        loginWindow.setVisible(true);
    }
}
