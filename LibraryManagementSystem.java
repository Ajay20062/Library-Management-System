import javax.swing.*;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create a label
        JLabel welcomeLabel = new JLabel("Welcome to the Library Management System", SwingConstants.CENTER);
        frame.getContentPane().add(welcomeLabel);

        // Make the frame visible
        frame.setVisible(true);
    }
}