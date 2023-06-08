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

                    private boolean saveUser(String signupUsername, String signupPassword) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        nextFrame.setSize(500, 500);
        nextFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        JPanel nextPanel = new JPanel();

        JLabel name = new JLabel("welcome to recipe organizer");
        name.setBounds(100, 20, 200, 25);
        JButton uploadButton = new JButton("Upload Recipe");
        uploadButton.setBounds(100, 50, 130, 25);
        JButton viewButton = new JButton("View Recipes");
        viewButton.setBounds(100, 100, 130, 25);
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle upload recipe action
                JOptionPane.showMessageDialog(nextFrame, "Upload Recipe option selected", "Upload Recipe", JOptionPane.INFORMATION_MESSAGE);
                addrecipepage obj= new addrecipepage();
                obj.open();
            }
        }); 

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle view recipes action
                JOptionPane.showMessageDialog(nextFrame, "View Recipes option selected", "View Recipes", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        nextPanel.add(uploadButton);
        nextPanel.add(viewButton);
        nextPanel.add(name);
        
        nextPanel.setLayout(null);
         
        nextFrame.add(nextPanel);
        nextFrame.setVisible(true);
    }
}

class addrecipepage{
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/danish ali";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public void open() {
        JFrame frame = new JFrame("Add Recipe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel addRecipePanel = new JPanel();
        addRecipePanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        

        JLabel nameLabel = new JLabel("Recipe Name:");
        JTextField nameField = new JTextField(20);

        JLabel imageLabel = new JLabel("Image:");
        JTextField imageField = new JTextField(20);

        JButton uploadButton = new JButton("Upload");
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle image upload action
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imageField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        JLabel descriptionLabel = new JLabel("Description:");
        JTextArea descriptionArea = new JTextArea(4, 20);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

        JLabel categoryLabel = new JLabel("Category:");
        JCheckBox categoryCheckBox = new JCheckBox("Vegetarian");

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        frame.setVisible(true);
    }

    private static boolean saveRecipe( String name, String image, String description, boolean isVegetarian) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String sql = "INSERT INTO recipe ( name, image, description, vegetarian) VALUES ( ?, ?, ?, ?)";
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
