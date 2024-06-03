package com.springboot.backend.focusclubapp.focusclubbackend.models.mapper;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.ClienteDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.RolDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Rol;

public class Mapper {

    public static ClienteDTO toClienteDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdCliente(cliente.getIdCliente());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellidos(cliente.getApellidos());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setPassword(cliente.getPassword());

        RolDTO rolDTO = toRolDTO(cliente.getRol());
        clienteDTO.setRol(rolDTO);
        return clienteDTO;
    }

    public static RolDTO toRolDTO(Rol rol) {
        if (rol == null) {
            return null;
        }
        RolDTO rolDTO = new RolDTO();
        rolDTO.setIdRol(rol.getIdRol());
        rolDTO.setTipoRol(rol.getTipoRol());
        return rolDTO;
    }

    public static Rol toRol(RolDTO rolDTO) {
        if (rolDTO == null) {
            return null;
        }
        Rol rol = new Rol();
        rol.setIdRol(rolDTO.getIdRol());
        rol.setTipoRol(rolDTO.getTipoRol());
        return rol;
    }
}
