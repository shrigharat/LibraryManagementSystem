package com.company.model;

import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class Loading extends JFrame implements Runnable {
    private JPanel panel;
    private JProgressBar progressBar;
    int counter;
    Conn conn;
    Thread thread;
    private Color loginPurple = new Color(108,99,255);

    private void setUploading() {
        thread.start();
    }

    public Loading() {
        super("Loading");
        thread = new Thread((Runnable) this);

        setBounds(400, 100, 500,400);

        panel = new JPanel();
        panel.setBounds(0, 0, 500, 400);
        setContentPane(panel);
        panel.setLayout(null);

        JLabel mainLabel = new JLabel("Library Manager V1.0.0");
        mainLabel.setForeground(loginPurple);
        mainLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        mainLabel.setBounds(75, 100, 350, 40);
        panel.add(mainLabel);

        progressBar = new JProgressBar();
        progressBar.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(44, 23, 150));
        progressBar.setBounds(150, 200, 200, 40);
        panel.add(progressBar);

        setUploading();
    }

    @Override
    public void run() {
        try {
            for(int i=0; i<200; i++) {
                counter += 1;
                int maximum = progressBar.getMaximum();
                int value = progressBar.getValue();
                if(value < maximum) {
                    progressBar.setValue(progressBar.getValue() + 1);
                } else {
                    i = 201;
                    setVisible(false);
                    new Home().setVisible(true);
                }
                Thread.sleep(20);
            }
        } catch(Exception e) {
            System.out.println("Error in thread run : " + e.getMessage());
        }
    }
}
