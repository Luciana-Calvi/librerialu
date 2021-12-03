package com.act1.libreriaLu.repositorio;

import com.act1.libreriaLu.Entidades.Autor;
import com.act1.libreriaLu.Entidades.Editorial;
import com.act1.libreriaLu.Entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface repositorioLibro extends JpaRepository<Libro, String> {
    
    
    @Query("SELECT c FROM Libro c WHERE c.id= :id")
	 public Libro buscarLibroporId(@Param("id")String id);
	 
	 @Query("SELECT c FROM Libro c ORDER BY c.titulo ASC")
	 public  List<Libro> buscarTodos();
	 
	 @Query("SELECT c FROM Libro c WHERE c.isbn= :isbn")
	 public Libro buscarporIsbn(@Param("isbn")Long isbn);	 
	 
	 @Query("SELECT c FROM Libro c WHERE c.autor= :autor")
	 public List<Libro> buscarLibroporAutor(@Param("autor")Autor autor);

	 @Query("SELECT c FROM Libro c WHERE c.editorial= :editorial")
	 public List<Libro> buscarLibroporEditorial(@Param("editorial")Editorial editorial);	 
}
    
    
//    @Query("SELECT c FROM Libro c WHERE c.autor.id = :id")
//    public void buscarLibroPorAutor(@Param("id")String id);
//    
//    @Query("SELECT c FROM Libro c WHERE c.editorial.id = :id")
//    public void buscarPorEditorial(@Param("id")String id);
//    
//    @Query("SELECT c FROM Libro c WHERE c.isbn = :isbn")
//    public void bucarPorIsbn(@Param("isbn")Long isbn);
//    
//    @Query("SELECT c FROM Libro c WHERE c.titulo = :titulo")
//    public void buscarPorTitulo(@Param("titulo")String titulo);
//    
//    @Query("SELECT c FROM Libro c WHERE c.anio = :anio")
//    public void buscarPorAnio(@Param("anio")Integer anio);
//    
//    @Query("SELECT c FROM Libro c WHERE c.ejemplares = :ejemplares")
//    public void buscarPorEjemplares(@Param("ejemplares")Integer ejemplares);
//    
//    @Query("SELECT c FROM Libro c WHERE c.ejemplaresPrestados = :ejemplaresPrestados")
//    public void buscarPorEjemplaresPrestados(@Param("ejemplaresPrestados")Integer ejemplaresPrestados);
//    
//    @Query("SELECT c FROM Libro c WHERE c.ejemplaresRestantes= :ejemplaresRestantes")
//    public void buscarPorEjemplaresRestantes(@Param("ejemplaresRestantes")Integer ejemplaresRestantes);
    
//    @Query("SELECT c FROM Libro c WHERE c.alta = :alta")
//    public void alta(@Param("alta")Boolean alta);
//    
//    @Query("SELECT c FROM Libro c WHERE c.alta = :false")
//    public void baja(@Param("alta")Boolean alta);
    
//    @Query("SELECT c FROM Libro c WHERE c.alta = :true")
//    public List<Libro> buscarActivos();

