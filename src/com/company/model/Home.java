package com.company.model;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Home extends JFrame implements ActionListener {
    JPanel panel;
    JButton addBookButton, statisticsButton, addStudentButton, issueBookButton, returnBookButton, aboutUsButton;
    JMenuBar menuBar;

    private Color loginPurple = new Color(108,99,255);
    private Color darkPurple = new Color(47, 46, 65);
    private Font normalFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
    private Font tfFont = new Font(Font.MONOSPACED, Font.BOLD, 15);

    Conn conn = new Conn();

    public Home() {
        setLayout(null);
        setResizable(false);
        setBounds(200, 30, 1000, 600);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1000, 600);
        setContentPane(panel);

        menuBar = new JMenuBar();
        menuBar.setBorder(new MatteBorder(3,3,3,3, darkPurple));
        menuBar.setBounds(0,0,1000,50);
        menuBar.setBackground(loginPurple);
        panel.add(menuBar);

        JMenu exitMenu = new JMenu("Exit");
        exitMenu.setFont(normalFont);

        JMenuItem logout = getJMenuItem("Logout");
        exitMenu.add(logout);

        JMenuItem exit = getJMenuItem("Exit App");
        exitMenu.add(exit);

        JMenu recordsMenu = new JMenu("Records");
        recordsMenu.setFont(normalFont);

        JMenuItem bookDetails = getJMenuItem("Book Details");
        recordsMenu.add(bookDetails);

        JMenuItem studentDetails = getJMenuItem("Student Details");
        recordsMenu.add(studentDetails);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setBorder(null);
        helpMenu.setFont(normalFont);

        JMenuItem readMe = getJMenuItem("Read Me");
        helpMenu.add(readMe);

        JMenuItem aboutUs = getJMenuItem("About Us");
        helpMenu.add(aboutUs);

        menuBar.add(recordsMenu);
        menuBar.add(helpMenu);
        menuBar.add(exitMenu);

        getJLabel("/resources/images/addBook.png", 50, 70);
        addBookButton = getJButton("Add Book", 50, 250);

        getJLabel("/resources/images/statistics.png", 370, 70);
        statisticsButton = getJButton("Statistics", 370, 250);

        getJLabel("/resources/images/addstudent.png", 660, 70);
        addStudentButton = getJButton("Add Student",660, 250);

        getJLabel("/resources/images/issueBook.png", 50, 300);
        issueBookButton = getJButton("Issue Book", 50, 480);

        getJLabel("/resources/images/returnBook.png", 370, 300);
        returnBookButton = getJButton("Return Book",370, 480);

        getJLabel("/resources/images/aboutus.png", 660, 300);
        aboutUsButton = getJButton("About Us", 660, 480);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = e.getActionCommand();
        if(msg.equals("Logout")) {
            changeStatus();
            dispose();
            new LoginUser().setVisible(true);
        } else if(msg.equals("Exit App")) {
            System.exit(ABORT);
        } else if(msg.equals("Book Details")) {
            dispose();
            new BookDetails().setVisible(true);
        } else if(msg.equals("Student Details")) {
            dispose();
            new StudentDetails().setVisible(true);
        } else if(msg.equals("Read Me")) {
            new ReadMe().setVisible(true);
        } else if(msg.equals("About Us")) {
            new AboutUs().setVisible(true);
        }else if(e.getSource() == addBookButton) {
            dispose();
            new AddBook().setVisible(true);
        } else if(e.getSource() == issueBookButton) {
            dispose();
            new IssueBook().setVisible(true);
        } else if(e.getSource() == returnBookButton) {
            dispose();
            new ReturnBook().setVisible(true);
        } else if(e.getSource() == statisticsButton) {
            dispose();
            new Statistics().setVisible(true);
        } else if(e.getSource() == aboutUsButton) {
            new AboutUs().setVisible(true);
        } else if(e.getSource() == addStudentButton) {
            dispose();
            new AddStudent().setVisible(true);
        }
    }

    private JLabel getJLabel(String imgSrc, int x, int y) {
        JLabel label = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(imgSrc));
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(250 ,160, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(newImg));
        label.setBorder(new MatteBorder(2,2,2,2,darkPurple));
        label.setBounds(x,y, 250,160);
        panel.add(label);
        return label;
    }

    private JButton getJButton(String title, int x, int y){
        JButton button = new JButton(title);
        button.addActionListener(this); //action listener to listen to the changes
        button.setForeground(Color.white);  //set foreground
        button.setBorder(new MatteBorder(2,2,2,2,Color.white));
        button.setFont(normalFont);
        button.setBackground(loginPurple); //set background
        button.setBounds(x, y, 250, 30);
        panel.add(button);
        return button;
    }

    private JMenuItem getJMenuItem(String title) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(this);
        menuItem.setBackground(darkPurple);
        menuItem.setForeground(Color.white);
        menuItem.setFont(tfFont);
        return menuItem;
    }

    private void changeStatus() {
        conn = new Conn();
        String sql = "UPDATE logStatus SET status=0 WHERE id=1";
        try {
            PreparedStatement statement = conn.conn.prepareStatement(sql);
            int affectedRows = statement.executeUpdate();
            if(affectedRows > 0) {
                System.out.println("Status : Logged out");
            } else {
                System.out.println("Error");
            }
            statement.close();
            conn.close();
        } catch (SQLException exception) {
            System.out.println("Couldn't update status value");
        }
    }


}
