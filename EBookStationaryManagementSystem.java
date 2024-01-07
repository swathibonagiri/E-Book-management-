import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class EBook {
    String title;
    String author;
    double price;
    boolean isAvailable;
    String username;

    public EBook(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.isAvailable = true;
        this.username = "";
    }
}

public class EBookStationaryManagementSystem extends JFrame {
    private JTextField titleTextField, authorTextField, priceTextField, usernameField;
    private JPasswordField passwordField;
    private ArrayList<EBook> eBooks;
    private Map<EBook, String> rentedBooks;

    private JPanel cards;
    private CardLayout cardLayout;

    public EBookStationaryManagementSystem() {
        eBooks = new ArrayList<>();
        rentedBooks = new HashMap<>();

        setTitle("E-Book Management Stationary");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the cards panel with CardLayout
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Create Login and Main pages
        JPanel loginPage = createLoginPage();
        JPanel mainPage = createMainPage();

        // Add pages to the cards panel
        cards.add(loginPage, "Login");
        cards.add(mainPage, "Main");

        // Add the cards panel to the frame
        getContentPane().add(cards);

        // Initially show the login page
        cardLayout.show(cards, "Login");
    }

    private JPanel createLoginPage() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Login Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement login logic here
                if (authenticateUser()) {
                    switchToMainPage();
                } else {
                    JOptionPane.showMessageDialog(EBookStationaryManagementSystem.this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        return loginPanel;
    }

    private JPanel createMainPage() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Set background color
        mainPanel.setBackground(new Color(173, 216, 230)); // Sky Blue

        titleTextField = new JTextField(20);
        authorTextField = new JTextField(20);
        priceTextField = new JTextField(10);

        JButton addButton = createStyledButton("Add E-Book", new Color(50, 205, 50)); // Lime Green
        JButton removeButton = createStyledButton("Remove E-Book", new Color(255, 69, 0)); // Red-Orange
        JButton viewBooksButton = createStyledButton("View Books", new Color(70, 130, 180)); // Steel Blue
        JButton purchaseButton = createStyledButton("Purchase E-Book", new Color(0, 128, 0)); // Green
        JButton rentButton = createStyledButton("Rent E-Book", new Color(255, 165, 0)); // Orange
        JButton returnButton = createStyledButton("Return E-Book", new Color(128, 0, 128)); // Purple

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEBook();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeEBook();
            }
        });

        viewBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBooks();
            }
        });

        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseEBook();
            }
        });

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rentEBook();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnEBook();
            }
        });

        // Layout (adjust as needed)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(createStyledLabel("Title:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(titleTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(createStyledLabel("Author:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(authorTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(createStyledLabel("Price:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(priceTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(addButton, gbc);

        gbc.gridx = 1;
        mainPanel.add(removeButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(viewBooksButton, gbc);

        gbc.gridx = 1;
        mainPanel.add(purchaseButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(rentButton, gbc);

        gbc.gridx = 1;
        mainPanel.add(returnButton, gbc);

        return mainPanel;
    }

    private boolean authenticateUser() {
        String enteredUsername = usernameField.getText();
        char[] enteredPasswordChars = passwordField.getPassword();
        String enteredPassword = new String(enteredPasswordChars);

        // Replace this with your actual authentication logic
        // For simplicity, let's consider "user" as the username and "password" as the password
        return enteredUsername.equals("swathi") && enteredPassword.equals("swathi123");
    }

    private void switchToMainPage() {
        cardLayout.show(cards, "Main");
    }

    private void addEBook() {
        // Implement the logic for adding an E-Book
        String title = titleTextField.getText();
        String author = authorTextField.getText();
        double price;

        try {
            price = Double.parseDouble(priceTextField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!title.isEmpty() && !author.isEmpty() && price >= 0) {
            EBook eBook = new EBook(title, author, price);
            eBooks.add(eBook);
            clearFields();
            JOptionPane.showMessageDialog(this, "E-Book added: " + eBook.title + " by " + eBook.author, "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter valid title, author, and price.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeEBook() {
        // Implement the logic for removing an E-Book
        String titleToRemove = titleTextField.getText();

        if (!titleToRemove.isEmpty()) {
            boolean removed = eBooks.removeIf(eBook -> eBook.title.equals(titleToRemove));

            if (removed) {
                JOptionPane.showMessageDialog(this, "E-Book removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "E-Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter the title of the E-Book to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewBooks() {
        // Implement the logic for viewing E-Books
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Title", "Author", "Price", "Availability", "Username"}, 0);

        // Add data to the table model for available books
        for (EBook eBook : eBooks) {
            String availability = eBook.isAvailable ? "Available" : "Not Available";
            tableModel.addRow(new String[]{eBook.title, eBook.author, String.valueOf(eBook.price), availability, ""});
        }

        // Add data to the table model for rented books
        for (Map.Entry<EBook, String> entry : rentedBooks.entrySet()) {
            EBook eBook = entry.getKey();
            String username = entry.getValue();
            tableModel.addRow(new String[]{eBook.title, eBook.author, String.valueOf(eBook.price), "Rented", username});
        }

        // Create and configure the JTable
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create and configure the JScrollPane for the JTable
        JScrollPane scrollPane = new JScrollPane(table);

        // Show the JTable in a popup dialog
        JOptionPane.showMessageDialog(this, scrollPane, "E-Book Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void purchaseEBook() {
        // Implement the logic for purchasing an E-Book
        String titleToPurchase = titleTextField.getText();

        if (!titleToPurchase.isEmpty()) {
            boolean found = false;
            for (EBook eBook : eBooks) {
                if (eBook.title.equals(titleToPurchase)) {
                    found = true;
                    JOptionPane.showMessageDialog(this, "E-Book purchased: " + eBook.title + " by " + eBook.author, "Success", JOptionPane.INFORMATION_MESSAGE);
                    eBooks.remove(eBook);
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "E-Book not available for purchase.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter the title of the E-Book to purchase.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rentEBook() {
        // Implement the logic for renting an E-Book
        String titleToRent = titleTextField.getText();

        if (!titleToRent.isEmpty()) {
            String username = JOptionPane.showInputDialog(this, "Enter username for renting:");
            if (username != null) { // Check if the user pressed cancel
                boolean found = false;
                for (EBook eBook : eBooks) {
                    if (eBook.title.equals(titleToRent) && eBook.isAvailable) {
                        found = true;
                        JOptionPane.showMessageDialog(this, "E-Book rented by " + username + ": " + eBook.title + " by " + eBook.author + " for $" + eBook.price, "Success", JOptionPane.INFORMATION_MESSAGE);
                        rentedBooks.put(eBook, username);
                        eBook.isAvailable = false; // Mark as not available
                        break;
                    } else if (eBook.title.equals(titleToRent) && !eBook.isAvailable) {
                        found = true;
                        JOptionPane.showMessageDialog(this, "E-Book is not available for rent.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(this, "E-Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                clearFields();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter the title of the E-Book to rent.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnEBook() {
        // Implement the logic for returning an E-Book
        String titleToReturn = titleTextField.getText();

        if (!titleToReturn.isEmpty()) {
            boolean found = false;
            for (EBook eBook : rentedBooks.keySet()) {
                if (eBook.title.equals(titleToReturn)) {
                    found = true;
                    JOptionPane.showMessageDialog(this, "E-Book returned: " + eBook.title + " by " + eBook.author, "Success", JOptionPane.INFORMATION_MESSAGE);
                    rentedBooks.remove(eBook);
                    eBook.isAvailable = true; // Mark as available
                    eBooks.add(eBook);
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "E-Book not found in rented books.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter the title of the E-Book to return.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        titleTextField.setText("");
        authorTextField.setText("");
        priceTextField.setText("");
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(color);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EBookStationaryManagementSystem().setVisible(true);
            }
        });
    }
}

