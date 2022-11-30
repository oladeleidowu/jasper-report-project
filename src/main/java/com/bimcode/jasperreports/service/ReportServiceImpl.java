package com.bimcode.jasperreports.service;

import com.bimcode.jasperreports.dto.ProductDTO;
import com.bimcode.jasperreports.entity.Product;
import com.bimcode.jasperreports.repository.ProductRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {


    @Value("${product.report}")
    private String reportPath;

    @Value("${product.file}")
    private String filePath;

    @Autowired
    private ProductRepository productRepository;

    private JasperPrint getJasperPrint(List<ProductDTO> products, String resourceLocation) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile(resourceLocation);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Oladele");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return jasperPrint;
    }

    private Path getUploadPath(String fileFormat, JasperPrint jasperPrint, String fileName) throws IOException, JRException {
        String uploadDir = StringUtils.cleanPath(filePath);
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        //generate the report and save it in the just created folder
        if (fileFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint, uploadPath+fileName);
        }
        return uploadPath;
    }

    private String getPdfFileLink(String uploadPath){
        return uploadPath+"/"+"products.pdf";
    }

    @Override
    public String generateReport(LocalDate localDate, String fileFormat) throws JRException, IOException {
        List<Product> products = productRepository.findAllByCreatedAt(localDate);
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product: products){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(product.getName());
            productDTO.setId(product.getId());
            productDTO.setDescription(product.getDescription());
            productDTO.setCreatedAt(product.getCreatedAt());
            productDTO.setProductType(product.getProductType().name());
            productDTO.setPrice(product.getPrice());
            productDTOs.add(productDTO);
        }
        //load the file and compile it
        String resourceLocation = reportPath;
        JasperPrint jasperPrint = getJasperPrint(productDTOs,resourceLocation);
        //create a folder to store the report
        String fileName = "/"+"products.pdf";
        Path uploadPath = getUploadPath(fileFormat, jasperPrint, fileName);

        //create a private method that returns the link to the specific pdf file
        return getPdfFileLink(uploadPath.toString());
    }
    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

}
