package com.springboot.backend.focusclubapp.focusclubbackend.models.dao;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.CompraExtendidaDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Compra;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompraDao extends CrudRepository<Compra, Long> {
    List<Compra> findByCliente_IdCliente(Long clienteId);
    List<Compra> findByEvento_IdEvento(Long eventoId);

    // Método para obtener las compras con los nombres de cliente y evento
    @Query("SELECT new com.springboot.backend.focusclubapp.focusclubbackend.models.dto.CompraExtendidaDTO(c.id, cl.idCliente, cl.nombre, e.idEvento, e.nombre, c.fechaCompra, c.precioEntrada, c.cantidadEntradas, c.precioTotal) " +
           "FROM Compra c " +
           "JOIN c.cliente cl " +
           "JOIN c.evento e")
    List<CompraExtendidaDTO> findAllComprasWithClientAndEventNames();
}
