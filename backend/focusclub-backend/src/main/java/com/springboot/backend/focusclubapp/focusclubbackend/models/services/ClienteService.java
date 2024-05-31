package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IClienteDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Rol;


@Service
public class ClienteService  implements UserDetailsService{

    private Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private IClienteDao clienteDao;

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Cliente cliente = clienteDao.findByNombrCliente(username);

        if(cliente == null){
            logger.error("Error en el login: no existe el usuario '"+username+"' en el sistema!");
            throw new UsernameNotFoundException("Error en el login: no existe el usuario '"+username+"' en el sistema!");
        }

        @SuppressWarnings("unchecked")
        List<GrantedAuthority> authorities = ((Collection<GrantedAuthority>) cliente.getRol())
        .stream()
        .map(role -> new SimpleGrantedAuthority(((Rol) role).getTipoRol()))
        .collect(Collectors.toList());

        return new User(cliente.getNombre(), cliente.getPassword(), true, true, true, true, authorities);
    }


}
