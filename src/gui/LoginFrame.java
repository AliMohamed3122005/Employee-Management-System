package gui;

import javax.swing.*;
import java.awt.*;
import gui.panels.DepartmentManagementPanel.IconType;
import gui.panels.DepartmentManagementPanel.VectorIcon;
import managers.DepartmentManager;
import managers.EmployeeManager;

class LoginFrame extends JFrame
{
    private JTextField usernameField;
    private JPasswordField passwordField;

    LoginFrame()
    {
        super("Employee Management System");
        Theme.setWindowIcon(this);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        Theme.styleField(usernameField);
        Theme.styleField(passwordField);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(buildBackground());
        setMinimumSize(new Dimension(500, 400));
        setSize(620, 520);
        setLocationRelativeTo(null);
    }

    // ivory background with the two dark-green corner curves
    private JPanel buildBackground()
    {
        JPanel background = new JPanel(new GridBagLayout())
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Theme.FOREST_GREEN);
                g2.fillOval(-150, -170, 380, 300);                                    // top-left
                g2.fillOval(getWidth() - 230, getHeight() - 110, 380, 260);          // bottom-right
                g2.dispose();
            }
        };
        background.setBackground(Theme.IVORY);

        JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));

        JLabel logoLabel = new JLabel(new VectorIcon(IconType.GROUP, 100, Theme.FOREST_GREEN));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        column.add(logoLabel);
        column.add(Box.createVerticalStrut(10));

        JLabel title = new JLabel("Employee Management System");
        title.setFont(Theme.bold(30));
        title.setForeground(Theme.TITLE_GREEN);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        column.add(title);
        column.add(Box.createVerticalStrut(28));

        JPanel card = buildCard();
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        column.add(card);

        background.add(column);   // no constraints = centred
        return background;
    }

    // the white box with the two fields and two buttons
    private JPanel buildCard()
    {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.GRID_GRAY, 1),
                BorderFactory.createEmptyBorder(24, 28, 24, 28)));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0;
        card.add(makeLabel("Username:"), c);
        c.gridx = 1;
        card.add(usernameField, c);

        c.gridx = 0; c.gridy = 1;
        card.add(makeLabel("Password:"), c);
        c.gridx = 1;
        card.add(passwordField, c);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(20, 8, 0, 8);
        card.add(buildButtonRow(), c);
        return card;
    }

    private JLabel makeLabel(String text)
    {
        JLabel label = new JLabel(text);
        label.setFont(Theme.bold(14));
        label.setForeground(Theme.TEXT);
        return label;
    }

    private JPanel buildButtonRow()
    {
        JButton loginButton = Theme.button("Login", Theme.FOREST_GREEN, Color.WHITE, null,
                Theme.icon("login-white.png", 18));
        JButton exitButton = Theme.button("Exit", Theme.SECONDARY_BG, Theme.SECONDARY_TEXT,
                Theme.SECONDARY_BORDER, Theme.icon("close-dark.png", 18));
        loginButton.setPreferredSize(new Dimension(150, 40));
        exitButton.setPreferredSize(new Dimension(150, 40));

        loginButton.addActionListener(e -> attemptLogin());
        exitButton.addActionListener(e -> System.exit(0));
        getRootPane().setDefaultButton(loginButton);   // Enter key = Login

        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        row.setOpaque(false);
        row.add(loginButton);
        row.add(exitButton);
        return row;
    }

    private void attemptLogin()
    {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Please enter your username and password.",
                    "Missing information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!credentialsAreValid(username, password))
        {
            JOptionPane.showMessageDialog(this, "Wrong username or password.",
                    "Login failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            return;
        }

        EmployeeManager employeeManager = new EmployeeManager();
        employeeManager.loadFromFile();
        DepartmentManager departmentManager = new DepartmentManager();

        dispose();                                                         // close the login window
        new MainDashboardFrame(employeeManager, departmentManager).setVisible(true);
    }

    // TEMPORARY: AuthService is still an empty, package-private class, so GUI cannot call it.
    private boolean credentialsAreValid(String username, String password)
    {
        return username.equals("admin") && password.equals("admin123");
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
