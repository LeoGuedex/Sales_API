package leoguedex.com.github.API_Sales_Java.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login")
    @NotEmpty(message = "Field login is mandatory")
    private String login;

    @Column(name = "password")
    @NotEmpty(message = "Field password is mandatory")
    private String password;

    private boolean admin;

}
