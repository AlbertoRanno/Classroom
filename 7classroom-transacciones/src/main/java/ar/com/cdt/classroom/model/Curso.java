package ar.com.cdt.classroom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    //@JsonBackReference
    @JsonManagedReference("profesor-curso")
    private Profesor profesor;

    @ManyToMany(mappedBy = "cursos")
    //@JsonBackReference("alumno-curso")
    @JsonIgnore // Evita el ciclo infinito
    private List<Alumno> alumnos = new ArrayList<>();
}
