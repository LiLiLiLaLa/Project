package edu.xpu.game.repository;

import edu.xpu.game.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    List<ShoppingCart> findAllByUserId(String userId);

    ShoppingCart findByProductIdAndUserId(String productId, String userId);
}
