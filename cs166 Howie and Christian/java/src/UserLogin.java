import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class UserLogin extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JLabel label;
    private JPanel contentPane;
    JCheckBox showPassword;

    Retail esql;
    private Connection _connection = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserLogin frame = new UserLogin();
                    frame.setTitle("Login Form");
                    frame.setVisible(true);
                    frame.setBounds(10, 10, 370, 600);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public UserLogin() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // JLabel lblNewLabel = new JLabel("Login");
        // lblNewLabel.setForeground(Color.BLACK);
        // lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        // lblNewLabel.setBounds(423, 13, 273, 93);
        // contentPane.add(lblNewLabel);

        textField = new JTextField();

        textField.setBounds(150, 150, 150, 30);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();

        passwordField.setBounds(150, 220, 150, 30);
        contentPane.add(passwordField);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);

        lblUsername.setBounds(50, 150, 100, 30);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);

        lblPassword.setBounds(50, 220, 100, 30);
        contentPane.add(lblPassword);

        btnNewButton = new JButton("Login");

        btnNewButton.setBounds(50, 300, 100, 30);

        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(150, 250, 150, 30);
        contentPane.add(showPassword);

        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String name = textField.getText();
                String password = passwordField.getText();

                // Checkbox
                if (e.getSource() == showPassword) {
                    if (showPassword.isSelected()) {
                        passwordField.setEchoChar((char) 0);
                    } else {
                        passwordField.setEchoChar('*');
                    }

                }
                try {
                    String url = "jdbc:postgresql://localhost:" + esql.getdbport() + "/" + esql.getdbname();
                    _connection = DriverManager.getConnection(url, esql.getUser(), esql.getPassword());

                    PreparedStatement st = (PreparedStatement) _connection
                            .prepareStatement("Select * from USERS where name= '%s' and password= '%s' ");

                    String query = String.format("SELECT * FROM USERS WHERE name = '%s' AND password = '%s'", name,
                            password);

                    st.setString(1, name);
                    st.setString(2, password);

                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        dispose();
                        UserHome ah = new UserHome(name);
                        ah.setTitle("Welcome");
                        ah.setVisible(true);
                        JOptionPane.showMessageDialog(btnNewButton, "You have successfully logged in");
                    } else {
                        JOptionPane.showMessageDialog(btnNewButton, "Wrong Username & Password");
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }

            }
        });

        contentPane.add(btnNewButton);

        label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        contentPane.add(label);
    }
}