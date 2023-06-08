package javaapplication36;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage {
    // Define your database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/danish ali";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Page");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 30, 80, 25);
        JTextField usernameField = new JTextField(20);
        usernameField.setBounds(50, 50, 120, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 80, 80, 25);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(50, 100, 120, 25);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(110, 140, 80, 25);

        JButton signupButton = new JButton("Signup");
        signupButton.setBounds(200, 140, 80, 25);

        // Add ActionListener to the loginButton
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check username and password against the database
                if (authenticateUser(username, password)) {
                    // If authentication succeeds, proceed to the next page
                    OpenNextPage openNextPage = new OpenNextPage();
                    openNextPage.open();
                } else {
                    // If authentication fails, show an error message
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame signupFrame = new JFrame("Signup Page");
                signupFrame.setSize(400, 300);
                signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel signupPanel = new JPanel();
                signupPanel.setLayout(null);

                JLabel signupUsernameLabel = new JLabel("Username:");
                signupUsernameLabel.setBounds(50, 30, 80, 25);
                JTextField signupUsernameField = new JTextField(20);
                signupUsernameField.setBounds(50, 50, 120, 25);

                JLabel signupPasswordLabel = new JLabel("Password:");
                signupPasswordLabel.setBounds(50, 80, 80, 25);
                JPasswordField signupPasswordField = new JPasswordField(20);
                signupPasswordField.setBounds(50, 100, 120, 25);

                JButton signupSubmitButton = new JButton("Submit");
                signupSubmitButton.setBounds(110, 140, 80, 25);

                signupSubmitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String signupUsername = signupUsernameField.getText();
                        String signupPassword = new String(signupPasswordField.getPassword());

                        // Save the username and password in the database
                        boolean success = saveUser(signupUsername, signupPassword);

                        if (success) {
                            JOptionPane.showMessageDialog(signupFrame, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            signupFrame.dispose(); // Close the signup page after successful signup
                        } else {
                            JOptionPane.showMessageDialog(signupFrame, "Signup failed", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                signupPanel.add(signupUsernameLabel);
                signupPanel.add(signupUsernameField);
                signupPanel.add(signupPasswordLabel);
                signupPanel.add(signupPasswordField);
                signupPanel.add(signupSubmitButton);

                signupFrame.add(signupPanel);
                signupFrame.setVisible(true);
            }
        });

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(signupButton);

        frame.add(loginPanel);
        frame.setVisible(true);
    }

    private static boolean authenticateUser(String username, String password) {
  
        // Establish database connection
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Prepare the SQL statement to check if the username and password match
            String sql = "SELECT COUNT(*) FROM customers WHERE username = ? AND pasword = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Check if a record exists with the provided username and password
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Return true if at least one record is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Return false if there's an error or no matching record found
    }

    }

   


class OpenNextPage {
    public void open() {
        JFrame nextFrame = new JFrame("Next Page");
        nextFrame.setSize(400, 300);
        nextFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel nextPanel = new JPanel();
        nextPanel.setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("Welcome to the next page!");
        nextPanel.add(welcomeLabel);

        nextFrame.add(nextPanel);
        nextFrame.setVisible(true);
    }
}
