package com.bimcode.jasperreports.controller;

import com.bimcode.jasperreports.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/")
    public String showProducts(Model model){
        model.addAttribute("products", reportService.findAllProducts());
        return "products";
    }

    @PostMapping("/report")
    public String generateReport(@RequestParam("date") String date, @RequestParam("fileFormat") String fileFormat)
                                throws JRException, IOException {
        LocalDate localDate = LocalDate.parse(date);
        String fileLink = reportService.generateReport(localDate, fileFormat);
        return "redirect:/" + fileLink;
    }
}
