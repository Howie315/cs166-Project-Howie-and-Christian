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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

import javax.swing.border.EmptyBorder;

public class PlaceOrder extends JFrame{
    
    private static final long serialVersionUID = 1L;
    // JFRAME stuff

    private JLabel store;
    private JList stores;
    private JButton btnNewButton;
    private JPanel panel;
    private JTextField userInput;
    private JTextField userInput2;
    private JTextField userInput3;

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
                    new PlaceOrder(user).setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PlaceOrder(String userses) {
        setSize(800, 800);
        setTitle("Place your order");
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
        store = new JLabel("Order as many as you like");
        
        // store.setBackground(Color.BLACK);
        store.setBounds(420, 20, 200, 30);
        panel.add(store, BorderLayout.NORTH);
        add(store);

        
        userInput = new JTextField();
        userInput.setColumns(30);
        userInput.setBounds(180, 150, 150, 30);
        panel.add(userInput);
        //add(userInput);

        JLabel store1 = new JLabel("Enter Store ID:");
        store1.setBackground(Color.BLACK);
        store1.setForeground(Color.BLACK);

        store1.setBounds(50, 150, 100, 30);
        panel.add(store1);


        userInput2 = new JTextField();
        userInput2.setBounds(180, 220, 150, 30);
        panel.add(userInput2);
        //add 
        JLabel product1 = new JLabel("Enter Product:");
        product1.setForeground(Color.BLACK);
        product1.setBackground(Color.CYAN);

        product1.setBounds(50, 220, 100, 30);
        panel.add(product1);

        userInput3 = new JTextField();
        userInput3.setBounds(180, 290, 150, 30);
        panel.add(userInput3);

       
        JLabel units = new JLabel("Enter Quantity:");
        units.setForeground(Color.BLACK);
        units.setBackground(Color.CYAN);

        units.setBounds(50, 290, 100, 30);
        panel.add(units);
        

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("cs166 Howie and Christian/java/src/Images/832773-200.png"));
        Dimension size = label.getPreferredSize();
        label.setBounds(800, 40, size.width, size.height);
        panel.add(label);
        //add(label);


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
        
        //add(userInput3);
    //     System.out.print("\tEnter Store ID:");
    //     String storeID = in.readLine();
    //     System.out.print("\tEnter Product Name:");
    //     String productName = in.readLine();
    // System.out.print("\tEnter Number of Units:");
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
