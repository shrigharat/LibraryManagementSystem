package com.company.model;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.toedter.calendar.JDateChooser;
;

public class ReturnBook extends JFrame implements ActionListener {

    JPanel leftPanel, rightPanel;
    JDateChooser dateChooser;
    JTextField bookIdTf, isbnTf, bookEditionTf, bookPriceTf, bookPagesTf, doiTf;
    JTextField studentIdTf, courseTf, yearTf, semesterTf;
    JTextArea studentNameTA, studentBranchTA, bookNameTA, bookPublisherTA;
    JButton returnButton, backBtn, searchBtnBook, searchBtnStudent;

    private Color green = new Color(46, 79, 90);
    private Color orange = new Color(251, 115, 93);
    private Font normalFont = new Font(Font.MONOSPACED, Font.BOLD, 14);
    private Font tfFont = new Font(Font.MONOSPACED, Font.BOLD, 14);
    private Font mainLabelFont = new Font(Font.MONOSPACED, Font.BOLD, 25);

    private String sql;
    private Conn conn = new Conn();

    public ReturnBook() {
        setLayout(new BorderLayout());
        setResizable(false);
        setBounds(200,30, 800, 600);

        leftPanel = createPanel(400, 600, BorderLayout.WEST);
        leftPanel.setBorder(new MatteBorder(0, 0, 0, 5, orange));
        rightPanel = createPanel(400, 600, BorderLayout.CENTER);
        rightPanel.setBorder(new MatteBorder(0,0,0,5, orange));

        JLabel bookMainLabel = createLabel(leftPanel, "Book", 2);
        bookMainLabel.setFont(mainLabelFont);
        JLabel studentMainLabel = createLabel(rightPanel, "Student", 2);
        studentMainLabel.setFont(mainLabelFont);
        studentMainLabel.setBounds(10, 2, 200, 30);

        createLabel(leftPanel, "Book ID", 50);
        bookIdTf = createTextField(leftPanel, 140, 100, 50);
        bookIdTf.setForeground(green);
        bookIdTf.setBackground(Color.white);
        bookIdTf.setBorder(new LineBorder(green));
        bookIdTf.setCaretColor(green);
        bookIdTf.setEditable(true);
        searchBtnBook = createButton(leftPanel, "Search", 270, 50, 100, green, Color.white);

        createLabel(leftPanel, "Name", 90);
        bookNameTA = createTextArea(leftPanel, 90);

        createLabel(leftPanel, "ISBN", 180);
        isbnTf = createTextField(leftPanel, 270, 100, 180);

        createLabel(leftPanel, "Publisher", 220);
        bookPublisherTA = createTextArea(leftPanel, 220);

        createLabel(leftPanel, "Edition", 310);
        bookEditionTf = createTextField(leftPanel, 270, 100, 310);

        createLabel(leftPanel, "Price", 350);
        bookPriceTf = createTextField(leftPanel, 270, 100, 350);

        createLabel(leftPanel, "Pages", 390);
        bookPagesTf = createTextField(leftPanel, 270, 100, 390);

        createLabel(leftPanel, "Issue Date", 430);
        doiTf = createTextField(leftPanel, 270, 100, 430);

        createLabel(leftPanel, "Return Date", 470);
        dateChooser = new JDateChooser();
        dateChooser.setForeground(green);
        dateChooser.setBackground(Color.white);
        dateChooser.setBorder(new MatteBorder(2,2,2,2,green));
        dateChooser.setBounds(100, 470, 270, 30);
        dateChooser.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
        leftPanel.add(dateChooser);

        returnButton = createButton(leftPanel, "Return", 10, 520, 360, orange, Color.white);

        createLabel(rightPanel, "Stdnt ID", 50);
        studentIdTf = createTextField(rightPanel, 140, 100, 50);
        studentIdTf.setForeground(green);
        studentIdTf.setBackground(Color.white);
        studentIdTf.setBorder(new LineBorder(green));
        studentIdTf.setCaretColor(green);
        studentIdTf.setEditable(true);
        searchBtnStudent = createButton(rightPanel, "Search", 270, 50, 100, green, Color.white);

        createLabel(rightPanel, "Name", 90);
        studentNameTA = createTextArea(rightPanel, 90);

        createLabel(rightPanel, "Course", 180);
        courseTf = createTextField(rightPanel, 270, 100, 180);

        createLabel(rightPanel, "Branch", 220);
        studentBranchTA = createTextArea(rightPanel, 220);

        createLabel(rightPanel, "Year", 310);
        yearTf = createTextField(rightPanel, 270, 100, 310);

        createLabel(rightPanel, "Semester", 350);
        semesterTf = createTextField(rightPanel, 270, 100, 350);

        backBtn = createButton(rightPanel, "Back", 10, 520,360, Color.white, green);
        backBtn.setBorder(new MatteBorder(3,3,3,3,green));

    }

    private JButton createButton(JPanel panel, String title, int x, int y, int width, Color background, Color foreground) {
        JButton button = new JButton(title);
        button.addActionListener(this);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setBorder(new LineBorder(Color.white));
        button.setFont(normalFont);
        button.setBounds(x, y, width, 30);
        panel.add(button);
        return button;
    }

