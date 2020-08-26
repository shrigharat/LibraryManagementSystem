package com.company.model;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumnModel;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.proteanit.sql.DbUtils;

public class BookDetails extends JFrame implements ActionListener, DocumentListener {
    JPanel panelLeft, panelRight;
    JTable table;
    JTextField searchField, bookIdTf, isbnTf, priceTf, pagesTf, editionTf;
    JButton deleteRowBtn, searchBtn, backBtn;
    JTextArea publisherTA, nameTA;

    private Color lightGreen = new Color(1, 189, 165);
    private Color mediumGreen = new Color(0, 177, 125);
    private Color darkGreen = new Color(2, 127, 70);
    private Color moreDarkGreen = new Color(57, 90, 100);
    private Font normalFont = new Font(Font.MONOSPACED, Font.BOLD, 20);
    private Font tfFont = new Font(Font.MONOSPACED, Font.BOLD, 14);

    private String sql;
    private Conn conn = new Conn();

    public BookDetails() {
        setLayout(new BorderLayout());
        setResizable(false);
        setBounds(200, 30, 1000, 600);

        panelLeft = new JPanel();
        panelLeft.setLayout(null);
        panelLeft.setBackground(lightGreen);
        panelLeft.setPreferredSize(new Dimension(600, 600));

        panelRight = new JPanel();
        panelRight.setLayout(null);
        panelRight.setPreferredSize(new Dimension(400, 600));
        panelRight.setBackground(Color.white);

        this.add(panelLeft, BorderLayout.WEST);
        this.add(panelRight, BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 100, 578, 450);
        scrollPane.setBackground(moreDarkGreen);
        panelLeft.add(scrollPane);

        table = new JTable();
        table.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            int row = table.getSelectedRow();
                            //Set pane to details
                            String bookId = table.getModel().getValueAt(row, 0).toString();

                            sql = "SELECT * FROM books WHERE book_id=" + "\"" + bookId + "\"";
                            PreparedStatement stmt = conn.conn.prepareStatement(sql);

                            ResultSet rs = stmt.executeQuery();
                            bookIdTf.setText(rs.getString(1));
                            nameTA.setText(rs.getString(2));
                            isbnTf.setText(rs.getString(3));
                            publisherTA.setText(rs.getString(4));
                            editionTf.setText(rs.getString(5));
                            priceTf.setText(rs.getString(6));
                            pagesTf.setText(rs.getString(7));
                            rs.close();
                            stmt.close();
                        } catch (SQLException exception) {
                            System.out.println("Couldn't load entry : " + exception.getMessage());
                        }
                    }
                }
        );
        table.setFont(tfFont);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setBackground(moreDarkGreen);
        table.setForeground(Color.white);
        table.setRowHeight(70);
        scrollPane.setViewportView(table);
        scrollPane.getViewport().setBackground(moreDarkGreen);

        JLabel mainLabel = new JLabel("Book Details");
        mainLabel.setForeground(Color.white);
        mainLabel.setBounds(10, 10, 200, 40);
        mainLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        panelLeft.add(mainLabel);

        backBtn = createButton("Back", 10, 50, 100, moreDarkGreen, Color.white);

        searchField = createTextField(panelLeft, 270, 120, 50);
        searchField.setBackground(Color.white);
        searchField.setEditable(true);
        searchField.setForeground(moreDarkGreen);
        searchField.setText("");
        Document doc = searchField.getDocument();
        doc.addDocumentListener(this);

        searchBtn = createButton("Search", 410, 50, 70, moreDarkGreen, Color.white);
        deleteRowBtn = createButton("Delete", 500, 50, 70, moreDarkGreen, Color.white);

        createLabel("Book ID", 20);
        bookIdTf = createTextField(panelRight, 200, 150, 20);
        createLabel("Name", 70);
        nameTA = createTextArea(70);
        createLabel("ISBN", 170);
        isbnTf = createTextField(panelRight, 200, 150, 170);
        createLabel("Publisher", 220);
        publisherTA = createTextArea(220);
        createLabel("Edition", 320);
        editionTf = createTextField(panelRight, 200, 150, 320);
        createLabel("Price", 370);
        priceTf = createTextField(panelRight, 200, 150, 370);
        createLabel("Pages", 420);
        pagesTf = createTextField(panelRight, 200, 150, 420);

        loadTable(conn);
    }

    private JButton createButton(String title, int x, int y, int width, Color background, Color foreground) {
        JButton button = new JButton(title);
        button.addActionListener(this);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setBorder(new LineBorder(Color.white));
        button.setFont(tfFont);
        button.setBounds(x, y, width, 30);
        panelLeft.add(button);
        return button;
    }

    private JTextField createTextField(JPanel panel, int width, int xVal, int yVal) {
        JTextField tf = new JTextField();
        tf.setFont(tfFont);
        tf.setEditable(false);
        tf.setBounds(xVal, yVal, width, 30);
        tf.setForeground(Color.white);
        tf.setBackground(moreDarkGreen);
        tf.setCaretColor(Color.white);
        tf.setBorder(new LineBorder(Color.white, 1, true));
        panel.add(tf);
        return tf;
    }

    private JLabel createLabel(String title, int yVal) {
        JLabel label = new JLabel(title);
        label.setFont(normalFont);
        label.setForeground(moreDarkGreen);
        label.setBounds(30, yVal, 150, 30);
        panelRight.add(label);
        return label;
    }

    private JTextArea createTextArea(int yVal) {
        JTextArea ta = new JTextArea();
        ta.setBackground(moreDarkGreen);
        ta.setEditable(false);
        ta.setForeground(Color.white);
        ta.setFont(tfFont);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBounds(150, yVal, 200, 80);
        panelRight.add(ta);
        return ta;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == deleteRowBtn) {
                sql = "DELETE FROM books WHERE name=" + "\'" + nameTA.getText() + "\'";
                PreparedStatement statement = conn.conn.prepareStatement(sql);

                int response = JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to delete this entry ?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (response == JOptionPane.YES_OPTION) {
                    int affectedRows = statement.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Book deleted successfully");
                    } else {
                        JOptionPane.showMessageDialog(this, "Book was not deleted");
                    }
                    loadTable(conn);
                }
                statement.close();
            }

            if (e.getSource() == searchBtn) {
                loadTable(conn);
            }

            if (e.getSource() == backBtn) {
                conn.close();
                dispose();
                new Home().setVisible(true);
            }

        } catch (SQLException exception) {
            System.out.println("Couldn't perform table action : " + exception.getMessage());
        }
    }

    private void loadTable(Conn conn) {
        sql = "SELECT book_id, name, isbn FROM books WHERE name LIKE ?";
        System.out.println("SQL statement : " + sql);
        try(PreparedStatement statement = conn.conn.prepareStatement(sql);) {
            statement.setString(1,"%" + searchField.getText() + "%");
            ResultSet results = statement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(results));
            results.close();
        } catch (Exception e) {
            System.out.println("Couldn't load table : " + e.getMessage());
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        loadTable(conn);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        loadTable(conn);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
