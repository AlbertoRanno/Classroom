package ar.com.cdt.classroom.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table (name="Profesor")
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
    
    @Column (name="antiguedad_profesor") // length= 45 es para definir la longitud del String, en Integer no hace falta
    private int antiguedadProfesor;
}