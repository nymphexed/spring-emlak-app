package com.proje.odevi.emlak.controller;

import com.proje.odevi.emlak.model.Ilce;
import com.proje.odevi.emlak.service.IlceService;
import com.proje.odevi.emlak.service.IlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ilce")
public class IlceController {

    private final IlceService ilceService;
    private final IlService ilService;



    public IlceController(IlceService ilceService, IlService ilService) {
        this.ilceService = ilceService;
        this.ilService = ilService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("ilceler", ilceService.findAll());
        return "ilce/list";
    }

    

    @GetMapping("/ekle")
    public String ekleForm(Model model) {
        model.addAttribute("ilce", new Ilce());
        model.addAttribute("iller", ilService.findAll());
        return "ilce/ekle";
    }

    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Ilce ilce) {
        ilceService.save(ilce);
        return "redirect:/ilce";
    }

    // İl seçilince ilçeleri JSON olarak döndürecek
    @GetMapping("/getByIl/{ilId}")
    @ResponseBody
    public List<Ilce> getIlcelerByIl(@PathVariable Long ilId) {
        return ilceService.findByIlId(ilId);
    }
}