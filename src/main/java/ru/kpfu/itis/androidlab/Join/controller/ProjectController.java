package ru.kpfu.itis.androidlab.Join.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.androidlab.Join.dto.ProjectDto;
import ru.kpfu.itis.androidlab.Join.service.interfaces.ProjectServiceInt;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

    private ProjectServiceInt projectService;

    public ProjectController(ProjectServiceInt projectService) {
        this.projectService = projectService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getProject(@PathVariable Long id) {
        ProjectDto projectDto = projectService.getProjectDto(id);

        return ResponseEntity.ok(projectDto);
    }
}
