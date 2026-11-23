package managers;
import employee.Employee;
import employee.FullTimeEmployee;
import employee.PartTimeEmployee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import department.*;

/**
 * FileHandler
 */
public class FileHandler 
{   
    // Fields
    private final String employeeFilePath = "employees.csv";
    private final String departmentFilePath = "department.csv";

    // default Constructor

    // Getter
    public String getEmployeeFilePath(){return this.employeeFilePath;}
    
    public String getDepartmentFilePath(){return this.departmentFilePath;}

    protected void saveEmployees(List <Employee> employees)
    {
        if(employees==null)
            throw new IllegalArgumentException("File Employees Cannot be Null");
        else 
        {
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(employeeFilePath)))
            {
                writer.write("Type,Employee ID,age,Full Name,Department Name,Position,Salary Details");
                writer.newLine();
                for(Employee E : employees)
                {
                    if(E instanceof FullTimeEmployee)
                    {
                        writer.write("Full-Time,");
                        FullTimeEmployee F = (FullTimeEmployee) E;
                        writer.write(F.getEmployeeID() + ",");
                        writer.write(F.getAge() + ",");
                        writer.write(F.getFullname() + ",");
                        writer.write(F.getDepartment().getdepartmentName() + ",");
                        writer.write(F.getPostion() + ",");
                        writer.write(F.getSalary()  + "|" + F.getBounus());
                        writer.newLine();
                    }
                    else if(E instanceof PartTimeEmployee)
                    {
                        writer.write("Part-Time,");
                        PartTimeEmployee F = (PartTimeEmployee) E;
                        writer.write(F.getEmployeeID() + ",");
                        writer.write(F.getAge() + ",");
                        writer.write(F.getFullname() + ",");
                        writer.write(F.getDepartment().getdepartmentName() + ",");
                        writer.write(F.getPostion() + ",");
                        writer.write(F.getHourlyRate() + "|" + F.getHoursWorked());
                        writer.newLine();
                    }
                }
            }
            catch(IOException e)
            {
                System.out.println("Error Cannot Saving File" + e.getMessage());
            }
        }
    }   
    protected List<Employee> loadEmployees()
    {   
        File file = new File(employeeFilePath);
        if(! file.exists())
            return new ArrayList<>();
        else 
        {
            try(BufferedReader reader = new BufferedReader(new FileReader(employeeFilePath)))
            {   
                reader.readLine(); // Header
                List<Employee> employees = new ArrayList<>();
                String line;
                List <Department> departments = loadDeparments();
                while((line = reader.readLine()) != null) 
                {   
                    if(line.isBlank())
                    {continue;}
                    String []data = line.split(",");
                    if(data[0].equals("Full-Time"))
                    {   
                        int employeeID;
                        try{employeeID = Integer.parseInt(data[1]);}
                        catch(NumberFormatException e){continue;}
                        int age;
                        try{age = Integer.parseInt(data[2]);}
                        catch(NumberFormatException e){continue;}
                        String fullName = data[3].trim();
                        String departmentName = data[4].trim();
                        Department employeeDepartment = null;
                        for(Department dept : departments)
                        {   
                            if(dept.getdepartmentName().equalsIgnoreCase(departmentName))
                            {
                                employeeDepartment = dept;
                                break;
                            }
                        }
                        String position = data[5].trim();
                        String []dataSalary = data[6].split("\\|");
                        if(dataSalary.length != 2)
                            continue;
                        double salary = Double.parseDouble(dataSalary[0]);
                        double bonus = Double.parseDouble(dataSalary[1]);
                        if(employeeDepartment != null)
                        {
                            FullTimeEmployee E = new FullTimeEmployee(employeeID, fullName, age, employeeDepartment, position, salary, bonus);
                            employees.add(E);
                        }
                        else 
                        {
                            System.out.println("Cannot Load Emplyee Without Department");
                        }
                    }


                    else if(data[0].equals("Part-Time"))
                    {
                        int employeeID = Integer.parseInt(data[1]);
                        int age = Integer.parseInt(data[2]);
                        String fullName = data[3].trim();
                        String departmentName = data[4].trim();
                        Department employeeDepartment = null;
                        for(Department dept : departments)
                        {
                            if(dept.getdepartmentName().equalsIgnoreCase(departmentName))
                            {
                                employeeDepartment = dept;
                                break;
                            }
                        }
                        String position = data[5].trim();
                        String []dataSalary = data[6].split("\\|");
                        if(dataSalary.length != 2)
                            continue;
                        double hourlyRate = Double.parseDouble(dataSalary[0]);
                        double hoursWorked = Double.parseDouble(dataSalary[1]);
                        if(employeeDepartment != null)
                        {
                            PartTimeEmployee E = new PartTimeEmployee(employeeID, fullName, age, employeeDepartment, position, hourlyRate, hoursWorked);
                            employees.add(E);
                        }
                        else 
                        {
                            System.out.println("Cannot Load Emplyee Without Department");
                        }
                    }
                    else continue;


                }
                return employees;
            }
            catch(IOException e)
            {
                System.out.println("Error Cannot Open File" + e.getMessage());
                return new ArrayList<>();
            }
        }
    }

    protected void saveDepartments(List <Department> departments)
    {
        if(departments==null)
            throw new IllegalArgumentException("File departments Cannot be Null");
        else 
        {
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(departmentFilePath)))
            {
                writer.write("Department Name,Depaertment ID");
                writer.newLine();
                for(Department D : departments)
                {
                    writer.write(D.getdepartmentName() + ",");
                    writer.write(D.getDepartmentId() + "");
                    writer.newLine();
                }
            }
            catch(IOException e)
            {
                System.out.println("Error Cannot Saving File" + e.getMessage());
            }
        }
    }

    protected List<Department> loadDeparments()
    {   
        File file = new File(departmentFilePath);
        if(! file.exists())
            return new ArrayList<>();
        else 
        {
            try(BufferedReader reader = new BufferedReader(new FileReader(departmentFilePath)))
            {   
                reader.readLine(); // Header
                List<Department> departments = new ArrayList<>();
                String line;

                while((line = reader.readLine()) != null) 
                {   
                    if(line.isBlank())
                    {continue;}
                    String []data = line.split(",");
                    if(data.length != 2)
                        continue;
                    String departmentName = data[0].trim();
                    if(departmentName.isEmpty())
                        continue;
                    int departmentId ;
                    try{departmentId = Integer.parseInt(data[1].trim());}
                    catch(NumberFormatException e){continue;}

                    if(departmentId <= 0)
                        continue;
                    Department D = new Department(departmentName,departmentId);
                    departments.add(D);
                }
                return departments;
            }
            catch(IOException e)
            {
                System.out.println("Error Cannot Open File" + e.getMessage());
                return new ArrayList<>();
            }
        }
    }
}