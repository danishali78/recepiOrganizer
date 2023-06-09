package javaapplication36;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
        loginPanel.setBackground(new Color(255, 255, 240)); // Set background color

        JLabel titleLabel = new JLabel("Login Page");
        titleLabel.setBounds(130, 10, 150, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set title font

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 70, 80, 25);
        JTextField usernameField = new JTextField(20);
        usernameField.setBounds(150, 70, 150, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 110, 80, 25);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(150, 110, 150, 25);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 160, 200, 30);
        loginButton.setBackground(new Color(59, 89, 182)); // Set button background color
        loginButton.setForeground(Color.white); // Set button text color
        loginButton.setFocusPainted(false); // Remove focus border
        loginButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set button font

        JButton signupButton = new JButton("Create an Account");
        signupButton.setBounds(100, 210, 200, 30);
        signupButton.setBackground(new Color(255, 87, 34)); // Set button background color
        signupButton.setForeground(Color.white); // Set button text color
        signupButton.setFocusPainted(false); // Remove focus border
        signupButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set button font

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check username and password against the database
                if (authenticateUser(username, password)) {
                    // If authentication succeeds, proceed to the next page
                    JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    OpenNextPage obj1= new OpenNextPage();
                    obj1.open();
                    
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
        signupPanel.setBackground(new Color(245, 245, 245));
        signupPanel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Signup Page");
        titleLabel.setBounds(120, 10, 160, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(59, 89, 182));

        // Username Label and Field
        JLabel signupUsernameLabel = new JLabel("Username:");
        signupUsernameLabel.setBounds(50, 60, 80, 25);
        JTextField signupUsernameField = new JTextField(20);
        signupUsernameField.setBounds(140, 60, 160, 25);

        // Password Label and Field
        JLabel signupPasswordLabel = new JLabel("Password:");
        signupPasswordLabel.setBounds(50, 100, 80, 25);
        JPasswordField signupPasswordField = new JPasswordField(20);
        signupPasswordField.setBounds(140, 100, 160, 25);

        // Submit Button
        JButton signupSubmitButton = new JButton("Submit");
        signupSubmitButton.setBounds(150, 150, 100, 30);
        signupSubmitButton.setBackground(new Color(59, 89, 182));
        signupSubmitButton.setForeground(Color.WHITE);
        signupSubmitButton.setFont(new Font("Arial", Font.BOLD, 14));
        signupSubmitButton.setFocusPainted(false);


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

                    private boolean saveUser(String signupUsername, String signupPassword) {
                        // Implement your code to save the user details in the database
                        // Return true if the user is saved successfully, false otherwise
                        // Example code:
                        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                            String sql = "INSERT INTO customers (username, pasword) VALUES (?, ?)";
                            PreparedStatement stmt = conn.prepareStatement(sql);
                            stmt.setString(1, signupUsername);
                            stmt.setString(2, signupPassword);
                            int rowsInserted = stmt.executeUpdate();
                            return rowsInserted > 0;
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        return false;
                    }
                });
               
               signupPanel.add(titleLabel);
               signupPanel.add(signupUsernameLabel);
               signupPanel.add(signupUsernameField);
               signupPanel.add(signupPasswordLabel);
               signupPanel.add(signupPasswordField);
               signupPanel.add(signupSubmitButton);

        // Add the signup panel to the frame
               frame.setLocationRelativeTo(null);
               frame.add(signupPanel);
               frame.setVisible(true);
            }
        });

        loginPanel.add(titleLabel);
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(signupButton);
        frame.setLocationRelativeTo(null);
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
        nextFrame.setSize(500, 500);
        nextFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        JPanel nextPanel = new JPanel();
    nextPanel.setBackground(new Color(240, 240, 240));
    nextPanel.setLayout(null);

    JLabel nameLabel = new JLabel("Welcome to Recipe Organizer");
    nameLabel.setBounds(50, 50, 400, 60);
    nameLabel.setFont(new Font("Arial", Font.BOLD, 28));
    nameLabel.setForeground(new Color(59, 89, 182));

    JButton uploadButton = new JButton("Upload Recipe");
    uploadButton.setBounds(100, 150, 300, 50);
    uploadButton.setBackground(new Color(59, 89, 182));
    uploadButton.setForeground(Color.WHITE);
    uploadButton.setFont(new Font("Arial", Font.BOLD, 20));
    uploadButton.setFocusPainted(false);
    uploadButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    JButton viewButton = new JButton("View Recipes");
    viewButton.setBounds(100, 230, 300, 50);
    viewButton.setBackground(new Color(59, 89, 182));
    viewButton.setForeground(Color.WHITE);
    viewButton.setFont(new Font("Arial", Font.BOLD, 20));
    viewButton.setFocusPainted(false);
    viewButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        uploadButton.addActionListener(e -> {
            // Handle upload recipe action
            JOptionPane.showMessageDialog(nextFrame, "Upload Recipe option selected", "Upload Recipe", JOptionPane.INFORMATION_MESSAGE);
            addrecipepage obj = new addrecipepage();
            obj.open();
        });

        viewButton.addActionListener(e -> {
            // Handle view recipes action
            JOptionPane.showMessageDialog(nextFrame, "View Recipes option selected", "View Recipes", JOptionPane.INFORMATION_MESSAGE);
        });

        nextPanel.add(nameLabel);
        nextPanel.add(uploadButton);
        nextPanel.add(viewButton);

        nextFrame.add(nextPanel);
        nextFrame.setLocationRelativeTo(null); // Center the frame on the screen
        nextFrame.setVisible(true);
    }
}

