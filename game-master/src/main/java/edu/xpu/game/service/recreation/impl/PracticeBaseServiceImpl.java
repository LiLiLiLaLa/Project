package edu.xpu.game.service.recreation.impl;

import edu.xpu.game.entity.PracticeBase;
import edu.xpu.game.repository.PracticeBaseRepository;
import edu.xpu.game.service.recreation.PracticeBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @version 1.0
 * @className PracticeBaseServiceImpl
 * @description
 * @date 2019-06-06 20:06
 */
@Slf4j
@Service
public class PracticeBaseServiceImpl implements PracticeBaseService {
    private final PracticeBaseRepository repository;

    @Autowired
    public PracticeBaseServiceImpl(PracticeBaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Boolean add(PracticeBase practiceBase) {
        log.info("add = {}", practiceBase);
        return repository.save(practiceBase) != null;
    }

    @Override
    public PracticeBase find(String practiceBaseId) {
        Optional<PracticeBase> byId = repository.findById(practiceBaseId);
        return byId.orElse(null);
    }

    public void delete(String practiceBaseId) {
        repository.deleteById(practiceBaseId);
    }

    @Override
    public List<PracticeBase> findAll() {
        return repository.findAll();
    }
}
