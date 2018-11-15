package ru.kpfu.itis.androidlab.Join.service;

import org.springframework.stereotype.Service;
import ru.kpfu.itis.androidlab.Join.form.*;
import ru.kpfu.itis.androidlab.Join.model.Specialization;
import ru.kpfu.itis.androidlab.Join.model.SpecializationName;
import ru.kpfu.itis.androidlab.Join.model.User;
import ru.kpfu.itis.androidlab.Join.repository.SpecializationNameRepository;
import ru.kpfu.itis.androidlab.Join.repository.SpecializationRepository;
import ru.kpfu.itis.androidlab.Join.service.interfaces.SpecializationServiceInt;

@Service
public class SpecializationService implements SpecializationServiceInt {

    private SpecializationRepository specializationRepository;
    private SpecializationNameRepository specializationNameRepository;

    public SpecializationService(SpecializationRepository specializationRepository,
                                 SpecializationNameRepository specializationNameRepository) {
        this.specializationRepository = specializationRepository;
        this.specializationNameRepository = specializationNameRepository;
    }

    public ResultForm update(Long id, SpecializationForm specializationForm) {
        Specialization specialization = specializationRepository.getOne(id);
        SpecializationName specializationName = setTrueSpecializationName(specializationForm.getSpecializationName());
        Specialization repeatSpecialization = specializationRepository.findBySpecializationName(specializationName);

        if (specialization.getUser() != null && repeatSpecialization != null && !repeatSpecialization.getId().equals(specialization.getId())) {
            return ResultForm.builder().code(400).error("Invalid specialization").build();
        }

        specialization.setExperience(specializationForm.getExperience());
        specialization.setKnowledgeLevel(specializationForm.getKnowledgeLevel());
        specialization.setTechnologies(specializationForm.getTechnologies());
        specialization.setSpecializationName(setTrueSpecializationName(specializationForm.getSpecializationName()));

        specializationRepository.save(specialization);

        return ResultForm.builder().code(200).build();
    }

    @Override
    public ResultForm delete(Long id) {
        specializationRepository.deleteById(id);
        return new ResultForm();
    }

    public ResultForm addSpecialization(User user, SpecializationForm specializationForm) {
        SpecializationName specializationName = setTrueSpecializationName(specializationForm.getSpecializationName());
        Specialization repeatSpecialization = specializationRepository.findBySpecializationName(specializationName);

        if (repeatSpecialization != null) {
            return ResultForm.builder().code(400).error("Invalid specialization").build();
        }

        Specialization specialization = Specialization.builder()
                                            .specializationName(specializationName)
                                            .experience(specializationForm.getExperience())
                                            .knowledgeLevel(specializationForm.getKnowledgeLevel())
                                            .technologies(specializationForm.getTechnologies())
                                            .user(user)
                                            .build();
        specializationRepository.save(specialization);

        return ResultForm.builder().code(200).responseForm(new AddSpecializationResponseForm(specialization.getId())).build();
    }

    private SpecializationName setTrueSpecializationName(String name) {
        SpecializationName specializationName = specializationNameRepository.findByName(name);
        if (specializationName == null) {
            specializationName = SpecializationName.builder().name(name).build();
            specializationNameRepository.save(specializationName);
        }

        return specializationName;
    }
}
