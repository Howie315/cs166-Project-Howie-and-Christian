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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class UserHome extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel top;
    private JButton btnNewButton;
    private JButton storesButton;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu aboutMenu;
    private JMenuItem exitItem;
    JMenu logout;
    JMenuItem logoutItem;

    private JMenuItem about;
    private static Retail esql;
    // Classes
    private BrowseStores store;
    private BrowseProducts product;

    private UserLogin login;
    private static String user = "Howie";
    private Connection _connection = null;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //new UserHome(user).setVisible(true);
                    UserHome frame = new UserHome();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UserHome() {

    }

    /**
     * Create the Frame
     * 
     * @param userSes
     */
    public UserHome(String userSes) {
        setSize(1500, 1500);
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        /*
         * Setting the menu
         */
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        aboutMenu = new JMenu("About");

        exitItem = new JMenuItem("Exit");
        about = new JMenuItem("About Contributers");

        about.addActionListener(new AboutMenuListener());
        exitItem.addActionListener(new ExitListener());

        menuBar.add(fileMenu);
        fileMenu.add(exitItem);

        menuBar.add(aboutMenu);
        aboutMenu.add(about);

        setJMenuBar(menuBar);

        // Intiating all of the Jpanels
        // store = new BrowseStores(esql.getUser());
        // product = new BrowseProducts();

        /*
         * Setting View product and View stores
         */

        // contentPane = new JPanel(new BorderLayout());
        // top = new JPanel(new BorderLayout());
        // contentPane.add(store, BorderLayout.WEST);
        // contentPane.add(product, BorderLayout.EAST);
        // contentPane.add(top, BorderLayout.NORTH);
        // add(top);
        // add(contentPane);

        btnNewButton = new JButton("Logout");
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure?");
                // JOptionPane.setRootFrame(null);
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                    UserLogin obj = new UserLogin();
                    obj.setTitle("User-Login");
                    obj.setVisible(true);
                } else if (a == JOptionPane.NO_OPTION) {

                }
            }
        });
        btnNewButton.setBounds(880, 500, 100, 30);

        contentPane.add(btnNewButton, BorderLayout.SOUTH);
        add(btnNewButton);

        storesButton = new JButton("View Stores Near you");
        storesButton.setForeground(new Color(0, 0, 0));
        storesButton.setBackground(UIManager.getColor("Button.disabledForeground"));

        storesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
