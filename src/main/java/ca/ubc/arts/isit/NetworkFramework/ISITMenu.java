    package ca.ubc.arts.isit.NetworkFramework;
    import java.io.*;
    import javax.swing.*;
    import java.awt.*;
    import java.io.IOException;
    import java.util.*;
    import java.util.List;

    import org.apache.commons.csv.CSVFormat;
    import org.apache.commons.csv.CSVRecord;

    /**
     Author: Mario Cimet
     Version .01
     This is an application to aggregate student data from MOOCs and dynamic graphs with detailed attributes for Nodes (students)
     **/
        public class ISITMenu {

        public static String filepathNetwork;

        private static void show() {

            // Todo: Create menu interface - allow user to select file-path from browser
            //Create and set up the window.
            JFrame frame = new JFrame("ISITMenu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JLabel label = new JLabel("NetworkWrangler");
            label.setPreferredSize(new Dimension(175, 100));
            frame.getContentPane().add(label, BorderLayout.CENTER);

            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }

        public static void main(String[] args) throws IOException {
            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    show();
                }
            });

        }
    }

