package com.act1.libreriaLu.repositorio;

import com.act1.libreriaLu.Entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface repositorioAutor extends JpaRepository<Autor, String> {
    
    @Query("SELECT c FROM Autor c WHERE c.nombre = :nombre")
    public void buscarAutorPorNombre(@Param("nombre")String nombre);
    
    @Query("SELECT c FROM Autor c WHERE c.alta = :alta")
    public void darDeAltaAutor(@Param("alta")Boolean alta);
    
    @Query("SELECT c FROM Autor c WHERE c.alta = true")
    public List<Autor> buscarActivos();
}
