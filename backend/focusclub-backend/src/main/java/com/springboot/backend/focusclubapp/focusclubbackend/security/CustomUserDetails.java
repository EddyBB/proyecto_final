package com.springboot.backend.focusclubapp.focusclubbackend.security;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Cliente cliente;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Cliente cliente, Collection<? extends GrantedAuthority> authorities) {
        this.cliente = cliente;
        this.authorities = authorities;
    }

    public static CustomUserDetails create(Cliente cliente) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + cliente.getRol().getTipoRol())
        );
        return new CustomUserDetails(cliente, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return cliente.getPassword();
    }

    @Override
    public String getUsername() {
        return cliente.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
