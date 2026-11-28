package gui.panels;

import department.Department;
import employee.Employee;
import employee.FullTimeEmployee;
import employee.PartTimeEmployee;
import managers.DepartmentManager;
import managers.EmployeeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddEmployeePanel extends JPanel {
    private static final Color GREEN = new Color(0x075A38);
    private static final Color IVORY = new Color(0xFBF9F1);

    private final EmployeeManager employeeManager;
    private final List<Department> departments;

    private final JTextField idField = new JTextField(16);
    private final JTextField nameField = new JTextField(16);
    private final JTextField ageField = new JTextField(16);
    private final JTextField positionField = new JTextField(16);
    private final JComboBox<String> departmentField = new JComboBox<>();
    private final JComboBox<String> typeField =
            new JComboBox<>(new String[]{"Full Time", "Part Time"});

    private final JTextField salaryField = new JTextField(10);
    private final JTextField bonusField = new JTextField(10);
    private final JTextField hourlyRateField = new JTextField(10);
    private final JTextField hoursWorkedField = new JTextField(10);
    private final JPanel paymentFields = new JPanel(new BorderLayout());

    public AddEmployeePanel(EmployeeManager employeeManager,
                            DepartmentManager departmentManager) {
        if (employeeManager == null || departmentManager == null) {
            throw new IllegalArgumentException("Managers cannot be null");
        }

        this.employeeManager = employeeManager;
        departments = departmentManager.getAllDepartments();

        for (Department department : departments) {
            departmentField.addItem(department.getDepartmentName());
        }

        setLayout(new BorderLayout(0, 20));
        setBackground(IVORY);
        setBorder(BorderFactory.createEmptyBorder(24, 34, 20, 34));

        add(createHeader(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.CENTER);

        typeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                showPaymentFields();
            }
        });

        showPaymentFields();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Add Employee");
        title.setFont(new Font("Segoe UI", Font.BOLD, 27));
        title.setForeground(new Color(0x123D2A));
        header.add(title, BorderLayout.WEST);
        return header;
    }

    private JPanel createForm() {
        JPanel form = new JPanel(new BorderLayout(0, 16));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD9D9D3)),
                BorderFactory.createEmptyBorder(22, 24, 22, 24)));

        JPanel fields = new JPanel(new GridLayout(0, 2, 14, 12));
        fields.setOpaque(false);

        addField(fields, "Employee ID:", idField);
        addField(fields, "Full Name:", nameField);
        addField(fields, "Age:", ageField);
        addField(fields, "Position:", positionField);
        addField(fields, "Department:", departmentField);
        addField(fields, "Employee Type:", typeField);

        paymentFields.add(createFullTimeFields(), BorderLayout.CENTER);
        addField(fields, "Payment Details:", paymentFields);

        JButton addButton = new JButton("Add Employee");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(GREEN);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                addEmployee();
            }
        });

        form.add(fields, BorderLayout.CENTER);
        form.add(addButton, BorderLayout.SOUTH);
        return form;
    }

    private JPanel createFullTimeFields() {
        JPanel fields = new JPanel(new GridLayout(2, 2, 6, 4));
        fields.add(new JLabel("Salary:"));
        fields.add(salaryField);
        fields.add(new JLabel("Bonus:"));
        fields.add(bonusField);
        return fields;
    }

    private JPanel createPartTimeFields() {
        JPanel fields = new JPanel(new GridLayout(2, 2, 6, 4));
        fields.add(new JLabel("Hourly Rate:"));
        fields.add(hourlyRateField);
        fields.add(new JLabel("Hours Worked:"));
        fields.add(hoursWorkedField);
        return fields;
    }

    private void addField(JPanel panel, String label, JComponent field) {
        JLabel text = new JLabel(label);
        text.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(text);
        panel.add(field);
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

    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
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
            Employee employee;

            if ("Full Time".equals(typeField.getSelectedItem())) {
                double salary = Double.parseDouble(salaryField.getText().trim());
                double bonus = Double.parseDouble(bonusField.getText().trim());
                employee = new FullTimeEmployee(
                        id, name, age, department, position, salary, bonus);
            } else {
                double rate = Double.parseDouble(hourlyRateField.getText().trim());
                double hours = Double.parseDouble(hoursWorkedField.getText().trim());
                employee = new PartTimeEmployee(
                        id, name, age, department, position, rate, hours);
            }

            if (employeeManager.addEmployee(employee)) {
                clearFields();
                showMessage("Employee added successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException exception) {
            showMessage("Enter valid numbers in the ID, age, and payment fields.",
                    "Invalid input", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException exception) {
            showMessage(exception.getMessage(),
                    "Add failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        positionField.setText("");
        salaryField.setText("");
        bonusField.setText("");
        hourlyRateField.setText("");
        hoursWorkedField.setText("");
        departmentField.setSelectedIndex(-1);
    }

    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }
}
