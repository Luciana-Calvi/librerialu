package com.act1.libreriaLu.servicios;

import com.act1.libreriaLu.Entidades.Editorial;
import com.act1.libreriaLu.Entidades.Libro;
import com.act1.libreriaLu.errores.errorServicio;
import com.act1.libreriaLu.repositorio.repositorioEditorial;
import com.act1.libreriaLu.repositorio.repositorioLibro;
import static java.lang.Boolean.FALSE;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class servicioEditorial {

    @Autowired
    private repositorioLibro repositoriolibro;

    @Autowired
    private repositorioEditorial repositorioeditorial;

    @Transactional
    public void agragarEditorial(String idLibro, String nombre) throws errorServicio {

        Libro libro = repositoriolibro.findById(idLibro).get();

        validarEditorial(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(FALSE);

        repositorioeditorial.save(editorial);
    }

    @Transactional
    public void modificarEditorial(String id, String nombre) throws errorServicio {

        validarEditorial(nombre);

        Optional<Editorial> respuesta = repositorioeditorial.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            repositorioeditorial.save(editorial);
        } else {
            throw new errorServicio("LA EDITORIAL QUE USTED BUSCA NO EXISTE, VUELVA A INTENTARLO");
        }
    }
    
      @Transactional
    public void darDeBajaEditorial(String id)throws errorServicio{
        Optional<Editorial> respuesta = repositorioeditorial.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            
            repositorioeditorial.save(editorial);
    }else{
            throw new errorServicio("NO SE PUDO DAR DE BAJA EL LIBRO SOLICITADO");
        }
  }
    @Transactional
      public void darDeAltaEditorial(String id)throws errorServicio{
        Optional<Editorial> respuesta = repositorioeditorial.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(true);
            
            repositorioeditorial.save(editorial);
    }else{
            throw new errorServicio("NO SE PUDO DAR DE ALTA EL LIBRO SOLICITADO");
        }
  }

    @Transactional
    public void eliminarEditorial(String idLibro, String idEditorial) throws errorServicio {

        Optional<Editorial> respuesta = repositorioeditorial.findById(idEditorial);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            repositorioeditorial.save(editorial);
            editorial.setAlta(FALSE);
            repositorioeditorial.save(editorial);
        } else {
            throw new errorServicio("LA EDITORIAL QUE USTED BUSCA NO EXISTE, VUELVA A INTENTARLO");
        }
    }
    
     @Transactional(readOnly = true)
      public Editorial getOne(String id){
          return repositorioeditorial.getOne(id);
      }
      
      @Transactional(readOnly = true)
       public List<Editorial> listarTodos(){
           return repositorioeditorial.findAll();
       }
       @Transactional(readOnly = true)
       public List<Editorial> listarActivos(){
           return repositorioeditorial.findAll();
       }

    @Transactional
    public void validarEditorial(String nombre) throws errorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new errorServicio("COMPLETE NUEVAMENTE EL NOMBRE DE LA EDITORIAL POR FAVOR");
        }
    }
}
