package com.example.fitness_app.Test;

import lombok.*;
import java.util.UUID;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "test")
@Entity
@Builder
public class TestEntity {
    @Id
    @Column(name = "id")
    private final String id = UUID.randomUUID().toString();

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;
}
