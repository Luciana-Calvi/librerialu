package com.act1.libreriaLu.servicios;

import com.act1.libreriaLu.Entidades.Autor;
import com.act1.libreriaLu.Entidades.Editorial;
import com.act1.libreriaLu.Entidades.Libro;
import com.act1.libreriaLu.errores.errorServicio;
import com.act1.libreriaLu.repositorio.repositorioAutor;
import com.act1.libreriaLu.repositorio.repositorioEditorial;
import com.act1.libreriaLu.repositorio.repositorioLibro;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class servicioLibro {

    @Autowired
    private repositorioLibro repositoriolibro;
//    @Autowired
//    private repositorioAutor repositorioautor;
//    @Autowired
//    private repositorioEditorial repositorioeditorial;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes) throws errorServicio {

//        Editorial editorial = repositorioeditorial.getOne(ideditorial);
//	Autor autor = repositorioautor.getOne(idautor);
  		
        
        validarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
//        libro.setAutor(autor);
//        libro.setEditorial(editorial);
             
        repositoriolibro.save(libro);
    }

    @Transactional
    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes) throws errorServicio {

        validarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);

        Optional<Libro> respuesta = repositoriolibro.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);
//            libro.setAutor(autor);
//            libro.setEditorial(editorial);
            
            repositoriolibro.save(libro);
        } else {
            throw new errorServicio("NO SE ENCONTRO EL LIBRO SOLICITADO");
        }
    }

    @Transactional
    public void darDeBajaLibro(String id) throws errorServicio {
        Optional<Libro> respuesta = repositoriolibro.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);

            repositoriolibro.save(libro);
        } else {
            throw new errorServicio("NO SE PUDO DAR DE BAJA EL LIBRO SOLICITADO");
        }
    }

    @Transactional
    public void darDeAltaLibro(String id) throws errorServicio {
        Optional<Libro> respuesta = repositoriolibro.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(true);

            repositoriolibro.save(libro);
        } else {
            throw new errorServicio("NO SE PUDO DAR DE ALTA EL LIBRO SOLICITADO");
        }
    }

    @Transactional(readOnly = true)
    public Libro getOne(String id) {
        return repositoriolibro.getById(id);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarTodos() {
        return repositoriolibro.findAll();
    }

    @Transactional(readOnly = true)
    public List<Libro> listarActivos() {
        return repositoriolibro.findAll();
    }

    private void validarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes) throws errorServicio {
        if (isbn == null) {
            throw new errorServicio("DEBE COLOCAR EL ISBN DEL LIBRO POR FAVOR");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new errorServicio("DEBE COLOCAR EL TITULO DEL LIBRO POR FAVOR");
        }
        if (anio == null) {
            throw new errorServicio("DEBE COLOCAR EL AÑO EN QUE SE EDITO EL LIBRO POR FAVOR");
        }
        if (ejemplares == null) {
            throw new errorServicio("DEBE COLOCAR LA CANTIDAD DE EJEMPLARES POR FAVOR");
        }
        if (ejemplaresPrestados == null) {
            throw new errorServicio("DEBE COLOCAR LA CANTIDAD DE EJEMPLARES PRESTADOS POR FAVOR");
        }
        if (ejemplaresRestantes == null) {
            throw new errorServicio("DEBE COLOCAR LA CANTIDAD DE EJEMPLARES RESTANTES POR FAVOR");
        }
    }

}
