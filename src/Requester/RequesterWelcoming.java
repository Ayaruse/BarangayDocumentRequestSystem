package Requester;

import Admin.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RequesterWelcoming {

    private JFrame userWindow = new JFrame("Welcome Requester");
    private JLabel brgy = new JLabel("Barangay Document");
    private JLabel req = new JLabel("Request System");
    private JPanel TextContainer = new JPanel();

    private JButton login = new JButton("Log-in");
    private JButton crtAcc = new JButton("Create Account");
    private JLabel msg = new JLabel("New here? Select \"Create Account\"");

    private JPanel centerPANEL = new JPanel();

    public RequesterWelcoming() {
        brgy.setFont(new Font("Times New Roman", Font.BOLD, 50));
        brgy.setBounds(10, 0, 460, 50);

        req.setFont(new Font("Times New Roman", Font.BOLD, 50));
        req.setBounds(50, 50, 460, 50);

        TextContainer.setLayout(null);
        TextContainer.setBackground(Color.decode("#f2e85a"));
        TextContainer.setBounds(20, 20, 460, 100);

        login.setBounds(190, 150, 120, 30);//250-60=190;150-20=130
        login.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ey) {
                new RequesterLogin();
                userWindow.dispose();

            }
        });

        crtAcc.setBounds(165, 233, 170, 30);//250-60=190;150-20=130
        crtAcc.setFont(new Font("Times New Roman", Font.BOLD, 20));
        crtAcc.setForeground(Color.BLACK);
        crtAcc.setBorderPainted(false);
        crtAcc.setContentAreaFilled(false);
        crtAcc.setFocusPainted(false);
        crtAcc.setOpaque(false);

        crtAcc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent bzy) {
                new RequesterCreateAccount();
                userWindow.dispose();

            }
        });
        msg.setForeground(Color.BLUE);
        msg.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        msg.setBounds(115, 250, 460, 50);

        centerPANEL.setLayout(null);
        centerPANEL.setBounds(440, 220, 500, 300); //690-250=440/ 370-150=220
        centerPANEL.setBackground(Color.decode("#fffaad"));

//        ImageIcon bgpic = new ImageIcon("C:\\Users\\jjc\\Pictures\\IT203\\bgpic.png");
//        JLabel bgContainer = new JLabel(bgpic);
        BackgroundPicture callBG = new BackgroundPicture();
        String path = callBG.getBackground();
        JLabel bgContainer;
        if (path != null) {
            ImageIcon bgpic = new ImageIcon(path);

            Image smooth = bgpic.getImage().getScaledInstance(1380, 730, Image.SCALE_SMOOTH);

            bgContainer = new JLabel(new ImageIcon(smooth));
        } else {
            bgContainer = new JLabel();
        }

        TextContainer.add(brgy);
        TextContainer.add(req);

        centerPANEL.add(TextContainer);
        centerPANEL.add(login);
        centerPANEL.add(crtAcc);
        centerPANEL.add(msg);

        bgContainer.add(centerPANEL);

        userWindow.add(bgContainer);

        userWindow.setSize(1380, 730);
        userWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userWindow.setResizable(false);
        userWindow.setLocationRelativeTo(null);
        userWindow.setVisible(true);

    }

}
