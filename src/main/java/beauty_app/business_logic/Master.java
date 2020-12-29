package beauty_app.business_logic;

import org.springframework.stereotype.Component;

@Component
public class Master extends Employee {
    private String name;
    private Service.ServiceType services;

    public Master(){}

    public Master(String name, Service.ServiceType services, String login){
        super(login);
        this.name = name;
        this.services = services;
    }

    public Master(Integer id, String name, Service.ServiceType services, String login){
        super(id, login);
        this.name = name;
        this.services = services;
    }

    public String getName(){
        return name;
    }

    public Service.ServiceType getServices(){
        return services;
    }

    @Override
    public String toString() {
        return this.name + " (" + Service.ServiceType.serviceTypeToString(this.services) + ")";
    }
}
