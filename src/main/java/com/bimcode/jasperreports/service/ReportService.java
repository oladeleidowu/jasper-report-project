package com.bimcode.jasperreports.service;

import com.bimcode.jasperreports.entity.Product;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
public interface ReportService {
    String generateReport(LocalDate localDate, String fileFormat) throws JRException, IOException;
    List<Product> findAllProducts();
}
