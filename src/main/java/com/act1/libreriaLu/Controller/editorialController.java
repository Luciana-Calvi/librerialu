package com.act1.libreriaLu.Controller;

import com.act1.libreriaLu.Entidades.Editorial;
import com.act1.libreriaLu.servicios.servicioEditorial;
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
@RequestMapping("/editorial")
public class editorialController {
    
    @Autowired
     private servicioEditorial servicioeditorial;
     
     @GetMapping("/registro")
    public String formulario() {

        return "formularioEditorial";
    }
    
     @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam(required = false) String idLibro, @RequestParam(required = false) String nombre) {
        try {
            servicioeditorial.agragarEditorial(idLibro, nombre);
            modelo.put("exito", "registro exitoso");
            return "formularioEditorial";
        } catch (Exception e) {
            modelo.put("error", "falta dato");
            return "formularioEditorial";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("editorial", servicioeditorial.getOne(id));
        return "formularioEditorialMod";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) {

        try {
            servicioeditorial.modificarEditorial(id, nombre);
            modelo.put("exito", "Modificacion exitosa");

            return "listaEditorial";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "formularioEditorialMod";
        }
    }
    
     @GetMapping("/lista")
    public String lista(ModelMap modelo) {

        List<Editorial> editoriallista = servicioeditorial.listarTodos();
        
        modelo.addAttribute("editoriales" ,editoriallista);
        return "listaEditorial";
    }
    
    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable String id){
        
            try{
                servicioeditorial.darDeAltaEditorial(id);
                modelo.put("exito", "Su libro esta dado de Alta");
                 return "redirect:/editorial/lista";
            }catch(Exception e){
                 modelo.put("error", "No se pudo dar de alta");
            return "formularioLibro";
            }   
    }
    
     @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable String id){
        
            try{
                servicioeditorial.darDeBajaEditorial(id);
                modelo.put("exito", "Su libro esta dado de Alta");
                 return "redirect:/editorial/lista";
            }catch(Exception e){
                 modelo.put("error", "No se pudo dar de alta");
            return "formularioLibro";
            }   
    }
}
