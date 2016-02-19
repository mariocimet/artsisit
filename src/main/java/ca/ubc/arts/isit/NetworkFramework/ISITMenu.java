package ca.ubc.arts.isit.NetworkFramework;
// Author: Mario Cimet
// Version .01
// This is an application to aggregate student data from MOOCs and dynamic graphs with detailed attributes for Nodes (students)


    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;

    /* FrameDemo.java requires no other files. */
    public class ISITMenu {
        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event-dispatching thread.
         */
        private static void createAndShowGUI() {
            //Create and set up the window.
            JFrame frame = new JFrame("ISITMenu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel emptyLabel = new JLabel("");
            emptyLabel.setPreferredSize(new Dimension(175, 100));
            frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }

        public static void main(String[] args) {
            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
        }
    }

}
