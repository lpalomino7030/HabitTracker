package com.cibertec.habittracker.service;

import com.cibertec.habittracker.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetalleService implements UserDetailsService {

    private final UsuarioService usuarioService;

    public UsuarioDetalleService(UsuarioService usuarioService) {

        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioService.obtenerUsuarioByNomusuario(username);
        System.out.println("LOGIN: " + username);


        if (usuario == null) {
            System.out.println("USUARIO NO ENCONTRADO");
        } else {
            System.out.println("USUARIO OK: " + usuario.getNombreUsuario());
            System.out.println(usuario.getContrasena());
        }

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getNombreUsuario())
                .password(usuario.getContrasena())
                .authorities("USER")
                .build();
    }
}
