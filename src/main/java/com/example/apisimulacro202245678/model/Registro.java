package com.example.apisimulacro202245678.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="registros")
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    @Column(name = "name",length = 30, nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    @Column(name = "cantidad",length =40, nullable = false)
    private String cantidad;
    public String getCantidad() {
        return cantidad;
    }

    @Column(name = "date")
    private Date date;

}
