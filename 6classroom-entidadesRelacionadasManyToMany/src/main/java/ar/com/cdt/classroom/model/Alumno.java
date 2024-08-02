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
    @JoinColumn(name="profesor_id")
    private Profesor profesor;
}
