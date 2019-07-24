package edu.xpu.game.service.other;

import edu.xpu.game.entity.BotanyBase;

import java.util.List;

public interface BotanyInfoService {
    Boolean add(BotanyBase botanyBase);

    void delete(String botanyId);

    BotanyBase find(String botanyId);

    Boolean save(BotanyBase botanyInfo);

    BotanyBase adoptBotany(String botanyId, String userId);

    List<BotanyBase> findAll();

    List<BotanyBase> findMyAll(String userId);
}
