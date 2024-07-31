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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Alumno")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idAlumno")
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
    @OneToOne(optional = true) // 1 a 1, y No todos los alumnos tienen que relacionarse a un profesor
    @JoinColumn(name = "profesor_id") // Especifica la columna en la tabla Alumno que almacenará la clave foránea
    //@JsonManagedReference
    private Profesor profesor; // notar que NO es un int profesorId... sino una referencia al objeto Profesor, no solo un ID
}

/*El problema que experimentabas se debía a la forma en que Jackson, la biblioteca de serialización/deserialización de JSON utilizada por Spring Boot, 
manejaba la relación bidireccional entre Alumno y Profesor. Esto causaba ciclos de referencia infinita o datos no representados correctamente en el JSON, 
dependiendo de las anotaciones utilizadas.

Análisis de las Anotaciones
JsonManagedReference y JsonBackReference:

Pros:
Son fáciles de usar y se entienden fácilmente para romper ciclos de referencia.
Contras:
No permiten una visualización completa de la relación en ambas direcciones en el JSON resultante, ya que uno de los lados de la relación queda excluido del JSON.
JsonIdentityInfo (Solución Final):

Funcionamiento:
@JsonIdentityInfo con ObjectIdGenerators.PropertyGenerator.class genera un identificador único para cada objeto en base a una propiedad específica (como el ID). 
Cuando Jackson encuentra el mismo objeto más de una vez, usa el identificador en lugar de duplicar la información del objeto.
Esto permite representar la relación completa en ambas direcciones sin crear un ciclo de referencia infinito.
Pros:
Permite que ambas entidades se refieran entre sí completamente en la serialización JSON, mostrando todos los detalles relevantes.
Evita los problemas de referencia circular.
Contras:
Puede ser un poco más complejo de entender para quienes no están familiarizados con el manejo de identificadores de objeto en JSON.
Añade cierta complejidad al JSON, especialmente si el modelo de datos es más grande.
Explicación de @JsonIdentityInfo
@JsonIdentityInfo es una anotación de Jackson que se utiliza para gestionar objetos que pueden aparecer múltiples veces en un gráfico de objetos, 
evitando referencias cíclicas o duplicaciones de datos en la serialización JSON. Al usar ObjectIdGenerators.PropertyGenerator.class, le indicamos a
Jackson que utilice una propiedad específica (en este caso, el ID de la entidad) para identificar de manera única a cada objeto. De esta manera, 
Jackson puede referirse a los objetos mediante este ID en lugar de incluir repetidamente los mismos datos del objeto.

Esto es útil en relaciones bidireccionales como la que tienes entre Alumno y Profesor, ya que permite que ambas entidades sean completamente visibles 
en la serialización JSON sin caer en un bucle infinito o tener datos redundantes.*/
