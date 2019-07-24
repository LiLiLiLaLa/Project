package edu.xpu.game.repository;

import edu.xpu.game.entity.ActivityInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityInfoRepository extends JpaRepository<ActivityInfo, String> {
}
