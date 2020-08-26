package com.company.model;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

public class ForgotPassword extends JFrame implements ActionListener {

    JPanel panel;
    JTextField userNameTextField,  answerTextField;
    JTextField passwordTextField;
    JButton searchButton, backButton;
    JComboBox securityQuestion;

    private Color forgotBlue = new Color(100, 165, 255);
    private Font normalFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
    private Font tfFOnt = new Font(Font.MONOSPACED, Font.BOLD, 15);
    private int xVal = 60;

    public ForgotPassword() {
        setLayout(null);
        setBounds(200, 30, 1000, 600);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(forgotBlue);
        panel.setBounds(0, 0, 1000, 600);
        setContentPane(panel);

        JLabel imageRight = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/images/forgotpass.jpg"));
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        imageRight.setIcon(new ImageIcon(newImg));
        imageRight.setBounds(400, 0, 600,600);
        panel.add(imageRight);

        JLabel forgotPassLabel = new JLabel("Forgot Password");
        forgotPassLabel.setBounds(70, 5, 260, 40);
        forgotPassLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        forgotPassLabel.setForeground(Color.white);
        panel.add(forgotPassLabel);

        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setFont(normalFont);
        userNameLabel.setForeground(Color.white);
        userNameLabel.setBounds(xVal, 50, 200, 35 );
        panel.add(userNameLabel);

        userNameTextField = new JTextField();
        userNameTextField.setFont(tfFOnt);
        userNameTextField.setBounds(xVal, 90, 200, 35);
        userNameTextField.setCaretColor(forgotBlue);
        userNameTextField.setBorder(new LineBorder(Color.white, 1, true));
        panel.add(userNameTextField);

        JLabel comboBoxLabel = new JLabel("Security question");
        comboBoxLabel.setFont(normalFont);
        comboBoxLabel.setForeground(Color.white);
        comboBoxLabel.setBounds(xVal, 135, 200, 35 );
        panel.add(comboBoxLabel);

        securityQuestion = new JComboBox();
        securityQuestion.setModel(new DefaultComboBoxModel(
                new String[] {"Favourite fruit", "Name of pet", "Birthplace", "Favorite Color" })
        );
        securityQuestion.setFont(tfFOnt);
        securityQuestion.setBounds(xVal, 175, 200, 35);
        securityQuestion.setBorder(new LineBorder(Color.white, 1, true));
        securityQuestion.setBackground(new Color(57, 55, 78));
        securityQuestion.setForeground(Color.white);
        panel.add(securityQuestion);

        JLabel answerLabel = new JLabel("Answer");
        answerLabel.setFont(normalFont);
        answerLabel.setForeground(Color.white);
        answerLabel.setBounds(xVal, 220, 200, 35 );
        panel.add(answerLabel);

        answerTextField = new JTextField();
        answerTextField.setFont(tfFOnt);
        answerTextField.setBounds(xVal, 260, 200, 35);
        answerTextField.setCaretColor(forgotBlue);
        answerTextField.setBorder(new LineBorder(Color.white, 1, true));
        panel.add(answerTextField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchButton.setBackground(Color.white);
        searchButton.setForeground(forgotBlue);
        searchButton.setBorder(new LineBorder(forgotBlue));
        searchButton.setFont(normalFont);
        searchButton.setBounds(xVal+50 , 320, 100, 40);
        panel.add(searchButton);

        JLabel passWordLabel = new JLabel("Password");
        passWordLabel.setFont(normalFont);
        passWordLabel.setForeground(Color.white);
        passWordLabel.setBounds(xVal, 385, 200, 35 );
        panel.add(passWordLabel);

        passwordTextField = new JTextField();
        passwordTextField.setFont(tfFOnt);
        passwordTextField.setBounds(xVal, 425, 200, 35);
        passwordTextField.setCaretColor(forgotBlue);
        passwordTextField.setBorder(new LineBorder(Color.white, 1, true));
        panel.add(passwordTextField);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(5, 73, 156));
        backButton.setForeground(Color.white);
        backButton.setBorder(new LineBorder(Color.white));
        backButton.setFont(normalFont);
        backButton.setBounds(xVal + 50 , 480, 100, 40);
        panel.add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            Conn conn = new Conn();
            if( e.getSource() == searchButton) {
                String sql = "SELECT password FROM accounts WHERE username=? and securityQ = ? and securityAns = ?";
                PreparedStatement statement = conn.conn.prepareStatement(sql);

                statement.setString(1, userNameTextField.getText());
                statement.setString(2, (String) securityQuestion.getSelectedItem());
                statement.setString(3, answerTextField.getText());

                ResultSet results = statement.executeQuery();
                if(results.next()) {
                    passwordTextField.setText(results.getString(1));
                } else {
                    JOptionPane.showMessageDialog(this, "Couldn't find user");
                }
                statement.close();
            }
            if(e.getSource() == backButton) {
                conn.close();
                dispose();
                new LoginUser().setVisible(true);
            }
        } catch (SQLException exception) {
            System.out.println("Couldn't find user : " + exception.getMessage());
            JOptionPane.showMessageDialog(this, "Couldn't find user");
        }
    }
}
