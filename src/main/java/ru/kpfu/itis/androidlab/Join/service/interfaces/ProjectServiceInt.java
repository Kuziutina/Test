package ru.kpfu.itis.androidlab.Join.service.interfaces;

import ru.kpfu.itis.androidlab.Join.dto.ProjectDto;
import ru.kpfu.itis.androidlab.Join.dto.SimpleProjectDto;
import ru.kpfu.itis.androidlab.Join.form.ProjectForm;

import java.util.List;

public interface ProjectServiceInt {
    List<SimpleProjectDto> getUserProjectDtos(Long userId);
    ProjectDto getProjectDto(Long id);

    Long createProject(ProjectForm projectForm);
}
