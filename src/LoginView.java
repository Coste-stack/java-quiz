import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Dimension;
import java.awt.GridLayout;

public class LoginView extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private JFrame frame;
    private JPanel panel;
    private LoginHandler loginHandler;

    public LoginView(JFrame frame) {
        this.frame = frame;
        panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setPreferredSize(new Dimension(300, 150));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);
        
        loginHandler = new LoginHandler("users.ser");
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());

        add(panel);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (loginHandler.authenticate(username, password)) {
            // Usuń poprzedni panel logowania
            panel.setVisible(false);

            // Pokaż quiz
            CategoryView categoryView = new CategoryView();
            frame.add(categoryView);
            frame.revalidate();
            frame.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (!loginHandler.doesUserExist(username)) {
            loginHandler.addUser(username, password);
        } else {
            JOptionPane.showMessageDialog(this, "User already exists.");
        }
    }
}
