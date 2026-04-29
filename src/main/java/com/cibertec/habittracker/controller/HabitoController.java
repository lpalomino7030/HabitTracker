package com.cibertec.habittracker.controller;

import com.cibertec.habittracker.model.DTO.DiaEstado;
import com.cibertec.habittracker.model.Habito;
import com.cibertec.habittracker.model.Usuario;
import com.cibertec.habittracker.service.HabitoRegistradoService;
import com.cibertec.habittracker.service.HabitoService;
import com.cibertec.habittracker.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("habitos")
public class HabitoController {

    private final HabitoService habitoService;
    private final HabitoRegistradoService registroService;
private final UsuarioService usuarioService;

    public HabitoController(HabitoService habitoService, HabitoRegistradoService registroService, UsuarioService usuarioService) {
        this.habitoService = habitoService;
        this.registroService = registroService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model,  Authentication auth) {
        String username = auth.getName();

        List<Habito> habitos = habitoService.listarPorUsuario(username);        List<Integer> dias = habitoService.diasDelMes();
        Integer majorRacha = registroService.mejorRacha();
        Integer rachaActual = registroService.obtenerMayorRachaActual();


        Map<Long, List<DiaEstado>> semanas = new HashMap<>();
        for (Habito h : habitos) {
            semanas.put(h.getId(), registroService.obtenerSemana(h.getId().longValue()));
        }

        Map<Long, Double> porcentajes = new HashMap<>();

        for (Habito h : habitos) {
            porcentajes.put(h.getId(), registroService.porcentajeSemanal(h.getId()));
        }

        model.addAttribute("porcentajes", porcentajes);
        model.addAttribute("habitos", habitos);
        model.addAttribute("semanas", semanas);
        model.addAttribute("habito", new Habito());
        model.addAttribute("totalHabitos", habitoService.contarHabitos());
        model.addAttribute("diasMes", dias);
        model.addAttribute("mejorRacha", majorRacha);
        model.addAttribute("rachaActual", rachaActual);

        Set<Integer> diasUnicos = new HashSet<>();

        for (Habito h : habitos) {
            diasUnicos.addAll(
                    registroService.obtenerDiasMarcadosDelMes(h.getId())
            );
        }

        model.addAttribute("diasMarcados", diasUnicos);

        return "habitos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("habito", new Habito());
        return "habitos/crear";
    }

    @PostMapping("/crear")
    public String crear(Habito habito, Authentication auth) {

        String username = auth.getName();

        Usuario usuario = usuarioService.obtenerUsuarioByNomusuario(username);

        habito.setUsuario(usuario);

        habitoService.guardar(habito);

        return "redirect:/habitos";
    }



    @GetMapping("/marcar/{id}")
    @ResponseBody
    public List<DiaEstado> marcar(@PathVariable Long id) {
        registroService.marcarHoy(id);
        return registroService.obtenerSemana(id);
    }

}

