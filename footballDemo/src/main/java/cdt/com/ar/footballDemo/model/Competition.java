package cdt.com.ar.footballDemo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name="competition")
public class Competition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name= "name", columnDefinition = "VARCHAR(300)")
    private String name;
    @Column(name = "quantity_price", length = 10, nullable = false, unique = true, updatable = false)
    private Integer quantityPrice;
    @Column(name = "start_date", columnDefinition = "DATE")
    private LocalDate startDate;
    @Column(name = "end_date", columnDefinition = "DATE")
    private LocalDate endDate;
    /* length => cantidad de caracteres
    nullable => (con ctrl+espacio veo los valores por defecto), si lo paso a false, si quiero dejar ese valor nulo, dará error
    unique => simil, veo el valor por defecto, si lo paso a true, evitará por ejemplo que se guarden nombres de usuarios duplicados
    updatable => simil... en false No se puede actualizar el valor
    columnDefinition = "DATE" => Para evitar problemas, definiendo el tipo de datos que va a guardar en la tabla
    En el workbench, en competition, click derecho, alter table... y veo como impactan todas estas condiciones */
    
    /* Si descomento la relación, crea una 2da tabla intermedia competition_club... ademas de la de club_competition)
    @ManyToMany(targetEntity = Club.class, fetch = FetchType.LAZY)
    private List<Club> clubs;*/
    
}
