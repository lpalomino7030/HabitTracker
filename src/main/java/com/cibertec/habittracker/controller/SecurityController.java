package com.cibertec.habittracker.controller;

import com.cibertec.habittracker.model.DTO.RegistroUsuarioDto;
import com.cibertec.habittracker.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/security")
public class SecurityController {

    private final UsuarioService usuarioService;

    public SecurityController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/user")
    public String index(Model model){
        model.addAttribute("userList",
                usuarioService.listaUsuarios());
        return "security/index";
    }

    @GetMapping("/user/create")
    public String create(Model model){
        model.addAttribute("user",
                new RegistroUsuarioDto());
        return "security/user-create";
    }

    @GetMapping("/user/edit/{id}")
    public String create(Model model,
                         @PathVariable Long id) {
        model.addAttribute("user",
                usuarioService.obtenerUsuarioDtoByIdUsuario(id));
        return "security/user-edit";
    }

    @PostMapping("/user/register")
    public String register(@ModelAttribute
                           RegistroUsuarioDto user){
        usuarioService.registrarUsuario(user);
        return "redirect:/security/user";
    }

}

