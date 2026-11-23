package managers;

import java.util.*;

import department.Department;
import employee.Employee;
import employee.FullTimeEmployee;
import employee.PartTimeEmployee;

public class EmployeeManager
{   
    //Fields
    private List<Employee> employees = new ArrayList<>();
    private FileHandler fileHandler = new FileHandler();

    //Default Constructor

    //Getters
    protected  List<Employee> getEmployees(){return new ArrayList<>(employees);}
    protected  FileHandler getFileHandler(){return this.fileHandler;}

    //SaveToFile
    public void saveToFile()
    {   
        if(employees == null)
            throw new IllegalArgumentException("Employees List Cannot Be Null");
        else fileHandler.saveEmployees(employees);
    }
    //LoadFromFile
    public void loadFromFile()
    {
        List <Employee> bufferEmployees = (fileHandler.loadEmployees());
        if(bufferEmployees == null)
            throw new IllegalArgumentException("Load Employees List Cannot Be Null");
        else 
            {
                employees.clear();
                employees.addAll(bufferEmployees);
            }
    }
    
    //add Employee
    public boolean addEmployee(Employee E)
    {
        if(E==null)
            throw new IllegalArgumentException("Employee Cannot be Null");
        if(E.getEmployeeID()<=0 || !isIdUnique(E.getEmployeeID()))
            throw new IllegalArgumentException("the id already exists or the id is not given");
        if(E.getAge() < 18)
            throw new IllegalArgumentException("the employee age can't be lower than 18");
        if(E.getFullname()==null || E.getFullname().isBlank())
            throw new IllegalArgumentException("there must be a name");
        if(E.getPostion()==null)
            throw new IllegalArgumentException("the position can't be null");
        if(E instanceof FullTimeEmployee)
        {   
            FullTimeEmployee F = (FullTimeEmployee)E;
            if(F.getSalary()<=0) 
                throw new IllegalArgumentException("salary must be greater than 0");
            if (F.getBounus()<0) 
                throw new IllegalArgumentException("Bounus musn't be negative");
        }
        else if(E instanceof PartTimeEmployee)
        {
            PartTimeEmployee P = (PartTimeEmployee)E;
            if (P.getHourlyRate() <= 0 || P.getHoursWorked() < 0)
                throw new IllegalArgumentException("hourly rate must be more than 0  hoursWorked musn't be negative");
        }
        else
            throw new IllegalArgumentException("Unsupported Employee Type");
        employees.add(E);
        saveToFile();
        return true;
    }

    //updateEmployee
    public boolean updateEmployee(int id,Employee updated)
    {   
        if(updated==null)
            throw new IllegalArgumentException("Employee Cannot be Null");
        int index = searchINXByID(id);
        if(index == -1)
            throw new IllegalArgumentException("Employee Not Found");
        if(updated.getEmployeeID()<=0 || !isIdUnique(updated.getEmployeeID(),index))
            throw new IllegalArgumentException("the id already exists or the id is not given");
        if(updated.getAge() < 18)
            throw new IllegalArgumentException("the employee age can't be lower than 18");
        if(updated.getFullname()==null || updated.getFullname().isBlank())
            throw new IllegalArgumentException("there must be a name");
        if(updated.getPostion()==null)
            throw new IllegalArgumentException("the position can't be null");
        if(updated instanceof FullTimeEmployee)
        {   
            FullTimeEmployee F = (FullTimeEmployee)updated;
            if(F.getSalary()<=0) 
                throw new IllegalArgumentException("salary must be greater than 0");
            if (F.getBounus()<0) 
                throw new IllegalArgumentException("Bounus musn't be negative");
        }
        else if(updated instanceof PartTimeEmployee)
        {
            PartTimeEmployee P = (PartTimeEmployee)updated;
            if (P.getHourlyRate() <= 0 || P.getHoursWorked() < 0)
                throw new IllegalArgumentException("hourly rate must be more than 0  hoursWorked musn't be negative");
        }
        else
            throw new IllegalArgumentException("Unsupported Employee Type");
        employees.set(index,updated);
        saveToFile();
        return true;
    } 

    //deleteEmployee
    public boolean deleteEmployee(int id)
    {
        if(id<=0)
            throw new IllegalArgumentException("Invalid ID");
        else
        {   
            int index = searchINXByID(id); 
            if(index == -1)
                throw new IllegalArgumentException("Employee Not Found");
            employees.remove(index);
            saveToFile();
            return true;
        }
    }

    //searchByID
    public Employee searchByID(int id)
    {   
        if(id<=0)
            throw new IllegalArgumentException("Invalid ID");
        for(Employee E : employees)
        {
            if(E.getEmployeeID() == id)
                return E;
        }
        return null;
    }

    //searchByName
    public List <Employee> searchByName(String name)
    {   
        if(name==null || name.isBlank())
            throw new IllegalArgumentException("Invalid name");
        List <Employee> BufferEmployee = new ArrayList<>();
        for(Employee E : employees)
        {
            if(E.getFullname().equalsIgnoreCase(name.trim()))
                BufferEmployee.add(E);
        }
        return BufferEmployee;
    }

    // isIdUnique
    public boolean isIdUnique(int id)
    {
        if(id<=0)
            throw new IllegalArgumentException("Invalid ID");
        for(Employee E:employees)
        {
            if(E.getEmployeeID() == id)
                return false;
        }
        return true;
    }

    //filterByDepartment
    public List <Employee>filterByDepartment(String dept)
    {
        if(dept==null || dept.isBlank())
            throw new IllegalArgumentException("Department Cannot Be Null Or Empty");
        List<Employee> result = new ArrayList<>();
        for(Employee E : employees)
        {
            if(E.getDepartment() != null && E.getDepartment().getDepartmentName().equalsIgnoreCase(dept.trim()))
                result.add(E);
        }
        return result;
    }

    //getAllEmployees
    public List<Employee> getAllEmployees()
    {
        return new ArrayList<>(employees);
    }

    //unassignEmployees
    public void unassignEmployees(Department dept)
    {
        if(dept==null)
            throw new IllegalArgumentException("Department Cannot Be Null");
        for(Employee E : employees)
        {
            if(E.getDepartment() != null && E.getDepartment().getDepartmentId() == dept.getDepartmentId())
                E.setDepartment(null);
        }
        saveToFile();
    }

    //isIdUnique  used in updateEmployee
    public boolean isIdUnique(int newId, int ignoredIndex)
    {   
        if(newId <=0)
            throw new IllegalArgumentException("Invalid New ID");
        for(int i = 0; i < employees.size(); i++)
        {
            if(i != ignoredIndex && employees.get(i).getEmployeeID() == newId)
            {
                return false;
            }
        }
        return true;
    }

    //serachByID but get index used in update and delete
    public int searchINXByID(int id)
    {
        if(id <=0)
            throw new IllegalArgumentException("Invalid ID");
        else
        {
            int index = -1;
            for(int i=0;i<employees.size();i++)
            {
                if(employees.get(i).getEmployeeID() == id)
                {
                    index = i;
                    break;
                }
            }
            return index;
        }
    }
}
