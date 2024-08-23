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
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table (name="Profesor")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idProfesor")
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
    @JsonBackReference("alumno-profesor")
    private Alumno alumno;
    
    
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("profesor-curso")
    //@JsonBackReference("profesor-curso")
    //private List<Curso> cursos; => Al ser null, me daba error cuando quería modificar algún profesor que no tenía curso asignado.
    private List<Curso> cursos = new ArrayList<>();  // Inicializa con una lista vacía
    
}