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
    @JoinColumn(name="profesor_id") // Especifica la columna en la tabla Alumno que almacenará la clave foránea
    //@JsonManagedReference
    private Profesor profesor; // notar que NO es un int profesorId... sino una referencia al objeto Profesor, no solo un ID
}
