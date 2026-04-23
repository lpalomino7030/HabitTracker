package com.cibertec.habittracker.controller;

import com.cibertec.habittracker.model.Habito;
import com.cibertec.habittracker.model.HabitoRegistrado;
import com.cibertec.habittracker.service.HabitoRegistradoService;
import com.cibertec.habittracker.service.HabitoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("habitos")
public class HabitoController {

    private final HabitoService habitoService;
    private final HabitoRegistradoService registroService;

    public HabitoController(HabitoService habitoService, HabitoRegistradoService registroService) {
        this.habitoService = habitoService;
        this.registroService = registroService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("habitos", habitoService.listar());
        model.addAttribute("habito", new Habito());
        model.addAttribute("totalHabitos", habitoService.contarHabitos());
        return "habitos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("habito", new Habito());
        return "habitos/crear";
    }

    @PostMapping("/crear")
    public String crear(Habito habito) {
        habitoService.guardar(habito);
        return "redirect:/habitos";
    }

    @GetMapping("/marcar/{id}")
    public String marcar(@PathVariable Long id) {
        System.out.println(id);
        registroService.marcarHoy(id);
        return "redirect:/habitos";
    }

}

