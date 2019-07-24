package edu.xpu.game.service.recreation;

import edu.xpu.game.entity.PracticeBase;
import edu.xpu.game.entity.PracticeInfo;

import java.util.List;

/**
 * @version 1.0
 * @className PracticeInfoService
 * @description
 * @date 2019-06-06 21:20
 */
public interface PracticeInfoService {
    String userAdd(String userId, String practiceBaseId);

    PracticeInfo findById(String practiceId);

    List<PracticeBase> userAll(String userId);
}
