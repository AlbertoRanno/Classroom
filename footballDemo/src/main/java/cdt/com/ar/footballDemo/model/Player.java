package cdt.com.ar.footballDemo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "player")
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private String position;
    private int Age;
    
    @ManyToOne(targetEntity = Club.class)
    private Club club;
}
