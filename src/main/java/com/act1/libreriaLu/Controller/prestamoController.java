package com.act1.libreriaLu.Controller;

import com.act1.libreriaLu.Entidades.Cliente;
import com.act1.libreriaLu.Entidades.Libro;
import com.act1.libreriaLu.Entidades.Prestamo;
import com.act1.libreriaLu.errores.errorServicio;
import com.act1.libreriaLu.repositorio.repositorioPrestamo;
import com.act1.libreriaLu.servicios.servicioCliente;
import com.act1.libreriaLu.servicios.servicioLibro;
import com.act1.libreriaLu.servicios.servicioPrestamo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@RequestMapping("/prestamo")
public class prestamoController {

    @Autowired
    private servicioPrestamo servicioprestamo;
    @Autowired
    private repositorioPrestamo repositorioprestamo;
    @Autowired
    private servicioCliente serviciocliente;
    @Autowired
    private servicioLibro serviciolibro;

    @GetMapping("/cargarPrestamo")
    public String formulario(ModelMap modelo) {
        List<Cliente> clientelista = serviciocliente.listarTodos();

        modelo.addAttribute("clientes", clientelista);

        List<Libro> librolista = serviciolibro.listarTodos();

        modelo.addAttribute("libros", librolista);

        return "prestamo.html";
    }

    @PostMapping("/cargarPrestamo/{id}")
    public String cargarPrestamo(ModelMap modelo, @RequestParam String idCliente, @RequestParam String idLibro, @RequestParam String fecha, @RequestParam String devolucion) throws ParseException {

        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(devolucion);
            servicioprestamo.archivarPrestamo(idCliente, idLibro, date1, date2);
            modelo.put("exito", "Modificacion exitosa");
            return "prestamo.html";

        } catch (errorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "prestamo.html";
        }
    }

    @GetMapping("/finalizarPrestamo")
    public String finalizar(@PathVariable String idPrestamo, ModelMap modelo) {
        modelo.put("prestamo", servicioprestamo.getOne(idPrestamo));
        return "prestamoMod.html";
    }

    @PostMapping("/finalizarPrestamo")
    public String finalizarPrestamo(ModelMap modelo, @RequestParam String idPrestamo, @RequestParam String fechaDevoReal) throws ParseException {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaDevoReal);
            servicioprestamo.devolucionLibroPrestamo(idPrestamo, date1);
            modelo.put("titulo", "PRESTAMO");
            modelo.put("descripcion", "Cerrado de Forma Correcta!");

            if (repositorioprestamo.getById(idPrestamo).getMulta() > 0) {
                modelo.put("multa", "RECORDA COBRAR LA MULTA DE " + repositorioprestamo.getById(idPrestamo).getMulta());
            }

            return "prestamoMod.html";
        } catch (errorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "prestamoMod.html";
        }
    }

    @GetMapping("/listaPrestamos")
    public String listaPrestamos(ModelMap modelo) {

        List<Prestamo> prestamos = servicioprestamo.listarTodos();
        for (Prestamo prestamo : prestamos) {
            System.out.println(prestamo.getCliente());
            System.out.println(prestamo.getLibro());
        }
        modelo.addAttribute("prestamos", prestamos);
//        List<Cliente> clientelista = serviciocliente.listarTodos();
//        modelo.put("clientes", clientelista);
//        List<Libro> librolista = serviciolibro.listarTodos();
//        modelo.put("libros", librolista);
//        for (Prestamo prestamo : prestamolista) {
//            System.out.println(prestamo.getId());
//            System.out.println(prestamo.getCliente());
//            System.out.println(prestamo.getLibros());
//            System.out.println(prestamo.getDevolucion());
//            System.out.println(prestamo.getFecha());
//            System.out.println(prestamo.getEstadoprestamo());
//        }

        
        return "listaPrestamos.html";
    }

}
