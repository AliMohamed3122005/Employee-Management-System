package managers;

import java.util.List;
import java.util.ArrayList;
import department.Department;
import managers.FileHandler;

public class DepartmentManager
{
    private List<Department> departments;
    private FileHandler fileHandler;
    private EmployeeManager employeeManager;

    public DepartmentManager(){
        departments = new ArrayList<>();
        fileHandler = new FileHandler();
        employeeManager = new EmployeeManager();
        loadFromFile();
    }

    public void loadFromFile() {
        
        departments = fileHandler.loadDepartments();

        if (departments == null) {
            departments = new ArrayList<>();
        }   
        }

    public void saveToFile() {
        fileHandler.saveDepartments(departments);
        }

    public boolean isIdUnique(String id){
        if(id == null || id.trim().isEmpty()){
            return false;
        }
        String normalizedId = id.trim().toUpperCase();
        for (Department department : departments){
            if(department.getDepartmentId().equals(normalizedId)){
                return false;
            }
        }
        return true;
    }


    public boolean isNameUnique(String name){
        if (name == null || name.trim().isEmpty()||!name.trim().matches("[a-zA-Z ]+")) {
        return false;
        }
        String normalizedName = name.trim().toUpperCase();
        for(Department department : departments){
            if(department.getDepartmentName().equals(normalizedName)){
                return false;
            }
        }
        return true;
    }

    public boolean addDepartment(Department d){
        if (d == null) {
            return false;
        }

        String id = d.getDepartmentId();
        String name = d.getDepartmentName();

            if (id == null || id.trim().isEmpty()
                || name == null || name.trim().isEmpty() || !name.trim().matches("[a-zA-Z ]+")) {
            return false;
        }

            id = id.trim().toUpperCase();
            name = name.trim().toUpperCase();

            d.setDepartmentId(id);
            d.setDepartmentName(name);

            if (!isIdUnique(id)) {
            return false;
            }

            if (!isNameUnique(name)) {
                return false;
            }
            departments.add(d);

            saveToFile();

            return true;
        }



    public boolean deleteDepartment(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        String normalizedId = id.trim().toUpperCase();

        Department departmentToDelete = null;


        for(Department department : departments) {
            if (department.getDepartmentId().equals(normalizedId)) {
                departmentToDelete = department;
                break;
            }
        }

        if (departmentToDelete == null) {
            return false;
        }

        employeeManager.unassignEmployees(departmentToDelete);
        departments.remove(departmentToDelete);
        saveToFile();
        return true;
        }


    public List<Department> getAllDepartments() {
        return new ArrayList<>(departments);
}


}
