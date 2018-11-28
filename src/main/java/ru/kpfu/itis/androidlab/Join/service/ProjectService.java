package ru.kpfu.itis.androidlab.Join.service;

import org.springframework.stereotype.Service;
import ru.kpfu.itis.androidlab.Join.dto.ProjectDto;
import ru.kpfu.itis.androidlab.Join.dto.SimpleProjectDto;
import ru.kpfu.itis.androidlab.Join.form.ProjectForm;
import ru.kpfu.itis.androidlab.Join.form.SpecializationForm;
import ru.kpfu.itis.androidlab.Join.model.Project;
import ru.kpfu.itis.androidlab.Join.model.Specialization;
import ru.kpfu.itis.androidlab.Join.model.User;
import ru.kpfu.itis.androidlab.Join.repository.ProjectRepository;
import ru.kpfu.itis.androidlab.Join.service.interfaces.ProjectServiceInt;
import ru.kpfu.itis.androidlab.Join.service.interfaces.SpecializationServiceInt;
import ru.kpfu.itis.androidlab.Join.service.interfaces.UserServiceInt;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService implements ProjectServiceInt {

    private UserServiceInt userService;
    private SpecializationServiceInt specializationService;
    private ProjectRepository projectRepository;

    public ProjectService(UserServiceInt userService, ProjectRepository projectRepository, SpecializationServiceInt specializationService) {
        this.userService = userService;
        this.projectRepository = projectRepository;
        this.specializationService = specializationService;
    }

    private List<Project> getUserProject(Long userId) {

        User user = userService.getUser(userId);
        return projectRepository.findAllByParticipants(user);
    }

    @Override
    public List<SimpleProjectDto> getUserProjectDtos(Long id) {
        List<Project> projects = getUserProject(id);
        List<SimpleProjectDto> projectDtos = new ArrayList<>();
        if (projects != null) {
            for (Project project: projects) {
                projectDtos.add(SimpleProjectDto.from(project));
            }
        }

        return projectDtos;
    }

    private Project getProject(Long id) {
        return projectRepository.getOne(id);
    }

    @Override
    public ProjectDto getProjectDto(Long id) {
        return ProjectDto.from(getProject(id));
    }

    @Override
    public Long createProject(ProjectForm projectForm) {
        Project project = getProject(projectForm);
        return project.getId();
                //TODO
    }


    private Project getProject(ProjectForm projectForm) {
        User user = userService.getUser(projectForm.getUserId());
        Project project = Project.builder().title(projectForm.getName())
                                    .description(projectForm.getDescription())
                                    .leader(user).build();

        projectRepository.save(project);

        Specialization specialization;
        //TODO return null, don't need, no it's need
        project.setVacancies(new ArrayList<>());
        for (SpecializationForm specializationForm: projectForm.getVacancies()) {
            specialization = specializationService.addSpecialization(project, specializationForm);
            project.getVacancies().add(specialization);
        }

        return project;
    }
}
