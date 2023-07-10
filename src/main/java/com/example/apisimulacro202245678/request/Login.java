package com.example.apisimulacro202245678.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Login {

    @Column(name = "email",length =30, nullable = false)
    private String email;

    @Column(name = "password",length =20, nullable = false)
    private String password;


}
