package com.act1.libreriaLu.repositorio;

import com.act1.libreriaLu.Entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface repositorioEditorial extends JpaRepository<Editorial, String> {
    
    @Query("SELECT c FROM Editorial c WHERE c.nombre = :nombre")
    public void buscarEditorialPorNombre(@Param("nombre")String nombre);
    
    @Query("SELECT c FROM Editorial c WHERE c.alta = :alta")
    public void darDeAltaEditorial(@Param("alta")Boolean alta);
}