class addrecipepage{
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/danishali";
private static final String DB_USERNAME = "root";
private static final String DB_PASSWORD = "";

public void open() {
    JFrame frame = new JFrame("Add Recipe");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setBackground(new Color(255, 255, 240));

    JPanel addRecipePanel = new JPanel();
    addRecipePanel.setLayout(new GridBagLayout());
    addRecipePanel.setBackground(new Color(255, 255, 240));

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.insets = new Insets(10, 10, 10, 10);

    JLabel nameLabel = new JLabel("Recipe Name:");
    nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
    nameLabel.setForeground(new Color(59, 89, 182));
    JTextField nameField = new JTextField(20);
    nameField.setFont(new Font("Arial", Font.PLAIN, 14));

    JLabel imageLabel = new JLabel("Image:");
    imageLabel.setFont(new Font("Arial", Font.BOLD, 18));
    imageLabel.setForeground(new Color(59, 89, 182));
    JTextField imageField = new JTextField(20);
    imageField.setFont(new Font("Arial", Font.PLAIN, 14));

    JButton uploadButton = new JButton("Upload");
    uploadButton.setFont(new Font("Arial", Font.BOLD, 14));
    uploadButton.setBackground(new Color(59, 89, 182));
    uploadButton.setForeground(Color.WHITE);
    uploadButton.setFocusPainted(false);
    uploadButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    uploadButton.addActionListener(e -> {
        // Handle image upload action
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imageField.setText(selectedFile.getAbsolutePath());
        }
    });

    JLabel descriptionLabel = new JLabel("Description:");
    descriptionLabel.setFont(new Font("Arial", Font.BOLD, 18));
    descriptionLabel.setForeground(new Color(59, 89, 182));
    JTextArea descriptionArea = new JTextArea(4, 20);
    descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
    JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

    JLabel categoryLabel = new JLabel("Category:");
    categoryLabel.setFont(new Font("Arial", Font.BOLD, 18));
    categoryLabel.setForeground(new Color(59, 89, 182));
    JCheckBox categoryCheckBox = new JCheckBox("Vegetarian");
    categoryCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));

    JButton submitButton = new JButton("Submit");
    submitButton.setFont(new Font("Arial", Font.BOLD, 16));
    submitButton.setBackground(new Color(59, 89, 182));
    submitButton.setForeground(Color.WHITE);
    submitButton.setFocusPainted(false);
    submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    submitButton.addActionListener(e -> {
        // Handle submit action
        String name = nameField.getText();
        String image = imageField.getText();
        String description = descriptionArea.getText();
        boolean isVegetarian = categoryCheckBox.isSelected();

        // Save the recipe details in the database
        boolean success = saveRecipe(name, image, description, isVegetarian);

        if (success) {
            JOptionPane.showMessageDialog(frame, "Recipe added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Failed to add recipe", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    constraints.gridx = 0;
    constraints.gridy = 1;
    addRecipePanel.add(nameLabel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 1;
    addRecipePanel.add(nameField, constraints);

    constraints.gridx = 0;
    constraints.gridy = 2;
    addRecipePanel.add(imageLabel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 2;
    addRecipePanel.add(imageField, constraints);

    constraints.gridx = 2;
    constraints.gridy = 2;
    addRecipePanel.add(uploadButton, constraints);

    constraints.gridx = 0;
    constraints.gridy = 3;
    constraints.gridwidth = 2;
    addRecipePanel.add(descriptionLabel, constraints);

    constraints.gridx = 0;
    constraints.gridy = 4;
    constraints.gridwidth = 2;
    addRecipePanel.add(descriptionScrollPane, constraints);

    constraints.gridx = 0;
    constraints.gridy = 5;
    addRecipePanel.add(categoryLabel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 5;
    addRecipePanel.add(categoryCheckBox, constraints);

    constraints.gridx = 0;
    constraints.gridy = 6;
    constraints.gridwidth = 3;
    addRecipePanel.add(submitButton, constraints);

    frame.add(addRecipePanel);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}

private static boolean saveRecipe(String name, String image, String description, boolean isVegetarian) {
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
        String sql = "INSERT INTO recipe (name, image, description, vegetarian) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, image);
        stmt.setString(3, description);
        stmt.setBoolean(4, isVegetarian);

        int rowsAffected = stmt.executeUpdate();

        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

public static void main(String[] args) {
    addrecipepage addRecipePage = new addrecipepage();
    addRecipePage.open();
}
}
