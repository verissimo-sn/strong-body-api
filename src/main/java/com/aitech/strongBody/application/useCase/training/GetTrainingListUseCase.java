package com.aitech.strongBody.application.useCase.training;

import com.aitech.strongBody.domain.entity.Training;
import com.aitech.strongBody.domain.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class GetTrainingListUseCase {

    @Autowired
    private final TrainingRepository repository;

    public Page<Training> execute(Pageable pageable) {
        log.info("execute::Pageable: {}", pageable.toString());
        return this.repository.getAll(pageable);
    }
}
