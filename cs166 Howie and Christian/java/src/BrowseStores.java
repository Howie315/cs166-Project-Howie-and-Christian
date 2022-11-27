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
import javax.swing.*;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class BrowseStores extends JFrame {

    private static final long serialVersionUID = 1L;
    // JFRAME stuff

    private JLabel store;
    private JList stores;
    private JButton btnNewButton;
    private JPanel panel;

    // Viewing stores

    // Retail class
    private Retail esql;
    private Connection _connection = null;

    // Dummy testing variable
    private static String user = "Howie";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new BrowseStores(user).setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BrowseStores() {

    }

    /**
     * 
     * @param userSes
     */
    public BrowseStores(String userSes) {
        setSize(800, 800);
        setTitle("Stores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);
        // String viewStore = storeLabel.getText();

        /*
         * This is for placing the button and the list
         */
        store = new JLabel("Stores near you");
        store.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        // store.setBackground(Color.BLACK);
        store.setBounds(425, 70, 200, 30);
        panel.add(store, BorderLayout.NORTH);
        add(store);

        try {

            String query = String.format(
                    "select s.storeID, s.name, calculate_distance(u.latitude, u.longitude, s.latitude, s.longitude) as dist from users u, store s where u.userID = '%s' and calculate_distance(u.latitude, u.longitude, s.latitude, s.longitude) < 30",
                    esql.getUserId());
            String url = "jdbc:postgresql://localhost:" + esql.getdbport() + "/" +
                    esql.getdbname();
            _connection = DriverManager.getConnection(url, esql.getUser(),
                    esql.getPassword());
            List<String> list = new ArrayList<>();
            PreparedStatement st = _connection.prepareStatement(query);
            ResultSet userNum = st.executeQuery();

            while (userNum.next()) {
                list.add(userNum.getString("Store.name"));

                stores = new JList(list.toArray());
                stores.setBackground(Color.BLACK);
                stores.setBounds(500, 200, 100, 30);

            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        panel.add(stores);
        add(stores);

    }
}
