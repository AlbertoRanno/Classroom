package cdt.com.ar.footballDemo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @OneToOne(targetEntity = Coach.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_coach") //personalizo el nombre de la columna
    private Coach coach;
    /*targetEntity = Coach.class => especifico cual es la entidad con la que se va a hacer la relación
    fetch no se usa en 1a1, se usa cuando hay listas (many)
    cascade = CascadeType.PERSIST (Persistir es guardar en la bbdd) => si guardo un club, con un coach, gracias a esta propiedad, va a insertar al coach en su tabla 
    (haciendo dos inserts de una vez) //simil, con el REMOVE, si el elimino al equipo, elimina al coach de su tabla */

    @OneToMany(targetEntity = Player.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "club")
    private List<Player> players;
    /*Con carga Lazy (perezosa) tengo disponible el getPlayers(), para llamarlo cuando lo necesite, es lo más usado, dado que el EAGER, trae todos siempre, y pueden ser miles.
    Sin el mappedBy= "club", creaba una tabla intermedia para guardar como llaves foráneas los IDs de cada tabla, pero eso solo debe hacerse para ManyToMany.
    En el caso de 1aMuchos, se debe enviar el ID del que es el 1 de la relación, para que los guarde la tabla del lado del many, lo cual tiene sentido si lo pensas.. 
    Y el mappedBy= "club" justamente lo que indica es eso. El id de esta entidad, Club, es el que está del lado del One, entonces que sea mapeada por el atributo "club", que es el
    atributo que guarda los ids de la relacion, en la otra tabla. */

    @ManyToOne(targetEntity = Association.class)
    private Association association;
    /*IDEM En 1aMuchos o Muchosa1 la entidad que contiene el 1, debe pasar como clave foranea a la tabla que contiene la relacion de muchos*/

    @ManyToMany(targetEntity = Competition.class, fetch = FetchType.LAZY)
    @JoinTable(name = "competiciones_inscriptos", joinColumns = @JoinColumn(name = "club"), inverseJoinColumns = @JoinColumn(name = "competitions")) //Personalizo nombres, no es necesario
    private List<Competition> competitions;
}
