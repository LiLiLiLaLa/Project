package edu.xpu.game.repository;

import edu.xpu.game.entity.PickInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @version 1.0
 * @className PickInfoRepository
 * @description
 * @date 2019-06-07 17:42
 */
public interface PickInfoRepository extends JpaRepository<PickInfo, String> {
    List<PickInfo> findAllByPickUser(String userId);
    List<PickInfo> findAllByPickUserAndAndPickProduct(String userId, String productId);
}
