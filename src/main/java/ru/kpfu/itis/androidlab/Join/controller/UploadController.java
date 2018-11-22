package ru.kpfu.itis.androidlab.Join.controller;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.androidlab.Join.details.CustomUserDetails;
import ru.kpfu.itis.androidlab.Join.service.interfaces.AuthenticationServiceInt;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Controller
public class UploadController {

    @Value("CLOUDINARY_URL")
    private String CLOUDINARY_URL;
//
//    private AuthenticationServiceInt authenticationService;
//
//    public UploadController(AuthenticationServiceInt authenticationService) {
//        this.authenticationService = authenticationService;
//    }
//
//    @GetMapping(value = "/upload")
//    @ResponseBody
//    public String testUp() {
//        return "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
//                "<body>\n" +
//                "\n" +
//                "<h1>Spring Boot file upload example</h1>\n" +
//                "\n" +
//                "<form method=\"POST\" action=\"/upload\" enctype=\"multipart/form-data\">\n" +
//                "    <input type=\"file\" name=\"file\" /><br/><br/>\n" +
//                "    <input type=\"submit\" value=\"Submit\" />\n" +
//                "</form>\n" +
//                "\n" +
//                "</body>\n" +
//                "</html>";
//    }
//
//    @SuppressWarnings("rawtypes")
//    @PostMapping(value = "/upload") // //new annotation since 4.3
//    public ResponseEntity singleFileUpload(@RequestParam("file") MultipartFile file, Authentication authentication) {
//
//        if (authentication == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//        Long userId = authenticationService.getUserIdByAuthentication(authentication);
//        Map uploadResult = null;
//        if (file.isEmpty()) {
//            //TODO error
//            return ResponseEntity.status(400).body(null);
//        }
//
//        try {
//
//            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//                    "cloud_name", "dnl4u0eua",
//                    "api_key", "241562529735436",
//                    "api_secret", "zcu8PHeXQRjPAXjUUb8nqIyNzzE"));
//
//            uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
//
//            String url = (String) uploadResult.get("url");
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return "redirect:/uploadStatus";
//    }
}