    private JTextField createTextField(JPanel panel, int width, int xVal, int yVal) {
        JTextField tf = new JTextField();
        tf.setFont(tfFont);
        tf.setEditable(false);
        tf.setBounds(xVal, yVal, width, 30);
        tf.setForeground(Color.white);
        tf.setBackground(green);
        tf.setCaretColor(Color.white);
        tf.setBorder(new LineBorder(Color.white, 1, true));
        panel.add(tf);
        return tf;
    }

    private JLabel createLabel(JPanel panel, String title, int yVal) {
        JLabel label = new JLabel(title);
        label.setFont(normalFont);
        label.setForeground(green);
        label.setBounds(10, yVal, 100, 30);
        panel.add(label);
        return label;
    }

    private JTextArea createTextArea(JPanel panel,int yVal) {
        JTextArea ta = new JTextArea();
        ta.setBackground(green);
        ta.setEditable(false);
        ta.setForeground(Color.white);
        ta.setFont(tfFont);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBounds(100, yVal, 270, 80);
        panel.add(ta);
        return ta;
    }

    private JPanel createPanel(int width, int height, String location) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));
        panel.setLayout(null);
        this.add(panel, location);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if(e.getSource() == searchBtnBook) {
                sql = "SELECT * FROM books WHERE book_id=?";
                PreparedStatement statement = conn.conn.prepareStatement(sql);
                statement.setString(1, bookIdTf.getText());
                ResultSet rs = statement.executeQuery();
                if(rs.next()) {
                    bookNameTA.setText(rs.getString(2));
                    isbnTf.setText(rs.getString(3));
                    bookPublisherTA.setText(rs.getString(4));
                    bookEditionTf.setText(rs.getString(5));
                    bookPriceTf.setText(rs.getString(6));
                    bookPagesTf.setText(rs.getString(7));
                    doiTf.setText(getReturnDate(bookIdTf.getText()));
                } else {
                    JOptionPane.showMessageDialog(this, "Couldn't find book. Either it is not added to the collection or issued by someone.");
                }
                rs.close();
                statement.close();
            }
            if(e.getSource() == searchBtnStudent) {
                sql = "SELECT * FROM students WHERE student_id=?";
                PreparedStatement statement = conn.conn.prepareStatement(sql);
                statement.setString(1, studentIdTf.getText());
                ResultSet rs = statement.executeQuery();
                if(rs.next()) {
                    studentNameTA.setText(rs.getString(2));
                    courseTf.setText(rs.getString(3));
                    studentBranchTA.setText(rs.getString(4));
                    yearTf.setText(rs.getString(5));
                    semesterTf.setText(rs.getString(6));
                } else {
                    JOptionPane.showMessageDialog(this, "Couldn't find student. Create a student from home menu.");
                }
                rs.close();
                statement.close();
            }
            if(e.getSource() == returnButton) {
                sql = "INSERT INTO returnBooks(book_id, student_id, bookName, studentName, course, branch, dateOfIssue, dateOfReturn) VALUES(?,?,?,?,?,?,?,?)";
                PreparedStatement statement = conn.conn.prepareStatement(sql);
                statement.setString(1, bookIdTf.getText());
                statement.setString(2, studentIdTf.getText());
                statement.setString(3, bookNameTA.getText());
                statement.setString(4, studentNameTA.getText());
                statement.setString(5, courseTf.getText());
                statement.setString(6, studentBranchTA.getText());
                statement.setString(7, doiTf.getText());
                statement.setString(8,((JTextField) dateChooser.getDateEditor().getUiComponent()).getText());

                int affectedRows = statement.executeUpdate();
                if(affectedRows > 0) {
                    delete();
                } else {
                    JOptionPane.showMessageDialog(this, "Couldn't issue book !");
                }
                statement.close();
            }
            if(e.getSource() == backBtn) {
                conn.close();
                dispose();
                new Home().setVisible(true);
            }
        } catch (SQLException exception) {
            System.out.println("Couldn't load book : " + exception.getMessage());
            JOptionPane.showMessageDialog(this, "Error loading data !");
        }
    }

    public void delete() {
        try {
            String sql = "DELETE FROM issueBooks where book_id=?";
            PreparedStatement statement = conn.conn.prepareStatement(sql);
            statement.setString(1, bookIdTf.getText());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0)
                JOptionPane.showMessageDialog(this, "Book Returned !");
            else
                JOptionPane.showMessageDialog(this, "Error occurred while returning !");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
    }

    private String getReturnDate(String book_id) {
        try{
            String sql = "SELECT dateOfIssue FROM issueBooks WHERE book_id = ?";
            PreparedStatement st = conn.conn.prepareStatement(sql);
            st.setString(1, book_id);
            ResultSet rs= st.executeQuery();
            if(rs.next()) {
                return rs.getString(1);
            } else {
                return "No date";
            }
        } catch (SQLException excpetion) {
            System.out.println("Couldn't get issue date : " + excpetion.getMessage());
            return "No date";
        }

    }
}

