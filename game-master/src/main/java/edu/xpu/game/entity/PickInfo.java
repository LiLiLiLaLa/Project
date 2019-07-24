package edu.xpu.game.entity;

import edu.xpu.game.enums.PickCheckStatus;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @version 1.0
 * @className PickInfo
 * @description
 * @date 2019-06-07 17:38
 */
@Entity
@Data
@DynamicUpdate
public class PickInfo {
    @Id
    private String pickId;

    private String pickUser;

    private String pickProduct;

    private Date pickTime;

    private Integer pickCheck = PickCheckStatus.NEW.getCode();
}
