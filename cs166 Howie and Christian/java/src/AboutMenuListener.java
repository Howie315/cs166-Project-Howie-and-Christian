import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class AboutMenuListener implements ActionListener {
    /**
     * Purpose: action performed for displaying the contributors
     * signature: nothing
     * example: actionPerformed(ActionEvent e)
     */
    public void actionPerformed(ActionEvent e) {
        // Create a JOption pane
        JOptionPane pane = new JOptionPane();
        // display dialog
        pane.showMessageDialog(null,
                "Developers: " + "Howie Nguyen and Christian Boroff" + "\nEmails: nguyen.howie2010@gmail.com" +
                        "\n" + "cboro002@ucr.edu");
    }
}