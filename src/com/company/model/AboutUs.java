package com.company.model;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AboutUs extends JFrame {
    JPanel panel;
    private Color labelColor = new Color(0, 69, 85);
    private Color textColor = new Color(1, 144, 147);
    private String aboutMe = "Hello. I am Shrishail Gharat, a 3rd Computer Science student at MGM college of engineering and technology. " +
            "I am a budding developer trying to explore various fields in computer science to find my area of interests and capability." +
            "This project was built with the purpose of applying JDBC and Java Swing API.";

    public AboutUs() {
        setLayout(new BorderLayout());
        setResizable(false);
        setBounds(250,150, 800, 500);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        panel.setBounds(0,0,800,500);
        setContentPane(panel);

        JLabel imageRight = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/images/screens/aboutUsScreenInked.jpg"));
        Image image = imageIcon.getImage();
        imageRight.setIcon(new ImageIcon(image.getScaledInstance(500, 500, Image.SCALE_SMOOTH)));
        imageRight.setBounds(300,0,500,500);
        panel.add(imageRight);

        JLabel mainLabel = new JLabel("About me");
        mainLabel.setBounds(80, 0, 200, 50);
        mainLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        mainLabel.setForeground(labelColor);
        panel.add(mainLabel);

        JTextArea ta = new JTextArea();
        ta.setBounds(10, 50, 280, 420);
        ta.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        ta.setText(aboutMe);
        ta.setEditable(false);
        ta.setForeground(textColor);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        panel.add(ta);

    }
}
