package com.act1.libreriaLu.Controller;

import com.act1.libreriaLu.Entidades.Autor;
import com.act1.libreriaLu.servicios.servicioAutor;
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
@RequestMapping("/autor")
public class autorController {
    
     @Autowired
     private servicioAutor servicioautor;
     
     @GetMapping("/registro")
    public String formulario() {

        return "formularioAutor";
    }
    
     @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam(required = false) String idLibro, @RequestParam(required = false) String nombre) {
        try {
            servicioautor.agragarAutor(idLibro, nombre);
            modelo.put("exito", "registro exitoso");
            return "formularioAutor";
        } catch (Exception e) {
            modelo.put("error", "falta dato");
            return "formularioAutor";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("autor", servicioautor.getOne(id));
        return "formularioAutorMod";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) {

        try {
            servicioautor.modificarAutor(id, nombre);
            modelo.put("exito", "Modificacion exitosa");

            return "listaAutor";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "formularioAutorMod";
        }
    }
    
     @GetMapping("/lista")
    public String lista(ModelMap modelo) {

        List<Autor> autorlista = servicioautor.listarTodos();
        
        modelo.addAttribute("autores" ,autorlista);
        return "listaAutor";
    }
    
     @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable String id){
        
            try{
                servicioautor.darDeAltaAutor(id);
                modelo.put("exito", "Su libro esta dado de Alta");
                 return "redirect:/autor/lista";
            }catch(Exception e){
                 modelo.put("error", "No se pudo dar de alta");
            return "formularioLibro";
            }   
    }
    
     @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable String id){
        
            try{
                servicioautor.darDeBajaAutor(id);
                modelo.put("exito", "Su libro esta dado de Alta");
                 return "redirect:/autor/lista";
            }catch(Exception e){
                 modelo.put("error", "No se pudo dar de alta");
            return "formularioLibro";
            }   
    }
}
