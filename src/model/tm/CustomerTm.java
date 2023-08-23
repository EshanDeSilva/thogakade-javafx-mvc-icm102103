package model.tm;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTm {
    private String id;
    private String name;
    private String address;
    private double salary;
    private JFXButton btn;

    public void setSalary(double salary) {
        if (salary>=0){
            this.salary = salary;
        }
    }
}
