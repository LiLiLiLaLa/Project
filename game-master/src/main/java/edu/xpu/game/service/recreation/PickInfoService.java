package edu.xpu.game.service.recreation;

import edu.xpu.game.entity.PickInfo;
import edu.xpu.game.entity.ProductInfo;
import edu.xpu.game.vo.PickVO;

import java.util.List;

public interface PickInfoService {
    Boolean userAddPick(String pickProduct, Long pickTime, String userId);

    List<PickVO> findMyPick(String userId);

    Boolean cancelPick(String pickId);

    List<ProductInfo> showAllProduct();

    List<PickVO> checkShow();

    Boolean checkOne(String pickId, Integer checkStatus);
}
