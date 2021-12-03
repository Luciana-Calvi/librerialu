package com.act1.libreriaLu.servicios;

import com.act1.libreriaLu.Entidades.Cliente;
import com.act1.libreriaLu.errores.errorServicio;
import com.act1.libreriaLu.repositorio.repositorioCliente;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class servicioCliente implements UserDetailsService {

    int contador;

    @Autowired
    private repositorioCliente repositoriocliente;

    @Transactional
    public void registrarCliente(MultipartFile archivo, Long documento, String nombre, String apellido, String domicilio, String telefono, String mail, String clave, String clave2) throws errorServicio {

        validar(documento, nombre, apellido, domicilio, telefono, mail, clave, clave2);

        Cliente cliente = new Cliente();
        validarDupli(documento);
        cliente.setDocumento(documento);
        nombre = nombre.toUpperCase();
        cliente.setNombre(nombre);
        apellido = apellido.toUpperCase();
        cliente.setApellido(apellido);
        domicilio = domicilio.toUpperCase();
        cliente.setDomicilio(domicilio);
        cliente.setTelefono(telefono);
        cliente.setMail(mail);
        cliente.setClave(clave);
        
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        cliente.setClave(encriptada);
        
        cliente.setAlta(new Date());

        repositoriocliente.save(cliente);
    }

    @Transactional
    public void modificarCliente(MultipartFile archivo, String id, Long documento, String nombre, String apellido, String domicilio, String telefono, String mail, String clave, String clave2) throws errorServicio {

        validar(documento, nombre, apellido, domicilio, telefono, mail, clave, clave2);

        Optional<Cliente> respuesta = repositoriocliente.findById(id);
        if (respuesta.isPresent()) {

            Cliente cliente = respuesta.get();
            cliente.setApellido(apellido);
            cliente.setNombre(nombre);
            cliente.setMail(mail);
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            cliente.setClave(encriptada);

            repositoriocliente.save(cliente);
        } else {

            throw new errorServicio("No se encontró el usuario solicitado");
        }

    }
    
     @Transactional
    public void deshabilitar(String id) throws errorServicio {

        Optional<Cliente> respuesta = repositoriocliente.findById(id);
        if (respuesta.isPresent()) {

           Cliente cliente = respuesta.get();
           cliente.setBaja(new Date());
            repositoriocliente.save(cliente);
        } else {

            throw new errorServicio("No se encontró el usuario solicitado");
        }
    }
    
      @Transactional
    public void habilitar(String id) throws errorServicio {

        Optional<Cliente> respuesta = repositoriocliente.findById(id);
        if (respuesta.isPresent()) {

            Cliente cliente = respuesta.get();
            cliente.setBaja(null);
            repositoriocliente.save(cliente);
        } else {

            throw new errorServicio("No se encontró el usuario solicitado");
        }

    }

    @Transactional
    public void eliminarCliente(String idCliente) throws errorServicio {

        Optional<Cliente> respuesta = repositoriocliente.findById(idCliente);

        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            repositoriocliente.delete(cliente);
        }
    }

    public void validar(Long documento, String nombre, String apellido, String domicilio, String telefono, String mail, String clave, String clave2) throws errorServicio {
        if (documento == null || documento == 0) {
            throw new errorServicio("El documento del cliente no puede ser nulo o vacio");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new errorServicio("El nombre del cliente no puede ser nulo o vacio");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new errorServicio("El apellido del cliente no puede ser nulo o vacio");
        }
        if (domicilio == null || domicilio.isEmpty()) {
            throw new errorServicio("El domicilio del cliente no puede ser nulo o vacio");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new errorServicio("El telefono del cliente no puede ser nulo o vacio");
        }
        if (mail == null || mail.isEmpty()) {
            throw new errorServicio("El mail no puede ser nulo");
        }

        if (clave == null || clave.isEmpty() || clave.length() <= 6) {
            throw new errorServicio("La clave del cliente no puede ser nula y tiene que tener mas de seis digitos");
        }
        if (!clave.equals(clave2)) {
            throw new errorServicio("Las claves deben ser iguales");
        }
    }

    @Transactional(readOnly = true)
    public Cliente getOne(String id) {
        return repositoriocliente.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return repositoriocliente.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarActivos() {
        return repositoriocliente.findAll();
    }

    public void validarDupli(Long documento) throws errorServicio {
        List<Cliente> lista1 = repositoriocliente.buscarTodos();
        for (Cliente unitario : lista1) {
            if (unitario.getDocumento().equals(documento)) {
                throw new errorServicio("El documento ingresado existe en la base de datos, verifique");
            }
        }
    }
    
     @Transactional(readOnly=true)
    public Cliente buscarPorId(String id) throws errorServicio {

        Optional<Cliente> respuesta = repositoriocliente.findById(id);
        if (respuesta.isPresent()) {

            Cliente cliente = respuesta.get();
            return cliente;
        } else {

            throw new errorServicio("No se encontró el usuario solicitado");
        }

    }

   @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Cliente cliente = repositoriocliente.buscarPorMail(mail);
        if (cliente != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
                        
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);
         
            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("clientesession", cliente);

            User user = new User(cliente.getMail(), cliente.getClave(), permisos);
            return user;

        } else {
            return null;
        }

    }
}
