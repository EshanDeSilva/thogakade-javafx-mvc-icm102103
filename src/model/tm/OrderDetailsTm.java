package model.tm;

import lombok.*;
import model.OrderDetails;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailsTm {
    private String id;
    private LocalDate date;
    private String customerId;
    private List<OrderDetails> details;
}
