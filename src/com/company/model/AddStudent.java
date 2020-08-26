package com.company.model;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStudent extends JFrame implements ActionListener {
    JPanel panel;
    JTextField nameTf, studentIdTf;
    JComboBox courseCB, branchCB, yearCB, semesterCB;
    JButton addStudentButton, backButton;

    private Color addStudentBlue = new Color(14, 125, 254);
    private Color addStudentAccent = new Color(7, 85, 183);
    private Font normalFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
    private Font tfFont = new Font(Font.MONOSPACED, Font.BOLD, 15);
    private int xVal = 650;

    private String[] courseArray = {"B.E", "B.P.T", "Dental", "B.D.S", "M.B.B.S", "J.D"};
    private String[] branchArray = {"Computer", "Biomedical", "Civil", "Mechanical", "Chemical", "Information Technology", "Electronics and Telecommunications", "Electrical", "Instrumentation", "Not Applicable"};
    private String[] yearArray = {"1", "2", "3", "4", "5"};
    private String[] semArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    public AddStudent() {
        setLayout(null);
        setResizable(false);
        setBounds(200, 100, 1000, 600);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1000, 600);
        panel.setBackground(addStudentBlue);
        setContentPane(panel);

        JLabel imageLeft = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/images/screens/addStudentScreen.jpg"));
        Image image = imageIcon.getImage();
        imageLeft.setIcon(new ImageIcon(image.getScaledInstance(600,600, Image.SCALE_SMOOTH)));
        imageLeft.setBounds(0, 0, 600, 600);
        panel.add(imageLeft);

        JLabel mainLabel = new JLabel("Add Book");
        mainLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        mainLabel.setForeground(Color.white);
        mainLabel.setBounds(740, 5, 200, 40);
        panel.add(mainLabel);

        createLabel("Student UID", 55);
        studentIdTf = createTextField(80);
        createLabel("Name", 115);
        nameTf = createTextField(140);
        createLabel("Course", 175);
        courseCB = createComboBox(addStudentAccent, 200, courseArray);
        createLabel("Branch", 235);
        branchCB = createComboBox(addStudentAccent, 260, branchArray);
        createLabel("Year", 295);
        yearCB = createComboBox(addStudentAccent, 320, yearArray);
        createLabel("Semester", 355);
        semesterCB = createComboBox(addStudentAccent, 390, semArray);

        addStudentButton = createButton("Add student", 700, 450, Color.white, addStudentBlue);
        backButton = createButton("Back", 700, 500, Color.white, addStudentBlue);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            Conn conn = new Conn();
            if(e.getSource() == addStudentButton) {
                String sql = "INSERT INTO students(student_id, name, course, branch, year, semester) VALUES(?,?,?,?,?,?)";
                PreparedStatement statement = conn.conn.prepareStatement(sql);

                statement.setString(1, studentIdTf.getText());
                statement.setString(2, nameTf.getText());
                statement.setString(3, (String) courseCB.getSelectedItem());
                statement.setString(4, (String) branchCB.getSelectedItem());
                statement.setString(5, (String) yearCB.getSelectedItem());
                statement.setString(6, (String) semesterCB.getSelectedItem());

                int affectedRows = statement.executeUpdate();
                if(affectedRows > 0) {
                    clearTextFields();
                    JOptionPane.showMessageDialog(this, "Student Added Successfully");
                } else {
                    JOptionPane.showMessageDialog(this, "Couldn't add student");
                }
            }
            if(e.getSource()== backButton) {
                clearTextFields();
                dispose();
                new Home().setVisible(true);
            }
        } catch(SQLException exception) {
            System.out.println("Couldn't add student : " + exception.getMessage());
            JOptionPane.showMessageDialog(this, "Couldn't add student");
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
        tf.setCaretColor(addStudentAccent);
        tf.setBackground(Color.white);
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

    private JComboBox createComboBox(Color background, int yVal, String[] options) {
        JComboBox cb = new JComboBox();
        cb.setModel(new DefaultComboBoxModel(options));
        cb.setFont(tfFont);
        cb.setBounds(xVal, yVal, 270, 30);
        cb.setBorder(new LineBorder(Color.white, 1, true));
        cb.setBackground(background);
        cb.setForeground(Color.white);
        panel.add(cb);
        return cb;
    }

    private void clearTextFields() {
        nameTf.setText("");
        studentIdTf.setText("");
        courseCB.setSelectedItem(null);
        branchCB.setSelectedItem(null);
        yearCB.setSelectedItem(null);
        semesterCB.setSelectedItem(null);
    }

}
