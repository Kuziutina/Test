package ru.kpfu.itis.androidlab.Join.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.androidlab.Join.dto.UserDto;
import ru.kpfu.itis.androidlab.Join.form.ProfileForm;
import ru.kpfu.itis.androidlab.Join.form.ResponseForm;
import ru.kpfu.itis.androidlab.Join.form.ResultForm;
import ru.kpfu.itis.androidlab.Join.form.SpecializationForm;
import ru.kpfu.itis.androidlab.Join.model.User;
import ru.kpfu.itis.androidlab.Join.repository.UserRepository;
import ru.kpfu.itis.androidlab.Join.service.interfaces.SpecializationServiceInt;
import ru.kpfu.itis.androidlab.Join.service.interfaces.UserServiceInt;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController extends MainController{
    private UserServiceInt userService;
    private SpecializationServiceInt specializationService;

    public UserController(UserServiceInt userService,
                          SpecializationServiceInt specializationService) {
        this.userService = userService;
        this.specializationService = specializationService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable Long id) {

        UserDto userDto = userService.getUserProfile(id);

        return ResponseEntity.ok(userDto);
    }

    @PostMapping(value = "/{id}/add_specialization")
    public ResponseEntity addSpecialization(@PathVariable Long id, @RequestBody SpecializationForm specializationForm) {
        User user = userService.getUser(id);
        ResultForm responseForm = specializationService.addSpecialization(user, specializationForm);

        return createResponseEntity(responseForm);
    }

    @PostMapping(value = "/{id}/change")
    public ResponseEntity<ResponseForm> changeProfile(@PathVariable Long id, @RequestBody ProfileForm profileForm) {
        ResultForm resultForm = userService.change(id, profileForm);
        return createResponseEntity(resultForm);
    }

    @GetMapping(value = "/{id}/upload")
    @ResponseBody
    public String testUp(@PathVariable Long id) {
        return "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<body>\n" +
                "\n" +
                "<h1>Spring Boot file upload example</h1>\n" +
                "\n" +
                "<form method=\"POST\" action=\"/user/"+ id +"/upload\" enctype=\"multipart/form-data\">\n" +
                "    <input type=\"file\" name=\"file\" /><br/><br/>\n" +
                "    <input type=\"submit\" value=\"Submit\" />\n" +
                "</form>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }

    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/{id}/upload")
    public ResponseEntity singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
        Map uploadResult = null;
        if (file.isEmpty()) {
            //TODO error
            return ResponseEntity.status(400).body(null);
        }
        try {

            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "dnl4u0eua",
                    "api_key", "241562529735436",
                    "api_secret", "zcu8PHeXQRjPAXjUUb8nqIyNzzE"));

            uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

            String url = (String) uploadResult.get("url");
            userService.addProfileImage(url, id);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }

}
