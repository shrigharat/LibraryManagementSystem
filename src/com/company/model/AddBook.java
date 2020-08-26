package com.company.model;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class AddBook extends JFrame implements ActionListener {
    JPanel panel;
    JTextField bookIdTf, nameTf, isbnTf, publisherTf, priceTf, editionTf, pagesTf;
    JButton addBookButton, backButton;

    private Color addBookRed = new Color(255, 79, 90);
    private Color addBookGreen = new Color(56, 90, 100);
    private Font normalFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
    private Font tfFont = new Font(Font.MONOSPACED, Font.BOLD, 15);
    private int xVal = 650;


    public AddBook() {
        setLayout(null);
        setResizable(false);
        setBounds(200, 30, 1000, 600);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1000, 600);
        panel.setBackground(addBookRed);
        setContentPane(panel);

        JLabel imageLeft = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/images/screens/addBookScreen.jpg"));
        Image image = imageIcon.getImage();
        imageLeft.setIcon(new ImageIcon(image.getScaledInstance(600,600, Image.SCALE_SMOOTH)));
        imageLeft.setBounds(0, 0, 600, 600);
        panel.add(imageLeft);

        JLabel mainLabel = new JLabel("Add Book");
        mainLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        mainLabel.setForeground(Color.white);
        mainLabel.setBounds(740, 5, 200, 40);
        panel.add(mainLabel);

        createLabel("Book ID", 50);
        bookIdTf = createTextField(75);
        randomNumber();
        createLabel("Name", 110);
        nameTf = createTextField(135);
        createLabel("ISBN", 170);
        isbnTf = createTextField(195);
        createLabel("Publisher", 230);
        publisherTf = createTextField(255);
        createLabel("Edition", 290);
        editionTf = createTextField(315);
        createLabel("Price", 350);
        priceTf = createTextField(375);
        createLabel("Pages", 410);
        pagesTf = createTextField(435);

        addBookButton = createButton("Add", 700, 475, Color.white, addBookGreen);
        backButton = createButton("Back", 700, 515, addBookGreen, addBookRed);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            Conn conn = new Conn();
            if(e.getSource() == addBookButton) {
                String sql = "INSERT INTO books(book_id, name, isbn, publisher, edition, price, pages) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement statement = conn.conn.prepareStatement(sql);
                statement.setString(1, bookIdTf.getText());
                statement.setString(2, nameTf.getText());
                statement.setString(3, isbnTf.getText());
                statement.setString(4, publisherTf.getText());
                statement.setString(5, editionTf.getText());
                statement.setString(6, priceTf.getText());
                statement.setString(7, pagesTf.getText());

                int affectedRows = statement.executeUpdate();
                if(affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Book added successfully");
                    clearTextFields();
                    randomNumber();
                } else {
                    JOptionPane.showMessageDialog(this, "Couldn't add book");
                }
                statement.close();
            }
            if(e.getSource() == backButton) {
                clearTextFields();
                conn.close();
                dispose();
                new Home().setVisible(true);
            }
        } catch (SQLException exception) {
            System.out.println("Couldn't add Book : "+ exception.getMessage());
        }
    }

    private JLabel createLabel(String title, int yVal) {
        JLabel label = new JLabel(title);
        label.setFont(normalFont);
        label.setForeground(Color.white);
        label.setBounds(xVal, yVal, 200, 20);
        panel.add(label);
        return label;
    }

    private JTextField createTextField(int yVal) {
        JTextField tf = new JTextField();
        tf.setFont(tfFont);
        tf.setBounds(xVal, yVal, 270, 30);
        tf.setCaretColor(Color.white);
        tf.setBackground(addBookGreen);
        tf.setForeground(Color.white);
        tf.setBorder(new LineBorder(Color.white, 1, true));
        panel.add(tf);
        return tf;
    }

    private JButton createButton(String title, int x, int y, Color background, Color foreground) {
        JButton button = new JButton(title);
        button.addActionListener(this);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setBorder(new LineBorder(Color.white));
        button.setFont(normalFont);
        button.setBounds(x , y, 200, 30);
        panel.add(button);
        return button;
    }

    private void clearTextFields() {
        bookIdTf.setText("");
        nameTf.setText("");
        isbnTf.setText("");
        publisherTf.setText("");
        pagesTf.setText("");
        priceTf.setText("");
        editionTf.setText("");
    }

    private void randomNumber() {
        Random rd = new Random();
        bookIdTf.setText("" + (rd.nextInt(10000000) + 1));
    }
}
