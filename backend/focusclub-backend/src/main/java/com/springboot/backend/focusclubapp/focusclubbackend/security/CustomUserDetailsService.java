package com.springboot.backend.focusclubapp.focusclubbackend.security;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IClienteDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private IClienteDao clienteDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("Loading user by email: {}", email);
        Cliente cliente = clienteDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
        logger.debug("Loaded User: {}, Role: {}", cliente.getEmail(), cliente.getRol().getTipoRol());
        return CustomUserDetails.create(cliente);
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        logger.debug("Loading user by id: {}", id);
        Cliente cliente = clienteDao.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con id: " + id));
        logger.debug("Loaded User: {}, Role: {}", cliente.getEmail(), cliente.getRol().getTipoRol());
        return CustomUserDetails.create(cliente);
    }
}
