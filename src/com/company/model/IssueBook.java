package com.company.model;

import javax.swing.*;
import javax.swing.border.Border;
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

public class IssueBook extends JFrame implements ActionListener {

    JPanel leftPanel, centerPanel, rightPanel;
    JDateChooser dateChooser;
    JTextField bookIdTf, isbnTf, bookEditionTf, bookPriceTf, bookPagesTf;
    JTextField studentIdTf, courseTf, yearTf, semesterTf;
    JTextArea studentNameTA, studentBranchTA, bookNameTA, bookPublisherTA;
    JButton issueBtn, backBtn, searchBtnBook, searchBtnStudent;

    private Color green = new Color(46, 79, 90);
    private Color orange = new Color(251, 115, 93);
    private Font normalFont = new Font(Font.MONOSPACED, Font.BOLD, 16);
    private Font tfFont = new Font(Font.MONOSPACED, Font.BOLD, 14);
    private Font mainLabelFont = new Font(Font.MONOSPACED, Font.BOLD, 25);

    private String sql;
    private Conn conn = new Conn();

    public IssueBook() {
        setLayout(new BorderLayout());
        setResizable(false);
        setBounds(200,30, 1100, 600);

        leftPanel = createPanel(400, 600, BorderLayout.WEST);
        leftPanel.setBorder(new MatteBorder(0, 0, 0, 5, orange));
        rightPanel = createPanel(300, 600, BorderLayout.EAST);
        centerPanel = createPanel(400, 600, BorderLayout.CENTER);
        centerPanel.setBorder(new MatteBorder(0,0,0,5, orange));

        JLabel rightImage = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/images/screens/issueBookScreen.jpg"));
        Image image = imageIcon.getImage();
        imageIcon = new ImageIcon(image.getScaledInstance(800,600, Image.SCALE_SMOOTH));
        rightImage.setIcon(imageIcon);
        rightImage.setBounds(0,0,300,600);
        rightPanel.add(rightImage);

        JLabel bookMainLabel = createLabel(leftPanel, "Book", 2);
        bookMainLabel.setFont(mainLabelFont);
        JLabel studentMainLabel = createLabel(centerPanel, "Student", 2);
        studentMainLabel.setFont(mainLabelFont);
        studentMainLabel.setBounds(10, 2, 200, 30);

        createLabel(leftPanel, "Book ID", 70);
        bookIdTf = createTextField(leftPanel, 140, 100, 70);
        bookIdTf.setForeground(green);
        bookIdTf.setBackground(Color.white);
        bookIdTf.setBorder(new LineBorder(green));
        bookIdTf.setCaretColor(green);
        bookIdTf.setEditable(true);
        searchBtnBook = createButton(leftPanel, "Search", 270, 70, 100, green, Color.white);

        createLabel(leftPanel, "Name", 110);
        bookNameTA = createTextArea(leftPanel, 110);

        createLabel(leftPanel, "ISBN", 200);
        isbnTf = createTextField(leftPanel, 270, 100, 200);

        createLabel(leftPanel, "Publisher", 240);
        bookPublisherTA = createTextArea(leftPanel, 240);

        createLabel(leftPanel, "Edition", 330);
        bookEditionTf = createTextField(leftPanel, 270, 100, 330);

        createLabel(leftPanel, "Price", 370);
        bookPriceTf = createTextField(leftPanel, 270, 100, 370);

        createLabel(leftPanel, "Pages", 410);
        bookPagesTf = createTextField(leftPanel, 270, 100, 410);

        createLabel(leftPanel, "Date", 450);
        dateChooser = new JDateChooser();
        dateChooser.setForeground(green);
        dateChooser.setBackground(Color.white);
        dateChooser.setBorder(new MatteBorder(2,2,2,2,green));
        dateChooser.setBounds(100, 450, 270, 30);
        dateChooser.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
        leftPanel.add(dateChooser);

        issueBtn = createButton(leftPanel, "Issue", 10, 500, 360, orange, Color.white);

        createLabel(centerPanel, "Stdnt ID", 70);
        studentIdTf = createTextField(centerPanel, 140, 100, 70);
        studentIdTf.setForeground(green);
        studentIdTf.setBackground(Color.white);
        studentIdTf.setBorder(new LineBorder(green));
        studentIdTf.setCaretColor(green);
        studentIdTf.setEditable(true);
        searchBtnStudent = createButton(centerPanel, "Search", 270, 70, 100, green, Color.white);

        createLabel(centerPanel, "Name", 110);
        studentNameTA = createTextArea(centerPanel, 110);

        createLabel(centerPanel, "Course", 200);
        courseTf = createTextField(centerPanel, 270, 100, 200);

        createLabel(centerPanel, "Branch", 240);
        studentBranchTA = createTextArea(centerPanel, 240);

        createLabel(centerPanel, "Year", 330);
        yearTf = createTextField(centerPanel, 270, 100, 330);

        createLabel(centerPanel, "Semester", 370);
        semesterTf = createTextField(centerPanel, 270, 100, 370);
        backBtn = createButton(centerPanel, "Back", 10, 500,360, Color.white, green);
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
            if(e.getSource() == issueBtn) {
                sql = "INSERT INTO issueBooks(book_id, student_id, bookName, studentName, course, branch, dateOfIssue) VALUES(?,?,?,?,?,?,?)";
                PreparedStatement statement = conn.conn.prepareStatement(sql);
                statement.setString(1, bookIdTf.getText());
                statement.setString(2, studentIdTf.getText());
                statement.setString(3, bookNameTA.getText());
                statement.setString(4, studentNameTA.getText());
                statement.setString(5, courseTf.getText());
                statement.setString(6, studentBranchTA.getText());
                statement.setString(7, ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText());
                int affectedRows = statement.executeUpdate();
                if(affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Book issued !");
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
}
