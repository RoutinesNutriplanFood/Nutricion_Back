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
@Table(name="planes")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",length = 40, nullable = false)
    private String name;

    @Column(name = "descripcion",length =500, nullable = false)
    private String descripcion;

    @Column(name = "duracion",length =2, nullable = false)
    private String duracion;

    @Column(name = "objeticos",length =150, nullable = false)
    private String objeticos;

    @Column(name = "restricciones",length =150, nullable = false)
    private String restricciones;

}
