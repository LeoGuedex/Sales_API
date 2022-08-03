package leoguedex.com.github.API_Sales_Java.model;

import leoguedex.com.github.API_Sales_Java.model.enums.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "date_order")
    private LocalDate dateOrder;

    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;

    @Column(name = "status")
    private StatusOrder statusOrder;

    @OneToMany(mappedBy = "orders")
    private List<OrderedItem> items;

}
