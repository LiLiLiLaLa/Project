package edu.xpu.game.repository;

import edu.xpu.game.entity.PracticeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @version 1.0
 * @className PracticeInfoRepostory
 * @description
 * @date 2019-06-06 21:18
 */
public interface PracticeInfoRepository extends JpaRepository<PracticeInfo, String> {
    List<PracticeInfo> findAllByPracticeUser(String practiceUser);

    List<PracticeInfo> findAllByPracticeUserAndPracticeBase(String userId, String baseId);
}
