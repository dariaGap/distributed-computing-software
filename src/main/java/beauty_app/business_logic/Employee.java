package beauty_app.business_logic;

import org.springframework.stereotype.Component;

@Component
public class Employee {
    private Integer id;
    private String login;

    public Employee(){
    }

    public Employee(String login){
        this.login = login;
    }

    public Employee(Integer id, String login){
        this.id = id;
        this.login = login;
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
}
