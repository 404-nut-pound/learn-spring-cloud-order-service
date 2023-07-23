package io.hskim.learnspringcloudorderservice.repo;

import io.hskim.learnspringcloudorderservice.entity.OrderEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<OrderEntity, UUID> {
  Page<OrderEntity> findByUserIdEq(String userId, Pageable pageable);
}
