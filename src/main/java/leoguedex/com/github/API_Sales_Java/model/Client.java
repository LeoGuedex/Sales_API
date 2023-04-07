package leoguedex.com.github.API_Sales_Java.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100)
    @NotEmpty(message = "Name field is mandatory")
    private String name;

    @Column(name = "cpf", length = 11)
    @CPF(message = "Insert a valid CPF")
    @NotEmpty(message = "Field name is mandatory")
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private Set<Orders> orders;

}
