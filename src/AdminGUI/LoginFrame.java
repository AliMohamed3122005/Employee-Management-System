package AdminGUI;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import managers.AuthService;

class LoginFrame extends JFrame
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private AuthService authService;
}
