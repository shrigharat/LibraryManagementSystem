package com.company.model;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpUser extends JFrame implements ActionListener {

    JPanel panel;
    JTextField nameTextField, userNameTextField,  answerTextField;
    JPasswordField passwordTextField;
    JButton createButton, backButton;
    JComboBox securityQuestion;

    private Color loginPurple = new Color(108,99,255);
    private Font normalFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
    private Font tfFOnt = new Font(Font.MONOSPACED, Font.BOLD, 15);
    private int xVal = 60;

    public SignUpUser() {
        setLayout(null);
        setBounds(200, 100, 1000, 600);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(loginPurple);
        panel.setBounds(0, 0, 1000, 600);
        setContentPane(panel);

        JLabel imageRight = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/images/signup.png"));
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        imageRight.setIcon(new ImageIcon(newImg));
        imageRight.setBounds(400, 0, 600,600);
        panel.add(imageRight);

        JLabel signUpLabel = new JLabel("Sign up");
        signUpLabel.setBounds(140, 5, 120, 40);
        signUpLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        signUpLabel.setForeground(Color.white);
        panel.add(signUpLabel);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(normalFont);
        nameLabel.setForeground(Color.white);
        nameLabel.setBounds(xVal, 45, 200, 35 );
        panel.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setFont(tfFOnt);
        nameTextField.setBounds(xVal, 80, 200, 35);
        nameTextField.setCaretColor(loginPurple);
        nameTextField.setBorder(new LineBorder(Color.white, 1, true));
        panel.add(nameTextField);

        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setFont(normalFont);
        userNameLabel.setForeground(Color.white);
        userNameLabel.setBounds(xVal, 125, 200, 35 );
        panel.add(userNameLabel);

        userNameTextField = new JTextField();
        userNameTextField.setFont(tfFOnt);
        userNameTextField.setBounds(xVal, 165, 200, 35);
        userNameTextField.setCaretColor(loginPurple);
        userNameTextField.setBorder(new LineBorder(Color.white, 1, true));
        panel.add(userNameTextField);

        JLabel passWordLabel = new JLabel("Password");
        passWordLabel.setFont(normalFont);
        passWordLabel.setForeground(Color.white);
        passWordLabel.setBounds(xVal, 210, 200, 35 );
        panel.add(passWordLabel);

        passwordTextField = new JPasswordField();
        passwordTextField.setFont(tfFOnt);
        passwordTextField.setBounds(xVal, 250, 200, 35);
        passwordTextField.setCaretColor(loginPurple);
        passwordTextField.setBorder(new LineBorder(Color.white, 1, true));
        panel.add(passwordTextField);

        JLabel comboBoxLabel = new JLabel("Security question");
        comboBoxLabel.setFont(normalFont);
        comboBoxLabel.setForeground(Color.white);
        comboBoxLabel.setBounds(xVal, 295, 200, 35 );
        panel.add(comboBoxLabel);

        securityQuestion = new JComboBox();
        securityQuestion.setModel(new DefaultComboBoxModel(
                new String[] {"Favourite fruit", "Name of pet", "Birthplace", "Favorite Color" })
        );
        securityQuestion.setFont(tfFOnt);
        securityQuestion.setBounds(xVal, 335, 200, 35);
        securityQuestion.setBorder(new LineBorder(Color.white, 1, true));
        securityQuestion.setBackground(new Color(57, 55, 78));
        securityQuestion.setForeground(Color.white);
        panel.add(securityQuestion);

        JLabel answerLabel = new JLabel("Answer");
        answerLabel.setFont(normalFont);
        answerLabel.setForeground(Color.white);
        answerLabel.setBounds(xVal, 380, 200, 35 );
        panel.add(answerLabel);

        answerTextField = new JTextField();
        answerTextField.setFont(tfFOnt);
        answerTextField.setBounds(xVal, 420, 200, 35);
        answerTextField.setCaretColor(loginPurple);
        answerTextField.setBorder(new LineBorder(Color.white, 1, true));
        panel.add(answerTextField);

        createButton = new JButton("Create");
        createButton.addActionListener(this);
        createButton.setBackground(Color.white);
        createButton.setForeground(loginPurple);
        createButton.setBorder(new LineBorder(loginPurple));
        createButton.setFont(normalFont);
        createButton.setBounds(xVal , 490, 90, 40);
        panel.add(createButton);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(57, 55, 78));
        backButton.setForeground(Color.white);
        backButton.setBorder(new LineBorder(Color.white));
        backButton.setFont(normalFont);
        backButton.setBounds(xVal + 120 , 490, 90, 40);
        panel.add(backButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Conn conn = new Conn();
            if(e.getSource() == createButton) {
                String sqlQuery = "SELECT * FROM accounts WHERE username = ?";
                PreparedStatement statementQuery = conn.conn.prepareStatement(sqlQuery);
                statementQuery.setString(1, userNameTextField.getText());
                ResultSet results = statementQuery.executeQuery();
                if(results.next()) {
                    JOptionPane.showMessageDialog(this,"Username Already exisits. Try a different one");
                    userNameTextField.setText("");
                } else {
                    String sqlInsert =
                            "INSERT INTO accounts(username, name, password, securityQ, securityAns)" +
                                    " VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statementInsert = conn.conn.prepareStatement(sqlInsert);
                    statementInsert.setString(1, userNameTextField.getText());
                    statementInsert.setString(2, nameTextField.getText());
                    statementInsert.setString(3, passwordTextField.getText());
                    statementInsert.setString(4, (String) securityQuestion.getSelectedItem());
                    statementInsert.setString(5, answerTextField.getText());

                    int affectedRows = statementInsert.executeUpdate();
                    if(affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Sign up successful !");
                        this.setVisible(false);
                        new LoginUser().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Sign up failed !");
                        userNameTextField.setText("");
                        nameTextField.setText("");
                        passwordTextField.setText("");
                        answerTextField.setText("");
                    }
                }
            }
            if(e.getSource() == backButton) {
                //do something
                this.setVisible(false);
                new LoginUser().setVisible(true);
            }

        } catch (SQLException exception) {
            System.out.println("Couldn't sign up : " + exception.getMessage());
            JOptionPane.showMessageDialog(this, "Sign up failed !");
            userNameTextField.setText("");
            nameTextField.setText("");
            passwordTextField.setText("");
            answerTextField.setText("");
        }
    }

}
