package cdt.com.ar.footballDemo.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name="association")
public class Association {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private String president;
    
    @OneToMany(targetEntity = Club.class, fetch = FetchType.LAZY ,mappedBy = "association")
    private List<Club> clubs;
}
