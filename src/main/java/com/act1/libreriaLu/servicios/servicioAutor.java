package com.act1.libreriaLu.servicios;

import com.act1.libreriaLu.Entidades.Autor;
import com.act1.libreriaLu.Entidades.Libro;
import com.act1.libreriaLu.errores.errorServicio;
import com.act1.libreriaLu.repositorio.repositorioAutor;
import com.act1.libreriaLu.repositorio.repositorioLibro;
import static java.lang.Boolean.FALSE;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class servicioAutor {

    @Autowired
    private repositorioLibro repositoriolibro;

    @Autowired
    private repositorioAutor repositorioautor;

    @Transactional
    public void agragarAutor(String idLibro, String nombre) throws errorServicio {

        Libro libro = repositoriolibro.findById(idLibro).get();

        validarAutor(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(FALSE);

        repositorioautor.save(autor);
    }

    @Transactional
    public void modificarAutor(String id, String nombre) throws errorServicio {

        validarAutor(nombre);

        Optional<Autor> respuesta = repositorioautor.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            repositorioautor.save(autor);
        } else {
            throw new errorServicio("EL AUTOR QUE USTED BUSCA NO EXISTE, VUELVA A INTENTARLO");
        }
    }
    
    @Transactional
    public void darDeBajaAutor(String id)throws errorServicio{
        Optional<Autor> respuesta = repositorioautor.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setAlta(false);
            
            repositorioautor.save(autor);
    }else{
            throw new errorServicio("NO SE PUDO DAR DE BAJA EL LIBRO SOLICITADO");
        }
  }
    @Transactional
      public void darDeAltaAutor(String id)throws errorServicio{
        Optional<Autor> respuesta = repositorioautor.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setAlta(true);
            
            repositorioautor.save(autor);
    }else{
            throw new errorServicio("NO SE PUDO DAR DE ALTA EL LIBRO SOLICITADO");
        }
  }

    @Transactional
    public void eliminarAutor(String idLibro, String idAutor) throws errorServicio {

        Optional<Autor> respuesta = repositorioautor.findById(idAutor);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(FALSE);
            repositorioautor.save(autor);
        } else {
            throw new errorServicio("EL AUTOR QUE USTED BUSCA NO EXISTE, VUELVA A INTENTARLO");
        }
    }
    
      @Transactional(readOnly = true)
      public Autor getOne(String id){
          return repositorioautor.getOne(id);
      }
      
      @Transactional(readOnly = true)
       public List<Autor> listarTodos(){
           return repositorioautor.findAll();
       }
       @Transactional(readOnly = true)
       public List<Autor> listarActivos(){
           return repositorioautor.findAll();
       }

    @Transactional
    public void validarAutor(String nombre) throws errorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new errorServicio("COMPLETE NUEVAMENTE EL NOMBRE DEL AUTOR POR FAVOR");
        }
    }
}
