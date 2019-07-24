package edu.xpu.game.repository;

import edu.xpu.game.entity.BotanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @version 1.0
 * @className BotanyInfoRepository
 * @description
 * @date 2019-06-07 15:16
 */
public interface BotanyInfoRepository extends JpaRepository<BotanyInfo, String> {

    List<BotanyInfo> findAllByAdoptUser(String userId);

    List<BotanyInfo> findAllByAdoptUserAndAdoptBotany(String userId, String botanyId);
}
