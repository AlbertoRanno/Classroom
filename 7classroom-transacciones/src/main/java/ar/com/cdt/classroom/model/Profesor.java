package ar.com.cdt.classroom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table (name="Profesor")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idProfesor")
public class Profesor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_profesor")
    private int idProfesor;
    
    @Column(name="nombre_profesor", length= 45)
    private String nombreProfesor;
    
    @Column (name="apellido_profesor", length= 45)
    private String apellidoProfesor;
    
    @Column (name="telefono_profesor", length= 45)
    private String telefonoProfesor;
    
    @Column (name="asignatura_profesor", length= 45)
    private String asignaturaProfesor;
    
    @Column (name="antiguedad_profesor")
    private int antiguedadProfesor; // length= 45 es para definir la longitud del String, en Integer no hace falta
    
    /*
    La siguiente anotación, se entiende si se ve en relación a la que está en el otro modelo.
    mappedBy indica que la tabla que tiene la columna que almacena las llaves foráneas de la relación, es la del otro modelo.
    "profesor" indica que la relación está mapeada por el atributo de la otra clase modelo, al que llamé profesor:.
    @OneToOne(optional = true)
    @JoinColumn(name="profesor_id")
    private Profesor profesor;
    "alumno" guarda una referencia al objeto, no solamente su ID.
    el atributo optional, al establecerlo en la clase dueña de la relación, hace que no sea necesario aclararlo en la
    otra parte de la relación 1 a 1, pero, se puede hacer, para recordar la no obligatoriedad de la relación.
    */
    
    @OneToOne(mappedBy = "profesor", optional = true)
    @JsonBackReference
    private Alumno alumno;
    
    
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference
    @JsonBackReference
    private List<Curso> cursos;
    
}