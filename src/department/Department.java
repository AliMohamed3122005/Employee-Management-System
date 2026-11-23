package department;

public class Department
{
    private String departmentId;
    private String departmentName;

    public Department(String departmentId, String departmentName) {
        setDepartmentId(departmentId);
        setDepartmentName(departmentName);
    }


    public void setDepartmentId(String departmentId) {
        if(departmentId == null || departmentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Department ID cannot be null or empty.");
        }
        this.departmentId = departmentId.trim().toUpperCase();
    }

    public void setDepartmentName(String departmentName) {
        if(departmentName == null || departmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be null or empty.");
        }
        if(!departmentName.trim().matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Department name must contain only letters and spaces.");
        }
        this.departmentName = departmentName.trim().toUpperCase();
    }


        public String getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }


}
