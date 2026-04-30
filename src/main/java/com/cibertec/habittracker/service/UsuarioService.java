package com.cibertec.habittracker.service;

import com.cibertec.habittracker.model.DTO.ItemUsuarioDto;
import com.cibertec.habittracker.model.DTO.RegistroUsuarioDto;
import com.cibertec.habittracker.model.Usuario;
import com.cibertec.habittracker.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<ItemUsuarioDto> listaUsuarios(){
        List<ItemUsuarioDto> dtoList = new ArrayList<>();
        for (Usuario usuario: usuarioRepository.findAll()){
            ItemUsuarioDto itemUsuarioDto = new ItemUsuarioDto();

            itemUsuarioDto.setIdusuario(usuario.getId());
            itemUsuarioDto.setNombres(usuario.getNombres());
            itemUsuarioDto.setEmail(usuario.getEmail());

            dtoList.add(itemUsuarioDto);



        }
        return dtoList;
    }

    public RegistroUsuarioDto obtenerUsuarioDtoByIdUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario != null) {
            RegistroUsuarioDto dto = new RegistroUsuarioDto();

            dto.setIdusuario(usuario.getId());
            dto.setNombres(usuario.getNombreUsuario());
            dto.setNombres(usuario.getNombres());

            dto.setEmail(usuario.getEmail());
            return dto;

        }
return null;

    }

    public Usuario obtenerUsuarioByNomusuario(String nomusuario){


        return usuarioRepository.findByNombreUsuario(nomusuario);

    }

    @Transactional
    public void registrarUsuario(RegistroUsuarioDto dto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Usuario usuario;

        if (dto.getIdusuario() != null){
            usuario = usuarioRepository.findById(dto.getIdusuario())
                    .orElse(null);
        } else{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            usuario = new Usuario();
            usuario.setNombreUsuario(dto.getNomusuario());
            usuario.setContrasena(
                    encoder.encode(dto.getPassword())
            );
        }

        usuario.setNombres(dto.getNombres());
        usuario.setEmail(dto.getEmail());

        usuarioRepository.save(usuario);




    }


}
