package ar.com.cdt.classroom.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity //  Indica que esta clase es una entidad JPA, lo que significa que se mapeará a una tabla en la base de datos.
@Data // Proporciona automáticamente getters, setters, toString(), equals(), y hashCode(), gracias a Lombok.
@Table (name="Profesor") // Especifica el nombre de la tabla en la base de datos a la que se mapeará esta entidad.
public class Profesor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Id y @GeneratedValue Indican que idProfesor es la clave primaria de la tabla y que se generará automáticamente.
    @Column(name="id_profesor") // Define el mapeo de los atributos de la clase a las columnas de la tabla de la base de datos.
    private int idProfesor;
    
    @Column(name="nombre_profesor", length= 45)
    private String nombreProfesor;
    
    @Column (name="apellido_profesor", length= 45)
    private String apellidoProfesor;
    
    @Column (name="telefono_profesor", length= 45)
    private String telefonoProfesor;
    
    @Column (name="asignatura_profesor", length= 45)
    private String asignaturaProfesor;
    
    @Column (name="antiguedad_profesor") // length= 45 es para definir la longitud del String, en Integer no hace falta
    private int antiguedadProfesor;
    
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
    private Alumno alumno;
}