package com.javovka.userservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Iphone {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String model;
    private int quantities;
    private int price;

}
