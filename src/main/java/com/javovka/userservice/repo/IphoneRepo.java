package com.javovka.userservice.repo;

import com.javovka.userservice.models.Iphone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IphoneRepo extends JpaRepository<Iphone, Long> {

    Iphone findByModel(String model);

}
