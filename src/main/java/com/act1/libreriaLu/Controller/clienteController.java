package com.act1.libreriaLu.Controller;

import com.act1.libreriaLu.Entidades.Cliente;
import com.act1.libreriaLu.errores.errorServicio;
import com.act1.libreriaLu.repositorio.repositorioCliente;
import com.act1.libreriaLu.servicios.servicioCliente;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import static sun.security.jgss.GSSUtil.login;

@Controller
@RequestMapping("/cliente")
public class clienteController {

    @Autowired
    private servicioCliente serviciocliente;
    @Autowired
    private repositorioCliente repositoriocliente;

    @GetMapping("/agregarCliente")
    public String formulario(ModelMap modelo) {
        List<Cliente> clientelista = serviciocliente.listarTodos();
        
        modelo.addAttribute("clientes" ,clientelista);

        return "cliente.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE_REGISTRADO')")
    @PostMapping("/agregarCliente")
    public String agregarCliente(ModelMap modelo, HttpSession session, MultipartFile archivo, @RequestParam Long documento, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam String domicilio,
            @RequestParam String telefono,@RequestParam String mail, @RequestParam String clave) {
        
        Cliente cliente = null;
        
        try {
            
            Cliente login = (Cliente)
            
            serviciocliente.registrarCliente(archivo, documento, nombre, apellido, domicilio, telefono, mail, clave, clave);
            modelo.put("titulo", "CLIENTE");
            modelo.put("descripcion", "Creado de Forma Correcta!");
            return "listaClientes.html";
        } catch (errorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "cliente.html";
        }

    }
     @PreAuthorize("hasAnyRole('ROLE_CLIENTE_REGISTRADO')")
     @GetMapping("/modificar/{id}")
    public String modificar(HttpSession session, @RequestParam String id, ModelMap modelo) {
        
        Cliente login =(Cliente) session.getAttribute("clientesession");
        if (login == null || !login.getId().equals(id)) {
            return "/";
        }
        try {
            Cliente cliente = serviciocliente.buscarPorId(id);
            modelo.addAttribute("perfil", cliente);
        }catch(errorServicio e){
            modelo.addAttribute("error", e.getMessage());
        }
       
        return "clienteMod.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificarCliente(ModelMap modelo, @PathVariable String id,
            Long documento, String nombre, String apellido, String domicilio,
            String telefono) {
        try {
            serviciocliente.modificarCliente(id, documento, nombre, apellido, domicilio, telefono);
            modelo.put("exito", "Modificacion exitosa");
            return "listaClientes.html";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "clienteMod.html";
        }

    }

    @PostMapping("/eliminarCliente")
    public String eliminarAutor(ModelMap modelo, @RequestParam String idCliente) {
        try {
            serviciocliente.eliminarCliente(idCliente);
            modelo.put("titulo", "CLIENTE");
            modelo.put("descripcion", "Eliminado de Forma Correcta!");
            return "aviso.html";
        } catch (errorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "cliente.html";
        }
    }

    @GetMapping(value = "/listaClientes")
    public String listaClientes(ModelMap modelo) {
        List<Cliente> clientelista = serviciocliente.listarTodos();
        
        modelo.addAttribute("clientes" ,clientelista);
        return "listaClientes.html";
    }

}
