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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idProfesor")
/*
Similarmente, esto le dice a Jackson que use el valor de idProfesor como identificador único cuando
serialice un Profesor. Esto es útil cuando Profesor y Alumno tienen relaciones entre sí (por ejemplo, OneToOne),
ya que ayuda a evitar la serialización infinita de referencias cíclicas entre ellos.
*/
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
    private int antiguedadProfesor;
    
    @OneToOne(mappedBy = "profesor", optional = true)
    private Alumno alumno;
    
    /*Un profesor, puede dar varios cursos (pero cada curso solo puede ser dado por un profesor) 
    (mappedBy = "profesor"): Indica que la relación está mapeada por la propiedad profesor en la clase Curso.
    cascade = CascadeType.ALL: Propaga todas las operaciones (persistencia, eliminación, etc.) del profesor a sus cursos.
    Significa que cualquier operación que se realice en la entidad Profesor (como guardar, modificar, eliminar, etc.) se
    propaga automáticamente a las entidades Curso que están relacionadas.
    guardar: Si guardao un Profesor, todos los Curso relacionados se guardarán automáticamente.
    eliminar: Si elimino un Profesor, todos los Curso relacionados se eliminarán también.
    modificar: Si actualizo un Profesor, todos los Curso relacionados se actualizarán.
    orphanRemoval = true: si modifico por ejemplo el registro de un profesor, y elimino un curso que da, ese curso,
    en lugar de quedar sin profesor, es eliminado de la tabla de curso. Si no estuviera esto, el curso, en ese caso, 
    quedaría sin profesor asignado. Por lo tanto, puede ser útil o no depende el caso...
    @OneToMany: Especifica una relación donde una entidad puede tener muchas entidades asociadas en otra tabla. 
    Se usa típicamente en el lado "uno".*/
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Curso> cursos;
    /*-JsonManagedReference: si bien por el nombre, no entendía, y pensaba que estaban estas anotaciones invertidas..
    (esta dice ..managed.. y la otra .. back.. pero la que guarda la columna con la llave foránea, es la que tiene, 
    en este caso, la notación de back) la realidad es otra.
    @JsonManagedReference: Marca el lado de la relación que será serializado normalmente.  
    @JsonBackReference: Marca el lado de la relación que no se serializa.
    Serializar significa convertir un objeto en una secuencia de bytes o en un formato que pueda ser fácilmente almacenado o transmitido
    y luego reconstruido. En el contexto de JSON, significa convertir un objeto Java en una cadena de texto en formato JSON.
    Deserializar es el proceso inverso, es decir, convertir una secuencia de bytes o una cadena de texto en un objeto Java.
    Cuando tienes una relación bidireccional entre dos objetos, como en tu caso entre Profesor y Curso, necesitas controlar qué información
    incluir en la conversión a JSON para evitar ciclos infinitos (donde Profesor incluye Curso, que a su vez incluye Profesor, y así sucesivamente).
    Uso de @JsonManagedReference y @JsonBackReference
    @JsonManagedReference
    Explicación sencilla: Marca el lado de la relación que se va a incluir en la conversión a JSON.
    Ejemplo: Si conviertes un objeto Profesor a JSON, quieres que los cursos que imparte el profesor se incluyan en el JSON.
    @JsonBackReference
    Explicación sencilla: Marca el lado de la relación que no se va a incluir en la conversión a JSON.
    Ejemplo: Si conviertes un objeto Curso a JSON, no quieres que se incluya el profesor al que está asociado 
    (porque esto podría causar un ciclo si también incluye sus cursos).
    
    Lo importante es que, puedo invertir estas anotaciones, y al hacerlo, determino si, los cursos muestran los datos de los profesores,
    o si los profesores muestran los datos de los cursos que dan... 
    */
    
}