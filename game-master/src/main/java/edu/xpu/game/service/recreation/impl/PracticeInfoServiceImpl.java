package edu.xpu.game.service.recreation.impl;

import edu.xpu.game.entity.PracticeBase;
import edu.xpu.game.entity.PracticeInfo;
import edu.xpu.game.repository.PracticeBaseRepository;
import edu.xpu.game.repository.PracticeInfoRepository;
import edu.xpu.game.service.recreation.PracticeInfoService;
import edu.xpu.game.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @version 1.0
 * @className PracticeInfoServiceImpl
 * @description
 * @date 2019-06-06 21:20
 */
@Service
public class PracticeInfoServiceImpl implements PracticeInfoService {
    private final PracticeBaseRepository baseRepository;
    private final PracticeInfoRepository infoRepository;

    @Autowired
    public PracticeInfoServiceImpl(PracticeBaseRepository baseRepository, PracticeInfoRepository infoRepository) {
        this.baseRepository = baseRepository;
        this.infoRepository = infoRepository;
    }

    @Override
    public String userAdd(String userId, String practiceBaseId) {
        Optional<PracticeBase> byId = baseRepository.findById(practiceBaseId);
        if(!byId.isPresent()) return "Id对应的实践基地不存在";

        List<PracticeInfo> base = infoRepository.findAllByPracticeUserAndPracticeBase(userId, practiceBaseId);
        if(!base.isEmpty()) return "已经预约过此基地了";

        PracticeBase practiceBase = byId.get();
        if(practiceBase.getBaseMaxpeople() - practiceBase.getBaseJoin() >= 1){
            practiceBase.setBaseJoin(practiceBase.getBaseJoin() + 1);
        }else{
            return "预约人数已满";
        }

        PracticeInfo practiceInfo = new PracticeInfo();
        practiceInfo.setPracticeId(KeyUtil.genUniqueKey());
        practiceInfo.setPracticeUser(userId);
        practiceInfo.setPracticeBase(practiceBaseId);

        PracticeBase save1 = baseRepository.save(practiceBase);
        assert save1 != null;

        PracticeInfo save2 = infoRepository.save(practiceInfo);
        assert save2 != null;

        return null;
    }

    @Override
    public PracticeInfo findById(String practiceId) {
        Optional<PracticeInfo> byId = infoRepository.findById(practiceId);
        return byId.orElse(null);
    }

    @Override
    public List<PracticeBase> userAll(String userId) {
        List<PracticeBase> retList = new ArrayList<>();
        List<PracticeInfo> all = infoRepository.findAllByPracticeUser(userId);
        for(PracticeInfo practiceInfo: all){
            Optional<PracticeBase> byId = baseRepository.findById(practiceInfo.getPracticeBase());
            byId.ifPresent(retList::add);
        }
        return retList;
    }
}
