package com.cibertec.habittracker.controller;

import com.cibertec.habittracker.model.DTO.DiaEstado;
import com.cibertec.habittracker.model.Habito;
import com.cibertec.habittracker.service.HabitoRegistradoService;
import com.cibertec.habittracker.service.HabitoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

        List<Habito> habitos = habitoService.listar();
        List<Integer> dias = habitoService.diasDelMes();
        Map<Long, List<DiaEstado>> semanas = new HashMap<>();
        for (Habito h : habitos) {
            semanas.put(h.getId(), registroService.obtenerSemana(h.getId().longValue()));
        }


        model.addAttribute("habitos", habitos);
        model.addAttribute("semanas", semanas);
        model.addAttribute("habito", new Habito());
        model.addAttribute("totalHabitos", habitoService.contarHabitos());
        model.addAttribute("diasMes", dias);
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
    public String crear(Habito habito) {
        habitoService.guardar(habito);
        return "redirect:/habitos";
    }

//    @GetMapping("/marcar/{id}")
//    public String marcar(@PathVariable Long id) {
//        System.out.println(id);
//        registroService.marcarHoy(id);
//        return "redirect:/habitos";
//    }

//        @GetMapping("/marcar/{id}")
//        @ResponseBody
//        public String marcar(@PathVariable Long id) {
//            registroService.marcarHoy(id);
//            return "ok";
//        }

    @GetMapping("/marcar/{id}")
    @ResponseBody
    public List<DiaEstado> marcar(@PathVariable Long id) {
        registroService.marcarHoy(id);
        return registroService.obtenerSemana(id);
    }

}

