package gui;

import javax.swing.*;
import java.awt.*;
import managers.DepartmentManager;
import managers.EmployeeManager;

class MainDashboardFrame extends JFrame
{
    private EmployeeManager employeeManager;
    private DepartmentManager departmentManager;

    private static final String DASHBOARD   = "dashboard";
    private static final String VIEW        = "view";
    private static final String ADD         = "add";
    private static final String SEARCH      = "search";
    private static final String DEPARTMENTS = "departments";

    private CardLayout cards = new CardLayout();
    private JPanel content = new JPanel(cards);
    private ButtonGroup navGroup = new ButtonGroup();

    MainDashboardFrame(EmployeeManager employeeManager, DepartmentManager departmentManager)
    {
        super("Employee Management System");
        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(buildSidebar(), BorderLayout.WEST);
        add(buildContent(), BorderLayout.CENTER);

        setMinimumSize(new Dimension(900, 600));
        setSize(1160, 760);
        setLocationRelativeTo(null);
    }

    // ================= sidebar =================

    private JPanel buildSidebar()
    {
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBackground(Theme.FOREST_GREEN);
        side.setPreferredSize(new Dimension(250, 0));
        side.setBorder(BorderFactory.createEmptyBorder(22, 18, 18, 18));

        JLabel logoLabel = new JLabel(new PeopleIcon(48, Color.WHITE));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        side.add(logoLabel);
        side.add(Box.createVerticalStrut(5));

        JLabel name = new JLabel("<html><center>Employee<br>Management<br>System</center></html>");
        name.setFont(Theme.bold(17));
        name.setForeground(Color.WHITE);
        name.setHorizontalAlignment(SwingConstants.CENTER);   // centre the text inside the label
        name.setAlignmentX(Component.CENTER_ALIGNMENT);       // centre the label inside the sidebar
        side.add(name);
        side.add(Box.createVerticalStrut(22));

        NavButton dashboard = addNav(side, "Dashboard",      "home",   DASHBOARD);
        addNav(side, "View Employees", "people", VIEW);
        addNav(side, "Add Employee",   "add",    ADD);
        addNav(side, "Search / Update","search", SEARCH);
        addNav(side, "Departments",    "domain", DEPARTMENTS);
        dashboard.setSelected(true);

        side.add(Box.createVerticalStrut(24));
        JPanel divider = new JPanel();
        divider.setBackground(Theme.SIDEBAR_SEPARATOR);
        divider.setPreferredSize(new Dimension(180, 1));
        divider.setMaximumSize(new Dimension(180, 1));
        divider.setAlignmentX(Component.CENTER_ALIGNMENT);
        side.add(divider);
        side.add(Box.createVerticalStrut(15));

        NavButton logout = new NavButton("Logout", "logout");
        logout.addActionListener(e -> {
            logout.setSelected(false);              // Logout never stays highlighted
            confirmLogout();
        });
        side.add(logout);

        side.add(Box.createVerticalGlue());         // pushes everything up
        return side;
    }

    private NavButton addNav(JPanel side, String text, String iconName, String cardName)
    {
        if (navGroup.getButtonCount() > 0)
        {
            side.add(Box.createVerticalStrut(5));   // 5 px BETWEEN items, none after the last
        }
        NavButton button = new NavButton(text, iconName);
        button.addActionListener(e -> cards.show(content, cardName));
        navGroup.add(button);                       // only one selected at a time
        side.add(button);
        return button;
    }

    private void confirmLogout()
    {
        int answer = JOptionPane.showConfirmDialog(this, "Do you want to log out?",
                "Logout", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION)
        {
            dispose();
            new LoginFrame().setVisible(true);
        }
    }

    // ================= content area =================

    private JPanel buildContent()
    {
        content.setBackground(Theme.IVORY);
        content.setBorder(BorderFactory.createEmptyBorder(24, 34, 18, 34));

        content.add(buildWelcomePanel(), DASHBOARD);

        // placeholders until teammates' panels are public and accept the managers:
        // content.add(new ViewEmployeesPanel(employeeManager), VIEW);
        content.add(placeholder("View Employees"), VIEW);
        content.add(placeholder("Add Employee"), ADD);
        content.add(placeholder("Search / Update Employee"), SEARCH);
        content.add(placeholder("Department Management"), DEPARTMENTS);


        cards.show(content, DASHBOARD);
        return content;
    }

    private JPanel placeholder(String title)
    {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(Theme.header(title, "none.png"), BorderLayout.NORTH);
        return p;
    }

    private JPanel buildWelcomePanel()
    {
        JPanel page = new JPanel(new BorderLayout());
        page.setOpaque(false);
        page.add(Theme.header("Dashboard", "home-green.png"), BorderLayout.NORTH);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Theme.GRID_GRAY, 1));

        JLabel welcome = new JLabel("<html><div style='text-align:center'>"
                + "<span style='font-size:26pt'><b>Welcome to<br>Employee Management System</b></span>"
                + "<br><br><span style='font-size:13pt'>Manage employees and departments efficiently.</span>"
                + "</div></html>");
        welcome.setForeground(Theme.TEXT);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));

        JLabel welcomeLogo = new JLabel(new PeopleIcon(110, Theme.FOREST_GREEN));
        welcomeLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        column.add(welcomeLogo);
        column.add(Box.createVerticalStrut(16));
        column.add(welcome);

        card.add(column);                           // single child, no constraints = centred

        page.add(card, BorderLayout.CENTER);
        return page;
    }

    // ================= custom sidebar button =================

    private static class NavButton extends JToggleButton
    {
        private final String iconName;

        NavButton(String text, String iconName)
        {
            super(text);
            this.iconName = iconName;
            setFont(Theme.bold(14));
            setHorizontalAlignment(SwingConstants.LEFT);
            setIconTextGap(12);
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            setContentAreaFilled(false);
            setFocusPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setAlignmentX(Component.CENTER_ALIGNMENT);
            setPreferredSize(new Dimension(190, 43));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 43));
            addItemListener(e -> refreshColours());
            refreshColours();
        }

        private void refreshColours()
        {
            boolean on = isSelected();
            setForeground(on ? Theme.DARK_GREEN : Color.WHITE);
            setIcon(Theme.icon(iconName + (on ? "-green.png" : "-white.png"), 22));
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            g.setColor(isSelected() ? Theme.SELECTED_CREAM : Theme.FOREST_GREEN);
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    }
}
