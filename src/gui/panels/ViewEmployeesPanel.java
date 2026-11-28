package gui.panels;

import employee.Employee;
import managers.EmployeeManager;
import gui.util.TableStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewEmployeesPanel extends JPanel {
    private static final Color IVORY = new Color(0xFBF9F1);
    private static final Color GREEN = new Color(0x075A38);
    private static final Color GRID = new Color(0xD9D9D3);

    private final EmployeeManager employeeManager;

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Age", "Position", "Department", "Type", "Salary"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable employeeTable = new JTable(tableModel);

    public ViewEmployeesPanel(EmployeeManager employeeManager) {
        if (employeeManager == null) {
            throw new IllegalArgumentException("EmployeeManager cannot be null");
        }
        this.employeeManager = employeeManager;

        setLayout(new BorderLayout(0, 16));
        setBackground(IVORY);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 20, 28));

        add(createHeader(), BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(GRID));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        TableStyle.configure(employeeTable);
        setColumnWidths();
        refreshTable();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("View Employees");
        title.setFont(new Font("Segoe UI", Font.BOLD, 27));
        title.setForeground(new Color(0x123D2A));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshButton.setBackground(GREEN);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                refreshTable();
            }
        });

        header.add(title, BorderLayout.WEST);
        header.add(refreshButton, BorderLayout.EAST);
        return header;
    }

    private void setColumnWidths() {
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(65);
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(175);
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(55);
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(125);
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(150);
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        employeeTable.getColumnModel().getColumn(6).setPreferredWidth(100);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Employee> employees = employeeManager.getAllEmployees();

        for (Employee employee : employees) {
            String departmentName = "";
            if (employee.getDepartment() != null) {
                departmentName = employee.getDepartment().getDepartmentName();
            }

            tableModel.addRow(new Object[]{
                    employee.getEmployeeID(),
                    employee.getFullname(),
                    employee.getAge(),
                    employee.getPostion(),
                    departmentName,
                    employee.getEmployeeType(),
                    employee.calculatesalary()
            });
        }
    }
}
