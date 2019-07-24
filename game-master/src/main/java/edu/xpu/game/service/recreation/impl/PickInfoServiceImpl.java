package edu.xpu.game.service.recreation.impl;

import edu.xpu.game.entity.PickInfo;
import edu.xpu.game.entity.ProductInfo;
import edu.xpu.game.enums.PickCheckStatus;
import edu.xpu.game.repository.PickInfoRepository;
import edu.xpu.game.repository.ProductInfoRepository;
import edu.xpu.game.service.recreation.PickInfoService;
import edu.xpu.game.util.KeyUtil;
import edu.xpu.game.vo.PickVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @version 1.0
 * @className PickInfoServiceImpl
 * @description
 * @date 2019-06-07 17:45
 */
@Service
@Slf4j
public class PickInfoServiceImpl implements PickInfoService {
    private final PickInfoRepository pickRepository;
    private final ProductInfoRepository productRepository;

    @Autowired
    public PickInfoServiceImpl(PickInfoRepository pickRepository, ProductInfoRepository productRepository) {
        this.pickRepository = pickRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Boolean userAddPick(String pickProduct, Long pickTime, String userId) {
        PickInfo pickInfo = new PickInfo();
        pickInfo.setPickProduct(pickProduct);
        pickInfo.setPickTime(new Date(pickTime));

        Optional<ProductInfo> findProRet = productRepository.findById(pickInfo.getPickProduct());
        if(findProRet.isPresent()){
            List<PickInfo> pickProductList = pickRepository.findAllByPickUserAndAndPickProduct(userId, pickInfo.getPickProduct());
            if(!pickProductList.isEmpty()) return false;
            pickInfo.setPickId(KeyUtil.genUniqueKey());
            pickInfo.setPickUser(userId);
            pickInfo.setPickCheck(PickCheckStatus.NEW.getCode());

            PickInfo save = pickRepository.save(pickInfo);
            return save != null;
        }
        return false;
    }

    @Override
    public List<PickVO> findMyPick(String userId) {
        List<PickVO> ret = new ArrayList<>();
        List<PickInfo> allByPickUser = pickRepository.findAllByPickUser(userId);
        return getPickVOS(ret, allByPickUser);
    }

    @Override
    public Boolean cancelPick(String pickId) {
        Optional<PickInfo> byId = pickRepository.findById(pickId);
        if(byId.isPresent()){
            PickInfo pickInfo = byId.get();
            pickInfo.setPickCheck(PickCheckStatus.CANCEL.getCode());
            PickInfo save = pickRepository.save(pickInfo);
            return save != null;
        }
        return false;
    }

    @Override
    public List<ProductInfo> showAllProduct(){
        return productRepository.findAllByCategoryTypeNot(3);
    }

    @Override
    public List<PickVO> checkShow() {
        List<PickVO> ret = new ArrayList<>();
        List<PickInfo> all = pickRepository.findAll();
        return getPickVOS(ret, all);
    }

    @Override
    public Boolean checkOne(String pickId, Integer checkStatus) {
        Optional<PickInfo> byId = pickRepository.findById(pickId);
        if(byId.isPresent() && PickCheckStatus.contain(checkStatus)){
            PickInfo pickInfo = byId.get();
            pickInfo.setPickCheck(checkStatus);
            return pickRepository.save(pickInfo) != null;
        }
        return false;
    }

    private List<PickVO> getPickVOS(List<PickVO> ret, List<PickInfo> all) {
        PickVO pickVO;
        for(PickInfo pickInfo: all){
            pickVO = new PickVO();
            Optional<ProductInfo> byId = productRepository.findById(pickInfo.getPickProduct());
            if(byId.isPresent()){
                BeanUtils.copyProperties(byId.get(), pickVO);
            }
            pickVO.setPickTime(pickInfo.getPickTime());
            pickVO.setPickCheck(pickInfo.getPickCheck());
            pickVO.setPickId(pickInfo.getPickId());
            pickVO.setPickUser(pickInfo.getPickUser());
            ret.add(pickVO);
        }
        return ret;
    }
}
