package ar.com.cdt.classroom.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Curso")
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
    private Profesor profesor;
}

