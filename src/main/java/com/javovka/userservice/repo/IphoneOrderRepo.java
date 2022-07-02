package com.javovka.userservice.repo;

import com.javovka.userservice.models.IphoneOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IphoneOrderRepo extends JpaRepository<IphoneOrder, Long> {
}
