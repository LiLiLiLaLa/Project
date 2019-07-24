package edu.xpu.game.service.recreation;

import edu.xpu.game.entity.PracticeBase;

import java.util.List;

/**
 * @version 1.0
 * @className PracticeBaseService
 * @description
 * @date 2019-06-06 20:05
 */
public interface PracticeBaseService {
    Boolean add(PracticeBase practiceBase);

    PracticeBase find(String practiceBaseId);

    void delete(String practiceBaseId);

    List<PracticeBase> findAll();
}
