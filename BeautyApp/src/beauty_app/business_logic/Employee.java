package beauty_app.business_logic;

import org.springframework.stereotype.Component;

@Component
public class Employee {
    public enum EmployeeType { ADMIN, MASTER }

    private Integer id;
    private String login;
    private String password;
    private EmployeeType employeeType;

    public Employee(){
    }

    public Employee(String login, String password, EmployeeType employeeType){
        this.login = login;
        this.password = password;
        this.employeeType = employeeType;
    }

    public Employee(Integer id, String login, String password, EmployeeType employeeType){
        this.id = id;
        this.login = login;
        this.password = password;
        this.employeeType = employeeType;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getLogin(){
        return login;
    }

    public String getPassword(){
        return password;
    }

    public Boolean authorize(String password){
        if (this.password.equals(password))
            return true;
        return false;
    }

    public EmployeeType getEmployeeType(){
        return employeeType;
    }

    public void setEmployeeType(EmployeeType type) {
        employeeType = type;
    }
}
