package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IRolDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Rol;

import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private IRolDao rolRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> findById(Long id) {
        return rolRepository.findById(id);
    }

}
