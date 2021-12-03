package com.act1.libreriaLu.Controller;

import com.act1.libreriaLu.Entidades.Autor;
import com.act1.libreriaLu.Entidades.Editorial;
import com.act1.libreriaLu.Entidades.Libro;
import com.act1.libreriaLu.servicios.servicioAutor;
import com.act1.libreriaLu.servicios.servicioEditorial;
import com.act1.libreriaLu.servicios.servicioLibro;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class libroController {
   

    @Autowired
    private servicioLibro serviciolibro;
//     @Autowired
//     private servicioAutor servicioautor;
//      @Autowired
//     private servicioEditorial servicioeditorial;
   

    @GetMapping("/registro")
    public String formulario() {

        return "formularioLibro";
    }

    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam(required = false) Long isbn, @RequestParam(required = false) String titulo, @RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) Integer ejemplaresPrestados, @RequestParam(required = false) Integer ejemplaresRestantes) {
        try {
            serviciolibro.crearLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);
            modelo.put("exito", "registro exitoso");
            return "formularioLibro";
        } catch (Exception e) {
            modelo.put("error", "falta dato");
            return "formularioLibro";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("libro", serviciolibro.getOne(id));
//        List<Autor> autorlista = servicioautor.listarTodos();
//        modelo.addAttribute("autores" ,autorlista);
        
//        List<Editorial> editoriallista = servicioeditorial.listarTodos();

//        modelo.addAttribute("editoriales" ,editoriallista);
        return "formularioLibroMod";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String titulo, @RequestParam Long isbn, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Integer ejemplaresRestantes) {

        try {
            serviciolibro.modificarLibro(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);
            modelo.put("exito", "Modificacion exitosa");

            return "listaLibros";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "formularioLibroMod";
        }
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {

        List<Libro> librolista = serviciolibro.listarTodos();
        
        modelo.addAttribute("libros" ,librolista);
        return "listaLibros";
    }
    
    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable String id){
        
            try{
                serviciolibro.darDeAltaLibro(id);
                modelo.put("exito", "Su libro esta dado de Alta");
                 return "redirect:/libro/lista";
            }catch(Exception e){
                 modelo.put("error", "No se pudo dar de alta");
            return "formularioLibro";
            }   
    }
    
     @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable String id){
        
            try{
                serviciolibro.darDeBajaLibro(id);
                modelo.put("exito", "Su libro esta dado de Alta");
                 return "redirect:/libro/lista";
            }catch(Exception e){
                 modelo.put("error", "No se pudo dar de alta");
            return "formularioLibro";
            }   
    }
    
//      @GetMapping("/lista")
//    public String alta(ModelMap modelo, @PathVariable String id) {
//
//        List<Libro> librolista = serviciolibro.
//   
//        modelo.addAttribute("libros" ,librolista);
//        return "listaLibros";
//    }
   
}
