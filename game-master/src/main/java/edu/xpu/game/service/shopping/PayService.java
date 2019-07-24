package edu.xpu.game.service.shopping;

import com.alipay.api.AlipayApiException;
import edu.xpu.game.entity.AlipayBean;

public interface PayService {
    String aliPay(AlipayBean alipayBean) throws AlipayApiException;
}
