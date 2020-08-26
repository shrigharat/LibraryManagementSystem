package com.company.model;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class LoginUser extends JFrame implements ActionListener {
    private JPanel panel; //Create panel to mount the components on
    private JTextField textField; //Create textfield for entering username/email
    private JPasswordField passwordField; //Create password field
    private JButton b1, b2, b3; //Create login, signup, forgot password buttons
    private Color loginPurple = new Color(108,99,255);
    private Color loginYellow = new Color(255,202,1);
    private Color loginGreen = new Color(56, 90, 100);

    private Conn conn = new Conn();

    public LoginUser() {
        super("Library manager");
        //Setting layout to null as we will define the coordinates
        this.setLayout(null);
        this.setBackground(Color.white); //set background
        this.setBounds(100,100,1000,600); //set co-ordinates and height, width
        this.setResizable(false);

        panel = new JPanel(); //assign the panel
        panel.setBackground(Color.white); //set background
        setContentPane(panel);
        setBounds(0, 0, 1000, 600);
        panel.setBorder(new MatteBorder(5, 0, 5, 5, loginYellow)); //border color
        panel.setLayout(null); //Setting layout to null as we will define the coordinates

        JLabel imageLeft = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/images/library.jpg"));
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(600,600, Image.SCALE_SMOOTH);
        imageLeft.setIcon(new ImageIcon(newImg));
        imageLeft.setBounds(0, 0, 600, 600);
        imageLeft.setBorder(new MatteBorder(0,0,0,5, loginYellow));
        panel.add(imageLeft);

        JLabel labelUserNameLogin = new JLabel("Username"); //create labels for field
        labelUserNameLogin.setBounds(710, 89, 130, 30); //set co-ordinates and height, width
        labelUserNameLogin.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        labelUserNameLogin.setForeground(loginGreen);
        panel.add(labelUserNameLogin); //add object to panel, otherwise it won't show on the gui

        JLabel labelPasswordLogin = new JLabel("Password"); //create labels for field
        labelPasswordLogin.setBounds(710, 180, 130, 30); //set co-ordinates and height, width
        labelPasswordLogin.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        labelPasswordLogin.setForeground(loginGreen);
        panel.add(labelPasswordLogin); //add object to panel

        textField = new JTextField(); //assign the textfield
        textField.setFocusable(true);
        textField.setBorder(new LineBorder(loginYellow, 2, true));
        textField.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        textField.setBounds(710, 129, 200, 30);
        panel.add(textField); //add object to panel

        passwordField = new JPasswordField(); //assign the password field
        passwordField.setBorder(new LineBorder(loginYellow, 2, true));
        passwordField.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        passwordField.setBounds(710, 220, 200, 30);
        panel.add(passwordField); //add object to panel

        b1 = new JButton("Login"); //assign the button
        b1.addActionListener(this); //action listener to listen to the changes
        b1.setForeground(loginGreen);  //set foreground
        b1.setBorder(new LineBorder(loginGreen));
        b1.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        b1.setBackground(loginYellow); //set background
        b1.setBounds(710, 280, 90, 40); //set co-ordinates and height, width
        panel.add(b1); //add object to panel

        b2 = new JButton("Sign Up"); //assign the button
        b2.addActionListener(this); //action listener to listen to the changes
        b2.setBorder(new LineBorder(loginGreen, 2, true));
        b2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        b2.setForeground(loginGreen);
        b2.setBackground(Color.white); //set background
        b2.setBounds(810, 280, 90, 40); //set co-ordinates and height, width
        panel.add(b2); //add object to panel

        b3 = new JButton("Forgot Password"); //assign the button
        b3.addActionListener(this); //action listener to listen to the changes
        b3.setForeground(Color.white); //set foreground
        b3.setBackground(loginGreen); //set background
        b3.setBorder(new LineBorder(Color.white, 1, true));
        b3.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        b3.setBounds(710, 330, 190, 40); //set co-ordinates and height, width
        panel.add(b3); //add object to panel

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            Boolean status = false;
            try {
                String sql = "SELECT * FROM accounts WHERE username=? and password=?";
                PreparedStatement statement = conn.conn.prepareStatement(sql);

                statement.setString(1, textField.getText());
                statement.setString(2, new String(passwordField.getPassword()));

                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    changeStatus();
                    this.setVisible(false);
                    new Loading().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login!");
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        if (e.getSource() == b2) {
            setVisible(false);
            new SignUpUser().setVisible(true);
        }
        if (e.getSource() == b3) {
            setVisible(false);
            ForgotPassword forgot = new ForgotPassword();
            forgot.setVisible(true);
        }
    }

    private void changeStatus() {
        String sql = "UPDATE logStatus SET status=1 WHERE id=1";
        try {
            PreparedStatement statement = conn.conn.prepareStatement(sql);
            int affectedRows = statement.executeUpdate();
            if(affectedRows > 0) {
                System.out.println("Status : Logged IN");
            } else {
                System.out.println("Error");
            }
            statement.close();
            conn.close();
        } catch (SQLException exception) {
            System.out.println("Couldn't update status value : " + exception.getMessage());
        }
    }

}
