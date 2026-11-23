package employee;

import department.Department;

public class FullTimeEmployee extends Employee
{
    private double salary;
    private double Bounus;
    public FullTimeEmployee(int id, String fullname, int age, Department dept,
                            String pos,double salary, double Bounus)
    {
        super(id,fullname,age,dept,pos);
        if(salary<=0) throw new IllegalArgumentException("salary must be greater than 0");
        this.salary = salary;
        if (Bounus<0) throw new IllegalArgumentException("Bounus musn't be negative");
        this.Bounus = Bounus;
    }
    //getters
    public double getSalary() {return salary;}
    public double getBounus() {return Bounus;}
    public String getEmployeeType(){return "Full Time";}
    //setters
    public  void setSalary(double salary) {
        if(salary<=0) throw new IllegalArgumentException("salary must be greater than 0");
        this.salary = salary;
    }
    public void setbonus(double bonus){
        if (Bounus<0) throw new IllegalArgumentException("Bounus musn't be negative");
        this.Bounus=bonus;
    }

    public double calculatesalary(){return salary+Bounus;}

}