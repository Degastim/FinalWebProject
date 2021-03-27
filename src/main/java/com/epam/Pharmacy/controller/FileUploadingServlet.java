package com.epam.Pharmacy.controller;

import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.service.DrugPictureService;
import com.epam.Pharmacy.model.service.impl.DrugPictureServiceImpl;
import com.epam.Pharmacy.util.Base64Encoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@WebServlet(urlPatterns = {"/upload/*"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadingServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final DrugPictureService drugPictureService = DrugPictureServiceImpl.getInstance();
    private static final String PART_NAME = "picture";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String drugIdString = request.getParameter(RequestParameter.DRUG_ID);
        int drugId = Integer.parseInt(drugIdString);
        Part part = request.getPart(PART_NAME);
        part.getInputStream();
        InputStream inputStream = part.getInputStream();
        byte[] bytes;
        bytes = inputStream.readAllBytes();
        byte[] image = Base64Encoder.encode(bytes);
        try {
            drugPictureService.add(new String(image, StandardCharsets.UTF_8), drugId);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "File upload error");
            throw new ServletException(e);
        }
        response.sendRedirect(request.getHeader("REFERER"));
    }
}
