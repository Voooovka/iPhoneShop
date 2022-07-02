package com.javovka.userservice.service.impl;

import com.javovka.userservice.error.ModelFoundException;
import com.javovka.userservice.models.Iphone;
import com.javovka.userservice.repo.IphoneRepo;
import com.javovka.userservice.service.IphoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IphoneServiceImpl implements IphoneService {

    private final IphoneRepo iphoneRepo;

    @Override
    public List<Iphone> getIphones() {
        return iphoneRepo.findAll();
    }

    @Override
    public Iphone saveIphone(Iphone iphone) {

            Iphone iphone1 = iphoneRepo.findByModel(iphone.getModel());
            if (iphone1 == null) {
                return iphoneRepo.save(iphone);
            } else {
                log.info("Iphone found in the database: {}", iphone1);
                throw new ModelFoundException("This iPhone model is already exist in the database");
            }
    }

}
