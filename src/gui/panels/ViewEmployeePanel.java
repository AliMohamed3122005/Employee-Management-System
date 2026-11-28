package gui.panels;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import employee.Employee;
import managers.EmployeeManager;
import gui.util.*;

public class ViewEmployeePanel extends JPanel
{
    private static final Color Ivory = new Color(0xFBF9F1);
    private static final Color Green = new Color(0x075A38);
    private static final Color Grid = new Color(0xD9D9D3);

    private final EmployeeManager employeeManager;

    private final DefaultTableModel tableModel = new DefaultTableModel(
        new String[]{"ID","Name","Age","Position","Department","Type","Salary"},0){
            @Override 
            public boolean isCellEditable(int row,int col)
            {
                return false;
            }
        };
    
    private final JTable employeeTable = new JTable(tableModel);

    public ViewEmployeePanel(EmployeeManager employeeManager)
    {
        if(employeeManager == null)
            throw new IllegalArgumentException("Employee Manger Mustn't be NULL");
        this.employeeManager = employeeManager;
        
        setLayout(new BorderLayout(0,16));
        setBackground(Ivory);
        setBorder(BorderFactory.createEmptyBorder(24,28,20,28));
        add(createHeader(),BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Grid));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane,BorderLayout.CENTER);

        TableStyle.configure(employeeTable);
        setColumnWidths();
        RefreshTable();
    }
    
    private JPanel createHeader()
    {
        JPanel Header = new JPanel(new BorderLayout());
        Header.setOpaque(false);

        JLabel Title = new JLabel("View Employees");
        Title.setFont(new Font("Segoe UI", Font.BOLD, 27));
        Title.setForeground(Green);
        
        JButton Refresh = new JButton("Refresh");
        Refresh.setFont(new Font("Segoe UI", Font.BOLD, 15));
        Refresh.setBackground(Green);
        Refresh.setBackground(Color.WHITE);
        Refresh.setFocusPainted(false);
        Refresh.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent event)
            {
                RefreshTable();
            }
        });
        Header.add(Title,BorderLayout.EAST);
        Header.add(Title,BorderLayout.WEST);

        return Header;
    }

    private void setColumnWidths()
    {
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(140);
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(140);
        employeeTable.getColumnModel().getColumn(6).setPreferredWidth(90);
    }

    private void RefreshTable()
    {
        tableModel.setRowCount(0);
        List<Employee> employees = employeeManager.getAllEmployees();
        for(Employee E : employees)
        {
            String DepartmentName;
            if(E.getDepartment() == null)
                DepartmentName = "";
            else 
                DepartmentName = E.getDepartment().getDepartmentName();

            tableModel.addRow(
                new Object[]{
                    E.getEmployeeID(),
                    E.getFullname(),
                    E.getAge(),
                    E.getPostion(),
                    DepartmentName,
                    E.getEmployeeType(),
                    E.calculatesalary()
                }
            );
        }
    }
}