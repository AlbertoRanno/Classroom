package cdt.com.ar.footballDemo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "coach")
public class Coach {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private String nationality;
    private Integer age;
    // Si la descomento, la relacion OneToOne, se vuelve bidireccional, ya que desde ambas entidades, se podría acceder al info de la relación, gracias a getClub()
    /*@OneToOne(targetEntity = Club.class)
    private Club club;*/
    
}
