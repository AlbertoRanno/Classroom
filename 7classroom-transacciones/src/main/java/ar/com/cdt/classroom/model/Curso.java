package ar.com.cdt.classroom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "Curso")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idCurso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private int idCurso;

    @Column(name = "nombre_curso", length = 45)
    private String nombreCurso;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    /* Varios cursos pueden ser dados por el mismo profesor (Pero cada curso puede ser dado solo por un profesor) 
     Es decir, varios cursos pueden estar asociados con un mismo profesor.
    @ManyToOne: Especifica una relación donde muchas entidades pueden estar asociadas con una única entidad de otra tabla. 
    Se usa típicamente en el lado "muchos".*/
    @ManyToOne
    @JoinColumn(name = "profesor_id") //Es la columna que va a guardar la llave foránea de la relación.
    //@JsonBackReference
    @JsonManagedReference
    private Profesor profesor;

    @ManyToMany(mappedBy = "cursos")
    @JsonBackReference
    private List<Alumno> alumnos = new ArrayList<>();
}
