package com.act1.libreriaLu.servicios;

import com.act1.libreriaLu.Entidades.Cliente;
import com.act1.libreriaLu.Entidades.Libro;
import com.act1.libreriaLu.Entidades.Prestamo;
import com.act1.libreriaLu.Enum.estadosPrestamos;
import com.act1.libreriaLu.errores.errorServicio;
import com.act1.libreriaLu.repositorio.repositorioCliente;
import com.act1.libreriaLu.repositorio.repositorioLibro;
import com.act1.libreriaLu.repositorio.repositorioPrestamo;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class servicioPrestamo {

    @Autowired
    private repositorioPrestamo repositorioprestamo;
    @Autowired
    private repositorioCliente repositoriocliente;
    @Autowired
    private repositorioLibro repositoriolibro;

    @Transactional
    public void archivarPrestamo(String idCliente, String idLibro, Date fecha, Date devolucion) throws errorServicio {

        Cliente cliente = repositoriocliente.getById(idCliente);
        Libro libro = repositoriolibro.getById(idLibro);
        

        Prestamo prestamo = new Prestamo();

        if (libro.getEjemplaresPrestados() == libro.getEjemplares() || libro.getPrestados() > libro.getEjemplares()) {
            throw new errorServicio("No hay ejemplares disponibles, por favor elija otro libro");
        } else if (fecha.compareTo(devolucion) >= 0) {
            throw new errorServicio("La fecha programada de devolucion no puede ser anterior o igual a la fecha de entrega del ejemplar");
        } else {
            prestamo.setCliente(cliente);
            prestamo.setFecha(fecha);
            prestamo.setDevolucion(devolucion);
            prestamo.setMulta(0);
            prestamo.setLibros(libro);
            prestamo.setEstadoprestamo(estadosPrestamos.ABIERTO);
            libro.setPrestados(libro.getPrestados() + 1);
            repositorioprestamo.save(prestamo);
        }
    }

    @Transactional
    public void devolucionLibroPrestamo(String idPrestamo, Date fechaDevoReal) throws errorServicio {

        Prestamo prestamo = repositorioprestamo.getById(idPrestamo);
        String idLibro = prestamo.getLibro().getId();
        Libro libro = repositoriolibro.getById(idLibro);

        if (fechaDevoReal.compareTo(prestamo.getDevolucion()) > 0) {
            prestamo.setMulta(calcularMultaFinal(fechaDevoReal, prestamo.getDevolucion()));
        }

        libro.setPrestados(libro.getPrestados() - 1);
        prestamo.setEstadoprestamo(estadosPrestamos.CERRADO);
        prestamo.setDevolucion(fechaDevoReal);
        repositorioprestamo.save(prestamo);
    }

    public int calcularMultaFinal(Date fechaDevoReal, Date devolucion) {
        int precioxdia = 50;

        int cantidaddias = (int) (fechaDevoReal.getTime() - devolucion.getTime()) / 86400000;
        int multa = cantidaddias * precioxdia;

        return multa;
    }
    
    @Transactional(readOnly = true)
      public Prestamo getOne(String id){
          return repositorioprestamo.getById(id);
      }
      
      @Transactional(readOnly = true)
       public List<Prestamo> listarTodos(){
           return repositorioprestamo.findAll();
       }
       @Transactional(readOnly = true)
       public List<Prestamo> listarActivos(){
           return repositorioprestamo.findAll();
       }

}
