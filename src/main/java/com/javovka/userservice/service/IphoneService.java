package com.javovka.userservice.service;

import com.javovka.userservice.models.Iphone;

import java.util.List;

public interface IphoneService {

    List<Iphone> getIphones();
    Iphone saveIphone(Iphone iphone);

}
