package com.example.demo.controller;

import java.util.List;

import org.springframework.ui.Model;
import com.example.demo.entity.Country;
import com.example.demo.mapper.CountryCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/country")
public class CountryAdminController {

    @Autowired
    private CountryCodeMapper countryCodeMapper;

    // 一览
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("countries", countryCodeMapper.findAll());
        return "admin/country/list";
    }

    // 新规Form
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("country", new Country());
        return "admin/country/form";
    }

    // 编辑Form
    @GetMapping("/edit/{alpha3}")
    public String editForm(@PathVariable String alpha3, Model model) {
        model.addAttribute("country", countryCodeMapper.findByAlpha3(alpha3));
        return "admin/country/form";
    }

    // 保存（新建/更新共用）
    @PostMapping("/save")
    public String save(@ModelAttribute Country country) {
        if (countryCodeMapper.findByAlpha3(country.getAlpha3()) != null) {
            countryCodeMapper.update(country);
        } else {
            countryCodeMapper.insert(country);
        }
        return "redirect:/admin/country/list";
    }

    // 删除
    @GetMapping("/delete/{alpha3}")
    public String delete(@PathVariable String alpha3) {
        countryCodeMapper.delete(alpha3);
        return "redirect:/admin/country/list";
    }
}
