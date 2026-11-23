package employee;


import department.Department;

public class  PartTimeEmployee extends Employee
{
    private double hourlyrate;
    private double hoursworked;

    public PartTimeEmployee(int id, String name, int age, Department dept,
                            String pos, double hourlyRate, double hoursWorked)
    {
        super(id,name,age,dept,pos);
        if (hourlyRate <= 0 || hoursWorked <0)throw new IllegalArgumentException("hourly rate must be more than 0  hoursWorked musn't be negative");
        this.hourlyrate = hourlyRate;
        this.hoursworked = hoursWorked;
    }
    //getters
    public double getHourlyRate(){return hourlyrate;}
    public double getHoursWorked(){return hoursworked;}
    public String getEmployeeType(){return "PartTimeEmployee";}

    //setters
    public void setHourlyRate(double hourlyRate){
        if (hourlyrate<=0)throw new IllegalArgumentException("hourly rate must be more than 0");
        this.hourlyrate = hourlyRate;
    }
    public void sethoursWorked(double hoursWorked){
        if  (hoursworked<0)throw new IllegalArgumentException("hoursWorked musn't be negative");
        this.hoursworked = hoursWorked;
    }

    public double calculatesalary(){return hourlyrate*hoursworked;}
}