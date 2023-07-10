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
@Table(name="recetas")
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",length = 30, nullable = false)
    private String name;

    @Column(name = "ingredientes",length =150, nullable = false)
    private String ingredientes;

    @Column(name = "instrucciones",length =500, nullable = false)
    private String instrucciones;

    @Column(name = "propiedades",length =150, nullable = false)
    private String propiedades;

}
