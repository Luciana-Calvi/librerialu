package com.act1.libreriaLu.repositorio;

import com.act1.libreriaLu.Entidades.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface repositorioCliente extends JpaRepository<Cliente, String> {

    @Query("SELECT c FROM Cliente c WHERE c.id= :id")
    public Cliente buscarPorId(@Param("id") String id);

    @Query("SELECT c FROM Cliente c ORDER BY c.nombre ASC")
    public List<Cliente> buscarTodos();

    @Query("SELECT c FROM Cliente c WHERE c.documento= :documento")
    public Cliente buscarporDocumento(@Param("documento") Long documento);

    @Query("SELECT c FROM Cliente c WHERE c.mail = :mail")
    public Cliente buscarPorMail(@Param("mail") String mail);

}
