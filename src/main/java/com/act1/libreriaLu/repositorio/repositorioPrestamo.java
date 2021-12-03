package com.act1.libreriaLu.repositorio;

import com.act1.libreriaLu.Entidades.Cliente;
import com.act1.libreriaLu.Entidades.Libro;
import com.act1.libreriaLu.Entidades.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface repositorioPrestamo extends JpaRepository<Prestamo,String> {

	 @Query("SELECT a FROM Prestamo a WHERE a.id= :id")
	 public Prestamo buscarPrestamoporId(@Param("id")String id); 		
	 
	 @Query("SELECT a FROM Prestamo a ORDER BY a.fecha ASC")
	 public List<Prestamo> buscarTodos();	
         
         @Query("SELECT c FROM Cliente c WHERE c.id= :id")    
	  public Cliente buscarPorId(@Param("id")String id); 
          
          @Query("SELECT c FROM Libro c WHERE c.id= :id")
	 public Libro buscarLibroporId(@Param("id")String id);
	
	 		  
}
