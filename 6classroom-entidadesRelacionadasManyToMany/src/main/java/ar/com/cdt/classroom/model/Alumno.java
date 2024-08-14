package ar.com.cdt.classroom.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "Alumno")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idAlumno")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno")
    private int idAlumno;

    @Column(name = "nombre_alumno", length = 45)
    private String nombreAlumno;

    @Column(name = "apellido_alumno", length = 45)
    private String apellidoAlumno;

    @Column(name = "telefono_alumno", length = 45)
    private String telefonoAlumno;

    @Column(name = "email_alumno", length = 45)
    private String emailAlumno;

    @OneToOne(optional = true)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    /* Para implementar una relación muchos a muchos entre Curso y Alumno, se suele usar una tabla intermedia que almacene las llaves foráneas de ambas entidades.
    En JPA, para esta tabla intermedia no necesariamente necesito crear un modelo, ya que se puede manejar directamente con anotaciones en las entidades Curso y Alumno. 
    Primero hay que definir la relación muchos a muchos en ambas entidades (Alumno y Curso). Esto se hace usando la anotación @ManyToMany y @JoinTable.
    Con las anotaciones en ambas entidades, JPA se encargará automáticamente de crear la tabla intermedia llamada alumno_curso con las columnas alumno_id y curso_id. */
    @ManyToMany
    @JoinTable(//se utiliza en relaciones de muchos a muchos para especificar la tabla intermedia que se encargará de almacenar las relaciones entre las dos entidades involucradas.
            //Con esto evito crear el modelo Alumno_Curso
            name = "alumno_curso", // como quiero que se llame la tabla intermedia.
            joinColumns = @JoinColumn(name = "alumno_id"), // Especifica la columna que guardará la clave foránea que apunta a la entidad Alumno, osea, ésta entidad.
            inverseJoinColumns = @JoinColumn(name = "curso_id") //Especifica la columna que guardará la clave foránea que apunta a la entidad inversa de la relación, osea, Curso
    )
    private List<Curso> cursos = new ArrayList<>();
   /* Esta es una propiedad, del modelo Alumno, que guarda una lista de objetos Curso. Esta lista representa todos los cursos en los que un alumno está inscrito,
    estableciendo así la relación muchos a muchos entre Alumno y Curso. En otras palabras, es la propiedad del modelo que define y gestiona la relación muchos a muchos
    desde el lado de la entidad Alumno. Cuando trabaje con esta propiedad, estoy manipulando la relación entre alumnos y cursos. Por ejemplo, al añadir un curso a esta lista,
    estoy inscribiendo al alumno en ese curso, y al eliminar un curso de la lista, lo estoy desinscribiendo. */
}
