package gui.panels;

import department.Department;
import employee.Employee;
import employee.FullTimeEmployee;
import employee.PartTimeEmployee;
import managers.DepartmentManager;
import managers.EmployeeManager;
import gui.util.TableStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SearchUpdatePanel extends JPanel {
    private static final Color GREEN = new Color(0x075A38);
    private static final Color IVORY = new Color(0xFBF9F1);
    private static final Color GRID = new Color(0xD9D9D3);

    private final EmployeeManager employeeManager;
    private final List<Department> departments = new ArrayList<>();
    private final List<Employee> searchResults = new ArrayList<>();

    private final JComboBox<String> searchType =
            new JComboBox<>(new String[]{"ID", "Name"});
    private final JTextField searchField = new JTextField(18);

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Type", "Department"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable resultsTable = new JTable(tableModel);

    private final JTextField idField = new JTextField(12);
    private final JTextField nameField = new JTextField(12);
    private final JTextField ageField = new JTextField(12);
    private final JTextField positionField = new JTextField(12);
    private final JComboBox<String> departmentField = new JComboBox<>();
    private final JComboBox<String> typeField =
            new JComboBox<>(new String[]{"Full Time", "Part Time"});

    private final JTextField salaryField = new JTextField(10);
    private final JTextField bonusField = new JTextField(10);
    private final JTextField hourlyRateField = new JTextField(10);
    private final JTextField hoursWorkedField = new JTextField(10);
    private final JPanel paymentFields = new JPanel(new BorderLayout());

    private final JButton updateButton = new JButton("Update");
    private Employee selectedEmployee;

    public SearchUpdatePanel(EmployeeManager employeeManager,
                             DepartmentManager departmentManager) {
        if (employeeManager == null || departmentManager == null) {
            throw new IllegalArgumentException("Managers cannot be null");
        }

        this.employeeManager = employeeManager;
        loadDepartments(departmentManager);
        configureTable();

        setLayout(new BorderLayout(16, 16));
        setBackground(IVORY);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 20, 28));

        JPanel center = new JPanel(new GridLayout(1, 2, 14, 0));
        center.setOpaque(false);
        center.add(createResultsPanel());
        center.add(createEditorPanel());

        add(createHeader(), BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        setEditorEnabled(false);
        showPaymentFields();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(0, 14));
        header.setOpaque(false);

        JLabel title = new JLabel("Search / Update Employee");
        title.setFont(new Font("Segoe UI", Font.BOLD, 27));
        title.setForeground(new Color(0x123D2A));

        JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchRow.setOpaque(false);
        searchField.setPreferredSize(new Dimension(240, 36));

        JButton searchButton = createButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                searchEmployees();
            }
        });

        searchRow.add(new JLabel("Search by:"));
        searchRow.add(searchType);
        searchRow.add(searchField);
        searchRow.add(searchButton);

        header.add(title, BorderLayout.NORTH);
        header.add(searchRow, BorderLayout.SOUTH);
        return header;
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(IVORY);
        panel.setBorder(BorderFactory.createTitledBorder("Results"));

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(GRID));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createEditorPanel() {
        JPanel editor = new JPanel(new BorderLayout(0, 12));
        editor.setBackground(IVORY);
        editor.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        JPanel fields = new JPanel(new GridLayout(0, 2, 10, 8));
        fields.setOpaque(false);

        addField(fields, "Employee ID:", idField);
        addField(fields, "Full Name:", nameField);
        addField(fields, "Age:", ageField);
        addField(fields, "Position:", positionField);
        addField(fields, "Department:", departmentField);
        addField(fields, "Employee Type:", typeField);

        paymentFields.add(createFullTimeFields(), BorderLayout.CENTER);
        addField(fields, "Payment Details:", paymentFields);

        typeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                showPaymentFields();
            }
        });

        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        updateButton.setBackground(GREEN);
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                updateEmployee();
            }
        });

        editor.add(fields, BorderLayout.CENTER);
        editor.add(updateButton, BorderLayout.SOUTH);
        return editor;
    }

    private JPanel createFullTimeFields() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 6, 4));
        panel.add(new JLabel("Salary:"));
        panel.add(salaryField);
        panel.add(new JLabel("Bonus:"));
        panel.add(bonusField);
        return panel;
    }

    private JPanel createPartTimeFields() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 6, 4));
        panel.add(new JLabel("Hourly Rate:"));
        panel.add(hourlyRateField);
        panel.add(new JLabel("Hours Worked:"));
        panel.add(hoursWorkedField);
        return panel;
    }

    private void addField(JPanel panel, String label, JComponent field) {
        panel.add(new JLabel(label));
        panel.add(field);
    }

    private void loadDepartments(DepartmentManager manager) {
        departments.addAll(manager.getAllDepartments());
        for (Department department : departments) {
            departmentField.addItem(department.getDepartmentName());
        }
    }

    private void configureTable() {
        TableStyle.configure(resultsTable);
        resultsTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                loadSelectedEmployee();
            }
        });
    }

    private void searchEmployees() {
        String query = searchField.getText().trim();
        boolean searchById = "ID".equals(searchType.getSelectedItem());
        int wantedId = -1;

        if (query.isEmpty()) {
            showMessage("Enter an ID or name to search.",
                    "Search required", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (searchById) {
            try {
                wantedId = Integer.parseInt(query);
            } catch (NumberFormatException exception) {
                showMessage("Employee ID must be a number.",
                        "Invalid ID", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (wantedId <= 0) {
                showMessage("Employee ID must be greater than zero.",
                        "Invalid ID", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        searchResults.clear();
        tableModel.setRowCount(0);
        clearEditor();

        for (Employee employee : employeeManager.getAllEmployees()) {
            boolean matches = false;
            if (searchById) {
                if (employee.getEmployeeID() == wantedId) {
                    matches = true;
                }
            } else if (employee.getFullname().equalsIgnoreCase(query)) {
                matches = true;
            }

            if (matches) {
                searchResults.add(employee);
                String departmentName = "";
                if (employee.getDepartment() != null) {
                    departmentName =
                            employee.getDepartment().getDepartmentName();
                }
                tableModel.addRow(new Object[]{
                        employee.getEmployeeID(), employee.getFullname(),
                        employee.getEmployeeType(), departmentName
                });
            }
        }

        if (searchResults.isEmpty()) {
            showMessage("No employees found.",
                    "No results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadSelectedEmployee() {
        int row = resultsTable.getSelectedRow();
        if (row < 0) {
            return;
        }

        int modelRow = resultsTable.convertRowIndexToModel(row);
        selectedEmployee = searchResults.get(modelRow);

        idField.setText(String.valueOf(selectedEmployee.getEmployeeID()));
        nameField.setText(selectedEmployee.getFullname());
        ageField.setText(String.valueOf(selectedEmployee.getAge()));
        positionField.setText(selectedEmployee.getPostion());
        selectDepartment(selectedEmployee.getDepartment());

        if (selectedEmployee instanceof FullTimeEmployee) {
            FullTimeEmployee employee = (FullTimeEmployee) selectedEmployee;
            typeField.setSelectedItem("Full Time");
            salaryField.setText(String.valueOf(employee.getSalary()));
            bonusField.setText(String.valueOf(employee.getBounus()));
        } else if (selectedEmployee instanceof PartTimeEmployee) {
            PartTimeEmployee employee = (PartTimeEmployee) selectedEmployee;
            typeField.setSelectedItem("Part Time");
            hourlyRateField.setText(String.valueOf(employee.getHourlyRate()));
            hoursWorkedField.setText(String.valueOf(employee.getHoursWorked()));
        }

        setEditorEnabled(true);
        showPaymentFields();
    }

    private void selectDepartment(Department employeeDepartment) {
        departmentField.setSelectedIndex(-1);
        if (employeeDepartment == null) {
            return;
        }

        for (int i = 0; i < departments.size(); i++) {
            Department department = departments.get(i);
            if (department.getDepartmentId().equals(
                    employeeDepartment.getDepartmentId())) {
                departmentField.setSelectedIndex(i);
                return;
            }
        }
    }

    private void updateEmployee() {
        if (selectedEmployee == null) {
            showMessage("Select an employee from the results first.",
                    "No employee selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int newId = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String position = positionField.getText().trim();
            int departmentIndex = departmentField.getSelectedIndex();

            if (departmentIndex < 0) {
                showMessage("Select a department.",
                        "Department required", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Department department = departments.get(departmentIndex);
            Employee updated;

            if ("Full Time".equals(typeField.getSelectedItem())) {
                double salary = Double.parseDouble(salaryField.getText().trim());
                double bonus = Double.parseDouble(bonusField.getText().trim());
                updated = new FullTimeEmployee(
                        newId, name, age, department, position, salary, bonus);
            } else {
                double rate = Double.parseDouble(hourlyRateField.getText().trim());
                double hours = Double.parseDouble(hoursWorkedField.getText().trim());
                updated = new PartTimeEmployee(
                        newId, name, age, department, position, rate, hours);
            }

            int oldId = selectedEmployee.getEmployeeID();
            if (employeeManager.updateEmployee(oldId, updated)) {
                searchField.setText(searchById()
                        ? String.valueOf(newId) : name);
                clearEditor();
                searchEmployees();
                showMessage("Employee updated.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException exception) {
            showMessage("Check that ID, age, and payment values are numbers.",
                    "Invalid input", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException exception) {
            showMessage(exception.getMessage(),
                    "Update failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean searchById() {
        return "ID".equals(searchType.getSelectedItem());
    }

    private void showPaymentFields() {
        paymentFields.removeAll();
        if ("Full Time".equals(typeField.getSelectedItem())) {
            paymentFields.add(createFullTimeFields(), BorderLayout.CENTER);
        } else {
            paymentFields.add(createPartTimeFields(), BorderLayout.CENTER);
        }
        paymentFields.revalidate();
        paymentFields.repaint();
    }

    private void setEditorEnabled(boolean enabled) {
        idField.setEnabled(enabled);
        nameField.setEnabled(enabled);
        ageField.setEnabled(enabled);
        positionField.setEnabled(enabled);
        departmentField.setEnabled(enabled);
        typeField.setEnabled(enabled);
        salaryField.setEnabled(enabled);
        bonusField.setEnabled(enabled);
        hourlyRateField.setEnabled(enabled);
        hoursWorkedField.setEnabled(enabled);
        updateButton.setEnabled(enabled);
    }

    private void clearEditor() {
        selectedEmployee = null;
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        positionField.setText("");
        salaryField.setText("");
        bonusField.setText("");
        hourlyRateField.setText("");
        hoursWorkedField.setText("");
        departmentField.setSelectedIndex(-1);
        setEditorEnabled(false);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(GREEN);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));
        return button;
    }

    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }
}
