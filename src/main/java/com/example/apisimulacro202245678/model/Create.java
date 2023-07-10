package com.example.apisimulacro202245678.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="creates")
public class Create {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",length = 30, nullable = false)
    private String name;

    @Column(name = "email",length =30, nullable = false)
    private String email;

    @Column(name = "password",length =8, nullable = false)
    private String password;

    @Column(name = "peso",length =3, nullable = false)
    private String peso;

    @Column(name = "altura",length =3, nullable = false)
    private String altura;
}
