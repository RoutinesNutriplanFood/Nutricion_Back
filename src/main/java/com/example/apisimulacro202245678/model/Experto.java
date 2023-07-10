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
@Table(name="expertos")
public class Experto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",length = 200, nullable = true)
    private String name;

    @Column(name = "edad",length =2, nullable = true)
    private String edad;

    @Column(name = "presentacion",length =300, nullable = true)
    private String presentacion;

    @Column(name = "especialidad",length =300, nullable = true)
    private String especialidad;

    @Column(name = "phone",length =9, nullable = true)
    private String phone;

}
