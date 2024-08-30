package ar.com.cdt.classroom.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
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

@Entity //Indica que esta clase es una entidad JPA, lo que significa que se mapeará a una tabla en la base de datos.
@Data // Proporciona automáticamente getters, setters, toString(), equals(), y hashCode(), gracias a Lombok.
@Table(name = "Alumno") // Especifica el nombre de la tabla en la base de datos a la que se mapeará esta entidad.
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idAlumno")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Id y @GeneratedValue Indican que idAlumno es la clave primaria de la tabla y que se generará automáticamente.
    @Column(name = "id_alumno") // Define el mapeo de los atributos de la clase a las columnas de la tabla de la base de datos.
    private int idAlumno;

    @Column(name = "nombre_alumno", length = 45)
    private String nombreAlumno;

    @Column(name = "apellido_alumno", length = 45)
    private String apellidoAlumno;

    @Column(name = "telefono_alumno", length = 45)
    private String telefonoAlumno;

    @Column(name = "email_alumno", length = 45)
    private String emailAlumno;
    
    /*
    Voy a establecer una relación OneToOne entre las entidades de Alumno y Profesor. Cual si fuera el caso
    de que un profesor solo da clases particulares a un alumno, y ese alumno solo toma clases de ese profe.
    A esta relación, la voy a establecer como Optional, esto va a permitir, que un alumno no tenga 
    necesariamente un profesor asignado, pudiendo el campo 'profesor_id' ser null.
    Este tipo de relaciones 1 a 1, tiene anotaciones que parecen no simétricas a simple vista, pero si lo son,
    me refiero a que debo de elegir, cual de las 2 entidades va a guardar la clave foránea que referencia a la otra.
    Mendiante @JoinColumn(name = "profesor_id") la entidad Alumno guarda la referencia al Profesor
    Y por contracara, en el modelo de Profesor, se usa  @OneToOne(mappedBy = "profesor") para indicar que se están
    guardando las claves foráneas en la otra tabla. De esta forma, no se manejan datos duplicados, lo cual podría
    llevar a muchos errores.
    */

    @OneToOne(optional = true, cascade = CascadeType.DETACH, orphanRemoval = false) // **
    //Correctamente ubicada acá, porque como todas estas indicaciones van solamente del lado del dueño de la relacion (la tabla que guarda el id de la relacion => ALUMNO)
    @JoinColumn(name = "profesor_id") // Especifica la columna en la tabla Alumno que almacenará la clave foránea
    @JsonManagedReference("alumno-profesor")
    private Profesor profesor; // notar que NO es un int profesorId... sino una referencia al objeto Profesor, no solo un ID
    /* **Nota: Al usar optional=true, permito que un alumno se registre sin necesidad de un profesor asignado. Sin embargo, esto no es suficiente para eliminar
    un profesor relacionado sin antes desvincularlo del alumno. La columna profesor_id en la tabla Alumno se protege, por lo que primero se debe eliminar 
    la relación manualmente o utilizar configuraciones de cascada en Spring para manejarlo automáticamente. Con cascade = CascadeType.DETACH, al elimnar un profesor,
    lo desasocio de forma automática del alumno al que estaba asignado. Con eso sería suficiente, dado que por default,  orphanRemoval = false, por lo tanto no hace
    falta aclararlo, y ahí determino explícitamente que No se elimine al alumno que queda sin vinculo a un profesor. 
    OJO => ESTO SOLO, NO ALCANZÓ, ESTIMO PORQUE LOS 3 MODELOS ESTAN RELACIONADOS ENTRE SI, Y ESO LO HACE COMPLEJO. POR LO TANTO, TUVE QUE IMPLEMENTAR UN CAMBIO
    EN EL METODO ELIMINAR, EN LA IMPL DEL SERV, EN PROFESOR, DE MODO QUE: PRIMERO DESASOCIARA AL ALUMNO, Y LUEGO, DESASOCIARA LOS CURSOS A LOS QUE ESTABA ASIGANADO.
    PROBABLEMENTE, SI LA ENTIDAD DE CURSOS CON SUS RELACIONES, NO HUBIESE EXISTIDO, TODA ESA PARTE NO HUBIESE HECHO FALTA. 
    IGUALMENTE, HACER ESO, NO ES CONSIDERADO UNA MALA PRACTICA, PERO QUIZAS SI, MENOS ELEGANTE A MANEJARLO SIN ELLO. */
    
    
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
    /************* ERROR ENCONTRADO Y ENTENDIDO:
    En relaciones ManyToMany, no se usan @JsonManagedReference y @JsonBackReference porque pueden causar problemas en la serialización
    y deserialización con Jackson. Spring maneja estas relaciones automáticamente sin necesidad de estas anotaciones.
    Para evitar la recursividad infinita (donde un alumno muestra cursos, los cursos muestran alumnos, y así sucesivamente),
    se utiliza @JsonIgnore en UNO de los dos modelos.
    */    
    
    //@JsonManagedReference("alumno-curso")
    //@JsonIgnore // Evita el ciclo infinito, pero directamente no muestra los cursos a los que está asociado el alumno
    private List<Curso> cursos = new ArrayList<>();
   /* Esta es una propiedad, del modelo Alumno, que guarda una lista de objetos Curso. Esta lista representa todos los cursos en los que un alumno está inscrito,
    estableciendo así la relación muchos a muchos entre Alumno y Curso. En otras palabras, es la propiedad del modelo que define y gestiona la relación muchos a muchos
    desde el lado de la entidad Alumno. Cuando trabaje con esta propiedad, estoy manipulando la relación entre alumnos y cursos. Por ejemplo, al añadir un curso a esta lista,
    estoy inscribiendo al alumno en ese curso, y al eliminar un curso de la lista, lo estoy desinscribiendo. */
}

    /*El problema que experimentaba se debía a la forma en que Jackson, la biblioteca de serialización/deserialización de JSON utilizada por Spring Boot, 
    manejaba la relación bidireccional entre Alumno y Profesor. Esto causaba ciclos de referencia infinita o datos no representados correctamente en el JSON, 
    dependiendo de las anotaciones utilizadas.

    Análisis de las Anotaciones
    JsonManagedReference y JsonBackReference:

    Pros:
    Son fáciles de usar y se entienden fácilmente para romper ciclos de referencia.
    Contras:
    No permiten una visualización completa de la relación en ambas direcciones en el JSON resultante, ya que uno de los lados de la relación queda excluido del JSON.
    
    JsonIdentityInfo:
    @JsonIdentityInfo con ObjectIdGenerators.PropertyGenerator.class genera un identificador único para cada objeto en base a una propiedad específica (como el ID). 
    Cuando Jackson encuentra el mismo objeto más de una vez, usa el identificador en lugar de duplicar la información del objeto.
    Esto permite representar la relación completa en ambas direcciones sin crear un ciclo de referencia infinito.

    Pros:
    Permite que ambas entidades se refieran entre sí completamente en la serialización JSON, mostrando todos los detalles relevantes.
    Evita los problemas de referencia circular.
    Contras:
    Puede ser un poco más complejo de entender para quienes no estamos familiarizados con el manejo de identificadores de objeto en JSON.
    Añade cierta complejidad al JSON, especialmente si el modelo de datos es más grande.

    @JsonIdentityInfo es una anotación de Jackson que se utiliza para gestionar objetos que pueden aparecer múltiples veces en un gráfico de objetos, 
    evitando referencias cíclicas o duplicaciones de datos en la serialización JSON. Al usar ObjectIdGenerators.PropertyGenerator.class, le indicamos a
    Jackson que utilice una propiedad específica (en este caso, el ID de la entidad) para identificar de manera única a cada objeto. De esta manera, 
    Jackson puede referirse a los objetos mediante este ID en lugar de incluir repetidamente los mismos datos del objeto.

    Esto es útil en relaciones bidireccionales como las de Alumno y Profesor, ya que permite que ambas entidades sean completamente visibles 
    en la serialización JSON sin caer en un bucle infinito o tener datos redundantes.

    Nota agregada después: El problema que ocurrió después fue que, al sumar la entidad de cursos, y al usar en todas las entidades esta anotación,
    al mostrar un alumno, con sus cursos y profesores, el primer alumno mostraba todo, pero el segundo objeto alumno, no se mostraba, sino solo un ID,
    esto porque ya se había mostrado antes. Por lo cual esta forma está buena, pero en casos donde no hay tantos vínculos, y todos mostrando todo desde cualquier lado. */