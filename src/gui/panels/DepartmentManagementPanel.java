package gui.panels;

import department.Department;
import managers.DepartmentManager;
import gui.util.TableStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.List;

public class DepartmentManagementPanel extends JPanel {

    private static final Color GREEN = new Color(0x075A38);
    private static final Color DARK_GREEN = new Color(0x064B30);
    private static final Color CREAM = new Color(0xFFF8DF);
    private static final Color IVORY = new Color(0xFBF9F1);
    private static final Color GRID = new Color(0xD9D9D3);

    private final DepartmentManager manager;
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();

    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"Department ID", "Department Name"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable table = new JTable(model);

    public DepartmentManagementPanel() {
        this(new DepartmentManager());
    }

    public DepartmentManagementPanel(DepartmentManager manager) {
        this(manager, true);
    }

    public DepartmentManagementPanel(DepartmentManager manager, boolean showSidebar) {
        if (manager == null) {
            throw new IllegalArgumentException("DepartmentManager cannot be null");
        }

        this.manager = manager;

        setLayout(new BorderLayout());
        setBackground(IVORY);

        if (showSidebar) {
            add(createSidebar(), BorderLayout.WEST);
        }
        add(createContent(), BorderLayout.CENTER);

        refreshTable();
    }

    private JPanel createSidebar() {
        JPanel side = new JPanel();
        side.setPreferredSize(new Dimension(250, 0));
        side.setBackground(GREEN);
        side.setBorder(BorderFactory.createEmptyBorder(28, 18, 18, 18));
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel(new VectorIcon(IconType.GROUP, 48, Color.WHITE));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel brand = new JLabel(
                "<html><center>Employee<br>Management<br>System</center></html>"
        );
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Segoe UI", Font.BOLD, 17));
        brand.setHorizontalAlignment(SwingConstants.CENTER);
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);

        side.add(logo);
        side.add(Box.createVerticalStrut(4));
        side.add(Box.createVerticalStrut(5));
        side.add(brand);
        side.add(Box.createVerticalStrut(22));

        addNavButton(side, "Dashboard", IconType.DASHBOARD, false);
        addNavButton(side, "View Employees", IconType.PEOPLE, false);
        addNavButton(side, "Add Employee", IconType.ADD_PERSON, false);
        addNavButton(side, "Search / Update", IconType.SEARCH, false);
        addNavButton(side, "Departments", IconType.BUILDING, true);

        JPanel divider = new JPanel();
        divider.setBackground(new Color(0x79A38C));
        divider.setPreferredSize(new Dimension(180, 1));
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        side.add(Box.createVerticalStrut(24));
        side.add(divider);
        side.add(Box.createVerticalStrut(15));

        addNavButton(side, "Logout", IconType.LOGOUT, false);

        side.add(Box.createVerticalGlue());

