package com.cibertec.habittracker.controller;

import com.cibertec.habittracker.model.DTO.DiaEstado;
import com.cibertec.habittracker.model.DTO.HabitoUpdateResponse;
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
import java.time.LocalDate;
import java.time.YearMonth;

import java.time.format.TextStyle;
import java.util.Locale;

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

        List<Habito> habitos = habitoService.listarPorUsuario(username);
        List<Integer> dias = habitoService.diasDelMes();
        Integer majorRacha = registroService.mejorRacha(username);
        Integer rachaActual = registroService.obtenerMayorRachaActual(username);


        Map<Long, List<DiaEstado>> semanas = new HashMap<>();
        for (Habito h : habitos) {
            semanas.put(h.getId(), registroService.obtenerSemana(h.getId().longValue()));
        }

        Map<Long, Double> porcentajes = new HashMap<>();

        for (Habito h : habitos) {
            porcentajes.put(h.getId(), registroService.porcentajeSemanal(h.getId()));
        }
        model.addAttribute("usuario",
                usuarioService.obtenerUsuarioByNomusuario(auth.getName()));        model.addAttribute("porcentajes", porcentajes);
        model.addAttribute("habitos", habitos);
        model.addAttribute("semanas", semanas);
        model.addAttribute("habito", new Habito());
        model.addAttribute("totalHabitos", habitoService.contarHabitos(username));
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
    public String mostrarFormulario(Model model, Authentication auth) {

        String username = auth.getName();
        Usuario usuario = usuarioService.obtenerUsuarioByNomusuario(username);

        model.addAttribute("usuario", usuario);
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



    @PostMapping("/marcar/{id}")
    @ResponseBody
    public HabitoUpdateResponse marcar(@PathVariable Long id, Authentication auth) {

        String username = auth.getName();

        registroService.marcarHoy(id, username);

        HabitoUpdateResponse response = new HabitoUpdateResponse();

        response.setSemana(registroService.obtenerSemana(id));
        response.setDiasMarcados(registroService.obtenerDiasMarcadosDelMes(id));
        response.setPorcentaje(registroService.porcentajeSemanal(id));

        return response;
    }



    // vistas

    @GetMapping("/lista")
    public String verTodos(Model model, Authentication auth) {

        String username = auth.getName();

        model.addAttribute("usuario",
                usuarioService.obtenerUsuarioByNomusuario(username));

        model.addAttribute("habitos",
                habitoService.listarPorUsuario(username));

        return "habitos/all-habits";
    }

    @GetMapping("/calendar")
    public String verCalendario(Model model, Authentication auth){

        String username = auth.getName();

        List<Habito> habitos = habitoService.listarPorUsuario(username);



        YearMonth yearMonth = YearMonth.now();
        String mes = yearMonth.getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("es", "ES"));


        List<Integer> dias = new ArrayList<>();
        for(int i = 1; i <= yearMonth.lengthOfMonth(); i++){
            dias.add(i);
        }


        int primerDiaSemana = LocalDate.now()
                .withDayOfMonth(1)
                .getDayOfWeek()
                .getValue();

        List<String> diasSemana = List.of("L", "M", "M", "J", "V", "S", "D");
        model.addAttribute("diasSemana", diasSemana);

        model.addAttribute("offset", primerDiaSemana - 1);

        Set<Integer> diasUnicos = new HashSet<>();

        for (Habito h : habitos) {
            diasUnicos.addAll(
                    registroService.obtenerDiasMarcadosDelMes(h.getId())
            );
        }
        model.addAttribute("usuario",
                usuarioService.obtenerUsuarioByNomusuario(username));

        model.addAttribute("habitos", habitos);
        model.addAttribute("diasMes", dias);
        model.addAttribute("mes", mes);
        model.addAttribute("diasMarcados", diasUnicos);

        return "habitos/calendar";
    }






}

