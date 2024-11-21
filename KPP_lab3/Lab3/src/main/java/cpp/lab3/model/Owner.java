package cpp.lab3.model;

import java.io.Serializable;
import java.util.Objects;

public class Owner implements Serializable {
    private String firstName;
    private String lastName;
    private int age;
    
    public Owner() {
    }
    
    public Owner(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Owner owner)) {
            return false;
        }
        return age == owner.age && Objects.equals(firstName,
                                                  owner.firstName) && Objects.equals(lastName,
                                                                                     owner.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}
