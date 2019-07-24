package edu.xpu.game.service.other.impl;

import edu.xpu.game.entity.BotanyBase;
import edu.xpu.game.entity.BotanyInfo;
import edu.xpu.game.repository.BotanyBaseRepository;
import edu.xpu.game.repository.BotanyInfoRepository;
import edu.xpu.game.service.other.BotanyInfoService;
import edu.xpu.game.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @version 1.0
 * @className BotanyInfoServiceImpl
 * @description
 * @date 2019-06-07 14:34
 */
@Service
@Slf4j
public class BotanyInfoServiceImpl implements BotanyInfoService {
    private final BotanyBaseRepository botanyRepository;
    private final BotanyInfoRepository infoRepository;

    @Autowired
    public BotanyInfoServiceImpl(BotanyBaseRepository repository, BotanyInfoRepository infoRepository) {
        this.botanyRepository = repository;
        this.infoRepository = infoRepository;
    }

    @Override
    public Boolean add(BotanyBase botanyInfo) {
        botanyInfo.setBotanyId(KeyUtil.genUniqueKey());
        return botanyRepository.save(botanyInfo) != null;
    }

    @Override
    public void delete(String botanyId) {
        botanyRepository.deleteById(botanyId);
    }

    @Override
    public BotanyBase find(String botanyId) {
        return botanyRepository.findById(botanyId).orElse(null);
    }

    @Override
    public Boolean save(BotanyBase botanyInfo) {
        return botanyRepository.save(botanyInfo) != null;
    }

    @Override
    public BotanyBase adoptBotany(String botanyId, String userId) {
        Optional<BotanyBase> byId = botanyRepository.findById(botanyId);
        List<BotanyInfo> allList = infoRepository.findAllByAdoptUserAndAdoptBotany(userId, botanyId);

        if(!allList.isEmpty()){
            //已经领养过了
            //String adoptBotany = allList.get(0).getAdoptBotany();
            //return botanyRepository.findById(adoptBotany).orElse(null);
            return null;
        }

        //存在且有库存
        if(byId.isPresent() && (byId.get().getBotanyNum() > 0)){
            BotanyInfo botanyInfo = new BotanyInfo();
            botanyInfo.setAdoptId(KeyUtil.genUniqueKey());
            botanyInfo.setAdoptUser(userId);
            botanyInfo.setAdoptBotany(botanyId);
            BotanyInfo infoSave = infoRepository.save(botanyInfo);
            assert infoSave != null;

            BotanyBase botanyBase = byId.get();
            botanyBase.setBotanyNum(botanyBase.getBotanyNum() - 1);
            BotanyBase baseSave = botanyRepository.save(botanyBase);
            assert baseSave != null;

            return byId.orElse(null);
        }
        return null;
    }

    @Override
    public List<BotanyBase> findAll() {
        return botanyRepository.findAll();
    }

    @Override
    public List<BotanyBase> findMyAll(String userId) {
        List<BotanyInfo> allByAdoptUser = infoRepository.findAllByAdoptUser(userId);
        List<BotanyBase> ret = new ArrayList<>();
        for(BotanyInfo botanyInfo: allByAdoptUser){
            Optional<BotanyBase> byId = botanyRepository.findById(botanyInfo.getAdoptBotany());
            byId.ifPresent(ret::add);
        }
        return ret;
    }
}
