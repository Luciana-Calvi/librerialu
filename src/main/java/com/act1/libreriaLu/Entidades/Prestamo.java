package com.act1.libreriaLu.Entidades;

import com.act1.libreriaLu.Enum.estadosPrestamos;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;


@Entity
public class Prestamo {
	
	@Id
	@GeneratedValue (generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Temporal(TemporalType.DATE)
    private Date fecha;
    
	@Temporal(TemporalType.DATE)
    private Date devolucion;
	
	private int multa;
  	
    @OneToOne
    private Libro libros;
   
    @OneToOne
    private Cliente cliente;
    
    @Enumerated(EnumType.STRING)
    private estadosPrestamos estadoprestamo;
       
       
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Date devolucion) {
        this.devolucion = devolucion;
    }

	public int getMulta() {
		return multa;
	}

	public void setMulta(int multa) {
		this.multa = multa;
	}

	public Libro getLibro() {
		return libros;
	}

	public void setLibros(Libro libros) {
		this.libros = libros;
	}

	public estadosPrestamos getEstadoprestamo() {
		return estadoprestamo;
	}

	public void setEstadoprestamo(estadosPrestamos estadoprestamo) {
		this.estadoprestamo = estadoprestamo;
	}
	
	
	
	
}
