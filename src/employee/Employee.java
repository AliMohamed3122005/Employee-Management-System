package employee;
import department.Department;
import managers.EmployeeManager;
public abstract class Employee{
    protected int employeeID;
    protected int age;
    protected String fullname;
    protected Department department;
    protected String postion;

    EmployeeManager E = new EmployeeManager();
    
    public Employee( int id, String name, int age, Department dept, String pos )
    {   if (id<=0 ||!E.isIdUnique(id))throw new IllegalArgumentException("the id already exists or the id is not given");
        this.employeeID=id;
        if (name==null)   throw new IllegalArgumentException("there must be a name");
        this.fullname=name;
        if (age<18) throw new IllegalArgumentException("the employee age can't be lower than 18");
        this.age=age;
        if  (dept==null) throw new IllegalArgumentException("the department can't be null");
        this.department=dept;
        if  (pos==null) throw new IllegalArgumentException("the position can't be null");
        this.postion=pos;
    }
    //getters
    public int getEmployeeID()
    {
        return employeeID;
    }
    public int getAge()
    {return age;}
    public String getFullname()
    {return fullname;}
    public String getPostion()
    {return postion;}
    public Department getDepartment()
    {return department;}

    //setters
    public void setAge(int age)
    {   if (age<18) throw new IllegalArgumentException("the employee age can't be lower than 18");
        this.age=age;
    }
    public void setDepartment(Department dept){ 
        this.department=dept;
    }
    public void setPostion(String postion)
    {   if (postion==null) throw new IllegalArgumentException("the postion can't be null");
        this.postion=postion;
    }


    //calculate salary
    public abstract double calculatesalary();
    public abstract String getEmployeeType();
    public String toString()
    {
        return "name: "+fullname+"\n"+
                "age: "+age+"\n"+
                "ID"+employeeID+"\n"+
                "department"+department+"\n"+
                "postion"+postion;
    }
}