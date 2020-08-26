package com.company.model;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.DataBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Statistics extends JFrame implements ActionListener {
    JPanel panel;
    JScrollPane topPane, bottomPane;
    JTextField searchIssue, searchReturn;
    JTable issueBookTable, returnBookTable;
    JButton backBtn, searchIssueBtn, searchReturnBtn;

    private Color loginPurple = new Color(108, 99, 255);
    private Color darkPurple = new Color(47, 46, 65);
    private Font normalFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
    private Font tfFont = new Font(Font.MONOSPACED, Font.BOLD, 15);

    private String sql;
    private Conn conn = new Conn();

    public Statistics() {
        setLayout(null);
        setBounds(200, 30, 1000, 700);
        setResizable(false);

        panel = new JPanel();
        panel.setBounds(0, 0, 1000, 700);
        panel.setLayout(null);
        setContentPane(panel);

        createLabel("Issue Book Table", 5);
        backBtn = createButton("Back", 10, 30, 190);
        searchIssue = createTextField(500, 250, 30);
        searchIssueBtn = createButton("Search", 800, 30, 180);

        topPane = new JScrollPane();
        topPane.setBounds(10, 70, 970, 250);
        panel.add(topPane);

        issueBookTable = new JTable();
        issueBookTable.setFont(tfFont);
        issueBookTable.setForeground(Color.white);
        issueBookTable.setBackground(darkPurple);
        topPane.setViewportView(issueBookTable);
        topPane.getViewport().setBackground(darkPurple);

        createLabel("Return Book Table", 335);
        searchReturn = createTextField(500, 250, 360);
        searchReturnBtn = createButton("Search", 800, 360, 180);

        bottomPane = new JScrollPane();
        bottomPane.setBounds(10, 400, 970, 250);
        panel.add(bottomPane);

        returnBookTable = new JTable();
        returnBookTable.setFont(tfFont);
        returnBookTable.setBackground(darkPurple);
        returnBookTable.setForeground(Color.white);
        bottomPane.setViewportView(returnBookTable);
        bottomPane.getViewport().setBackground(darkPurple);

        loadIssueTable();
        loadReturnTable();

    }

    private JButton createButton(String title, int x, int y, int width) {
        JButton button = new JButton(title);
        button.addActionListener(this);
        button.setBackground(Color.white);
        button.setForeground(darkPurple);
        button.setBorder(new LineBorder(darkPurple));
        button.setFont(normalFont);
        button.setBounds(x, y, width, 30);
        panel.add(button);
        return button;
    }

    private JTextField createTextField(int width, int xVal, int yVal) {
        JTextField tf = new JTextField();
        tf.setFont(tfFont);
        tf.setBounds(xVal, yVal, width, 30);
        tf.setForeground(Color.white);
        tf.setBackground(darkPurple);
        tf.setCaretColor(Color.white);
        tf.setBorder(new LineBorder(darkPurple, 1, true));
        panel.add(tf);
        return tf;
    }

    private JLabel createLabel(String title, int yVal) {
        JLabel label = new JLabel(title);
        label.setFont(normalFont);
        label.setForeground(darkPurple);
        label.setBounds(350, yVal, 300, 20);
        panel.add(label);
        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchIssueBtn) {
            loadIssueTable();
        }
        if (e.getSource() == searchReturnBtn) {
            loadReturnTable();
        }
        if (e.getSource() == backBtn) {
            conn.close();
            dispose();
            new Home().setVisible(true);
        }
    }

    private void loadIssueTable() {
        try {
            sql = "SELECT book_id, student_id, bookName, studentName, dateOfIssue FROM issueBooks WHERE bookName LIKE ?";
            PreparedStatement statement = conn.conn.prepareStatement(sql);
            statement.setString(1, "%" + searchIssue.getText() + "%");
            ResultSet results = statement.executeQuery();
            issueBookTable.setModel(DbUtils.resultSetToTableModel(results));
            results.close();
            statement.close();
        } catch (SQLException exc) {
            System.out.println("Couldn't load Table : " + exc.getMessage());
        }
    }

    private void loadReturnTable() {
        try {
            sql = "SELECT book_id, student_id, bookName, studentName, dateOfReturn FROM returnBooks WHERE bookName LIKE ?";
            PreparedStatement statement = conn.conn.prepareStatement(sql);
            statement.setString(1, "%" + searchReturn.getText() + "%");
            ResultSet results = statement.executeQuery();
            returnBookTable.setModel(DbUtils.resultSetToTableModel(results));
            results.close();
            statement.close();
        } catch (SQLException exc) {
            System.out.println("Couldn't load Table : " + exc.getMessage());
        }
    }
}
