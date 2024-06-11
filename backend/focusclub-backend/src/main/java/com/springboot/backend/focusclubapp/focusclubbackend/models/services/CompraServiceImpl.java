package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.ICompraDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompraServiceImpl implements CompraService {

    private final ICompraDao compraDao;

    @Autowired
    public CompraServiceImpl(ICompraDao compraDao) {
        this.compraDao = compraDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> findByClienteId(Long clienteId) {
        return compraDao.findByCliente_IdCliente(clienteId);
    }

    @Override
    @Transactional
    public Compra save(Compra compra) {
        return compraDao.save(compra);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> findAll() {
        return (List<Compra>) compraDao.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        compraDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Compra> findById(Long id) {
        return compraDao.findById(id);
    }
}