        return side;
    }

    private void addNavButton(
            JPanel sidebar, String text, IconType iconType, boolean selected) {

        JButton button = new JButton(text,
                new VectorIcon(iconType, 22, selected ? DARK_GREEN : Color.WHITE));

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setIconTextGap(12);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(selected ? DARK_GREEN : Color.WHITE);
        button.setBackground(selected ? CREAM : GREEN);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(214, 44));
        button.setMinimumSize(new Dimension(214, 44));
        button.setMaximumSize(new Dimension(214, 44));

        sidebar.add(button);

        if (!text.equals("Departments") && !text.equals("Logout")) {
            sidebar.add(Box.createVerticalStrut(5));
        }

    }


    private JPanel createContent(){

            JPanel content = new JPanel(new BorderLayout(0,16));
            content.setBackground(IVORY);
            content.setBorder(BorderFactory.createEmptyBorder(24,34,18,34));
            
            JPanel top = new JPanel();
            top.setOpaque(false);
            top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

            JPanel heading = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
            heading.setOpaque(false);

            heading.add(new JLabel(new VectorIcon(IconType.BUILDING, 39, GREEN)));

            JLabel title = new JLabel("Department Management");
            title.setFont(new Font("Segoe UI", Font.BOLD, 30));
            title.setForeground(new Color(0x123D2A));
            heading.add(title);


            JPanel separator = new JPanel();
            separator.setBackground(new Color(0x83A88F));
            separator.setPreferredSize(new Dimension(900, 2));
            separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));

            top.add(heading);
            top.add(Box.createVerticalStrut(13));
            top.add(separator);
            top.add(Box.createVerticalStrut(22));
            top.add(createFormRow());

            TableStyle.configure(table);
            table.getColumnModel().getColumn(0).setPreferredWidth(210);
            table.getColumnModel().getColumn(1).setPreferredWidth(460);

            JScrollPane tableScroll = new JScrollPane(table);
            tableScroll.setBorder(BorderFactory.createLineBorder(GRID));
            tableScroll.getViewport().setBackground(Color.WHITE);

            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            bottom.setOpaque(false);

            JButton refresh = actionButton("Refresh", IconType.REFRESH, GREEN, Color.WHITE, 18);
            refresh.setPreferredSize(new Dimension(116, 42));
            refresh.addActionListener(event -> refreshTable());
            bottom.add(refresh);

            content.add(top,BorderLayout.NORTH);
            content.add(tableScroll,BorderLayout.CENTER);
            content.add(bottom,BorderLayout.SOUTH);

            return content;
    }

    private JPanel createFormRow(){
        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridy=0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(0,0,0,12);

        JLabel idLabel = new JLabel("Department ID:");
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        row.add(idLabel, c);

        c.gridx = 1;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        idField.setPreferredSize(new Dimension(120, 36));
        idField.setMinimumSize(new Dimension(120, 36));
        idField.setBackground(Color.WHITE);
        idField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x9CB3C4)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        row.add(idField, c);

        c.gridx = 2;
        c.weightx = 0.0;
        c.insets = new Insets(0, 8, 0, 8);
        JLabel nameLabel = new JLabel("Department Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        row.add(nameLabel, c);

        c.gridx = 3;
        c.insets = new Insets(0, 0, 0, 12);
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        nameField.setFont(new Font("Segoe UI",Font.PLAIN,15));
        nameField.setPreferredSize(new Dimension(240, 36));
        nameField.setMinimumSize(new Dimension(220, 36));
        nameField.setBackground(Color.WHITE);
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x9CB3C4)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        
        row.add(nameField, c);
        c.gridx = 4;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0,2,0,10);

        JButton add = actionButton("Add", IconType.PLUS, GREEN, Color.WHITE, 18);
        add.setPreferredSize(new Dimension(96, 42));

        add.addActionListener(event -> addDepartment());
        row.add(add,c);
        c.gridx = 5;
        c.insets = new Insets(0,2,0,0);

        JButton delete = actionButton("Delete", IconType.TRASH,
                new Color(0xF1EFE8), new Color(0x222222), 18);
        delete.setPreferredSize(new Dimension(112, 42));

        delete.addActionListener(event -> deleteDepartment());
        row.add(delete,c);
        return row;
    }

    private JButton actionButton(String text, IconType iconType, Color background,
                                 Color foreground, int iconSize) {

        JButton button = new JButton(text, new VectorIcon(iconType, iconSize, foreground));
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(9, 13, 9, 13));
        button.setIconTextGap(8);
        return button;
    }

    private void addDepartment(){
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        if (id.isEmpty()) {
            showMessage("Enter a department ID.",
                    "ID required", JOptionPane.ERROR_MESSAGE);
        } else if (!manager.isIdUnique(id)) {
            showMessage("This department ID is already in use.",
                    "Duplicate ID", JOptionPane.ERROR_MESSAGE);
        } else if (name.isEmpty()) {
            showMessage("Enter a department name.",
                    "Name required", JOptionPane.ERROR_MESSAGE);
        } else if (!name.matches("[a-zA-Z ]+")) {
            showMessage("Use letters and spaces only.",
                    "Invalid name", JOptionPane.ERROR_MESSAGE);
        } else if (!manager.isNameUnique(name)) {
            showMessage("This department already exists.",
                    "Duplicate name", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                if (manager.addDepartment(new Department(name, id))) {
                    idField.setText("");
                    nameField.setText("");
                    refreshTable();
                    showMessage("Department added.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showMessage("Could not add department. Check that the ID and name are unique.",
                            "Add failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RuntimeException ex) {
                showMessage(ex.getMessage(),
                        "Add failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteDepartment() {
        int row = table.getSelectedRow();

        if (row < 0) {
            showMessage("Select a department first.",
                    "No selection", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String id = String.valueOf(model.getValueAt(row,0));
        int answer = JOptionPane.showConfirmDialog(this, "Delete the selected department?","Confirm deletion",JOptionPane.YES_NO_CANCEL_OPTION);

        if(answer != JOptionPane.YES_OPTION){
            return;
        }

        try {
            if (manager.deleteDepartment(id)) {
                refreshTable();
                showMessage("Department deleted.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                showMessage("Could not delete department.",
                        "Delete failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RuntimeException ex) {
            showMessage(ex.getMessage(),
                    "Delete failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void refreshTable() {
        model.setRowCount(0);
        List<Department> departments = manager.getAllDepartments();

        for (Department department : departments) {
            model.addRow(new Object[]{
                    department.getDepartmentId(),
                    department.getDepartmentName()
            });
        }
    }
    private void showMessage(String text, String title, int type) {
        JOptionPane.showMessageDialog(this, text, title, type);
    }
    public enum IconType {
        GROUP, DASHBOARD, PEOPLE, ADD_PERSON, SEARCH, BUILDING, LOGOUT, PLUS, TRASH, REFRESH
    }

    // Vector icons are painted at their final Swing size to keep edges sharp.
    public static final class VectorIcon implements Icon {
        private final IconType type;
        private final int size;
        private final Color color;

        public VectorIcon(IconType type, int size, Color color) {
            this.type = type;
            this.size = size;
            this.color = color;
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }

        @Override
        public void paintIcon(Component component, Graphics graphics, int x, int y) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.translate(x, y);
            g.scale(size / 48.0, size / 48.0);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_PURE);
            g.setColor(color);
            g.setStroke(new BasicStroke(2.6f, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));

            switch (type) {
                case GROUP -> drawGroup(g);
                case DASHBOARD -> drawDashboard(g);
                case PEOPLE -> drawPeople(g);
                case ADD_PERSON -> drawAddPerson(g);
                case SEARCH -> drawSearch(g);
                case BUILDING -> drawBuilding(g);
                case LOGOUT -> drawLogout(g);
                case PLUS -> drawPlus(g);
                case TRASH -> drawTrash(g);
                case REFRESH -> drawRefresh(g);
            }
            g.dispose();
        }

        private static void drawGroup(Graphics2D g) {
            g.drawOval(19, 3, 10, 10);
            g.drawOval(5, 9, 8, 8);
            g.drawOval(35, 9, 8, 8);
            g.draw(new Arc2D.Double(14, 17, 20, 21, 0, 180, Arc2D.OPEN));
            g.draw(new Arc2D.Double(0, 21, 17, 17, 0, 180, Arc2D.OPEN));
            g.draw(new Arc2D.Double(31, 21, 17, 17, 0, 180, Arc2D.OPEN));
        }

        private static void drawDashboard(Graphics2D g) {
            g.drawRoundRect(5, 5, 16, 16, 3, 3);
            g.drawRoundRect(27, 5, 16, 16, 3, 3);
            g.drawRoundRect(5, 27, 16, 16, 3, 3);
            g.drawRoundRect(27, 27, 16, 16, 3, 3);
        }

        private static void drawPeople(Graphics2D g) {
            g.drawOval(10, 7, 9, 9);
            g.drawOval(29, 7, 9, 9);
            g.draw(new Arc2D.Double(5, 18, 18, 18, 0, 180, Arc2D.OPEN));
            g.draw(new Arc2D.Double(25, 18, 18, 18, 0, 180, Arc2D.OPEN));
        }

        private static void drawAddPerson(Graphics2D g) {
            g.drawOval(8, 6, 11, 11);
            g.draw(new Arc2D.Double(2, 20, 23, 21, 0, 180, Arc2D.OPEN));
            g.drawLine(34, 20, 34, 42);
            g.drawLine(23, 31, 45, 31);
        }

        private static void drawSearch(Graphics2D g) {
            g.drawOval(4, 4, 27, 27);
            g.drawLine(25, 26, 43, 44);
        }

        private static void drawBuilding(Graphics2D g) {
            g.drawRoundRect(10, 4, 28, 39, 2, 2);
            g.drawLine(5, 44, 43, 44);
            g.drawRect(16, 10, 4, 5);
            g.drawRect(27, 10, 4, 5);
            g.drawRect(16, 20, 4, 5);
            g.drawRect(27, 20, 4, 5);
            g.drawRect(20, 33, 7, 10);
        }

        private static void drawLogout(Graphics2D g) {
            g.drawRoundRect(25, 4, 16, 40, 2, 2);
            g.drawLine(4, 24, 31, 24);
            g.drawLine(4, 24, 13, 15);
            g.drawLine(4, 24, 13, 33);
        }

        private static void drawPlus(Graphics2D g) {
            g.drawLine(24, 7, 24, 41);
            g.drawLine(7, 24, 41, 24);
        }

        private static void drawTrash(Graphics2D g) {
            g.drawRoundRect(12, 14, 24, 30, 3, 3);
            g.drawLine(8, 11, 40, 11);
            g.drawRoundRect(18, 5, 12, 6, 2, 2);
            g.drawLine(20, 19, 20, 38);
            g.drawLine(24, 19, 24, 38);
            g.drawLine(28, 19, 28, 38);
        }

        private static void drawRefresh(Graphics2D g) {
            g.drawArc(7, 7, 34, 34, 38, 145);
            g.drawArc(7, 7, 34, 34, 218, 145);
            g.fillPolygon(new int[]{37, 42, 32}, new int[]{6, 16, 14}, 3);
            g.fillPolygon(new int[]{11, 6, 16}, new int[]{42, 32, 34}, 3);
        }
    }
    }
