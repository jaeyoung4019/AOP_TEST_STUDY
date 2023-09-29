package test.security.security_test.diagram.class_diagram;

import org.springframework.scheduling.annotation.Schedules;

public class User {
    private String id;
    private String password;
    private Schedule schedule;

    public User(Schedule schedule) {
        this.schedule = schedule;
    }

    public void work(){
        schedule.test();
    }
}
