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

public class BrowseProducts extends JFrame{
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
    private UserHome obj;
    // Viewing stores

    // Retail class
  

    // Dummy testing variable
    private static String user = "Howie";
   



    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new BrowseProducts(user).setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public BrowseProducts() {

    }
   
    /**
     * 
     * @param userSes
     */
    public BrowseProducts(String userSes) {
        setSize(800, 800);
        setTitle("Products in this store");
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
        store = new JLabel("View all products");
        
        // store.setBackground(Color.BLACK);
        store.setBounds(450, 20, 200, 30);
        panel.add(store, BorderLayout.NORTH);
        add(store);

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("cs166 Howie and Christian/java/src/Images/download (1).png"));
        Dimension size = label.getPreferredSize();
        label.setBounds(20, 40, size.width, size.height);
        panel.add(label);
        //add(label);

        JLabel drinks = new JLabel("Drinks");
        drinks.setBounds(350, 50, 200, 30);
        panel.add(drinks, BorderLayout.NORTH);
        add(drinks);

        JLabel food = new JLabel("Food");
        food.setBounds(610, 50, 200, 30);
        panel.add(food, BorderLayout.NORTH);
        add(food);

        JButton btnNewButton = new JButton("Home");
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure?");
                // JOptionPane.setRootFrame(null);
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                    obj = new UserHome(user);
                    obj.setTitle("Home");
                    obj.setVisible(true);
                } else if (a == JOptionPane.NO_OPTION) {

                }
            }
        });
        btnNewButton.setBounds(880, 530, 100, 30);

        panel.add(btnNewButton, BorderLayout.SOUTH);
        add(btnNewButton);

        // try {

        //     String query = String.format(
        //             "select s.storeID, s.name, calculate_distance(u.latitude, u.longitude, s.latitude, s.longitude) as dist from users u, store s where u.userID = '%s' and calculate_distance(u.latitude, u.longitude, s.latitude, s.longitude) < 30",
        //             esql.getUserId());
        //     String url = "jdbc:postgresql://localhost:" + esql.getdbport() + "/" +
        //             esql.getdbname();
        //     _connection = DriverManager.getConnection(url, esql.getUser(),
        //             esql.getPassword());
        //     List<String> list = new ArrayList<>();
        //     PreparedStatement st = _connection.prepareStatement(query);
        //     ResultSet userNum = st.executeQuery();

        //     while (userNum.next()) {
        //         list.add(userNum.getString("Store.name"));

        //         stores = new JList(list.toArray());
        //         stores.setBackground(Color.BLACK);
        //         stores.setBounds(500, 200, 100, 30);

        //     }

        // } catch (SQLException sqlException) {
        //     sqlException.printStackTrace();
        // }

        //panel.add(stores);
        //add(stores);

    }
}



