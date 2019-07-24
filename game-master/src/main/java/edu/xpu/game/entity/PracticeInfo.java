package edu.xpu.game.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @version 1.0
 * @className PracticeInfo
 * @description
 * @date 2019-06-06 21:16
 */
@Entity
@Data
@DynamicUpdate
public class PracticeInfo {
    @Id
    private String practiceId;

    private String practiceUser;

    private String practiceBase;
}
