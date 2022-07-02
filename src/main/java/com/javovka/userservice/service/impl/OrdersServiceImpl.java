package com.javovka.userservice.service.impl;

import com.javovka.userservice.dto.OrderDto;
import com.javovka.userservice.models.Iphone;
import com.javovka.userservice.models.IphoneOrder;
import com.javovka.userservice.models.Orders;
import com.javovka.userservice.models.User;
import com.javovka.userservice.repo.IphoneOrderRepo;
import com.javovka.userservice.repo.IphoneRepo;
import com.javovka.userservice.repo.OrdersRepo;
import com.javovka.userservice.repo.UserRepo;
import com.javovka.userservice.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepo ordersRepo;
    private final UserRepo userRepo;
    private final IphoneRepo iphoneRepo;
    private final IphoneOrderRepo iphoneOrderRepo;

    @Override
    public List<Orders> getAllOrders() {
        return ordersRepo.findAll();
    }

    @Override
    public Orders saveOrder(OrderDto orderDto, String header1) {

        String username = getUsername(header1);

        final String usernameForThreads = username;
        Orders order = new Orders();
        order.setUser(userRepo.findByUsername(username));

        List<IphoneOrder> list = orderDto.getIphoneOrdersDto().stream()
                .map(iphoneOrder -> IphoneOrder.builder()
                        .quantities(iphoneOrder.getQuantities())
                        .iphone(iphoneRepo.findByModel(iphoneOrder.getIphone().getModel()))
                        .orders(order).build()).collect(Collectors.toList());

        int price = 0;
        boolean isAvailable = true;

        for (IphoneOrder iphoneOrder : list){
            price += (iphoneOrder.getQuantities() * iphoneOrder.getIphone().getPrice());
            if(isAvailable){
                if (!(iphoneRepo.findByModel(iphoneOrder.getIphone().getModel()).getQuantities()
                        >= iphoneOrder.getQuantities()) || iphoneOrder.getQuantities() == 0) isAvailable = false;
            }
        }

        final int priceForThread = price;
        order.setIphoneOrders(list);
        order.setPrice(price);

        if (isAvailable) {

            for (IphoneOrder iphoneOrder : list) {
                iphoneRepo.findByModel(iphoneOrder.getIphone().getModel()).setQuantities(
                        iphoneRepo.findByModel(iphoneOrder.getIphone().getModel()).getQuantities() - iphoneOrder.getQuantities()
                );
            }

            if (userRepo.findByUsername(username).getMoney() >= price) {

                userRepo.findByUsername(username).setMoney(userRepo.findByUsername(username).getMoney() - price);
                return ordersRepo.save(order);

                // some endpoint(що оплата пройшла успішно)

            } else {
                ordersRepo.save(order);

                Runnable wait = () -> {

                    try {
                        TimeUnit.SECONDS.sleep(600);
                        for (IphoneOrder iphoneOrder : list) {
                            Iphone iphone = iphoneRepo.findByModel(iphoneOrder.getIphone().getModel());
                            iphone.setQuantities(iphone.getQuantities() + iphoneOrder.getQuantities());
                            iphoneRepo.save(iphone);
                        }

                        deleteOrder(order);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                };

                Thread WaitThread = new Thread(wait);
                WaitThread.start();

                Runnable check = () -> {

                    while (WaitThread.isAlive()){
                        try {
                            Thread.sleep(1000);
                        if (userRepo.findByUsername(usernameForThreads).getMoney() >= priceForThread) {
                            User user = userRepo.findByUsername(usernameForThreads);
                            user.setMoney(user.getMoney() - priceForThread);
                            userRepo.save(user);
                            WaitThread.stop();
                            return;
                        }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Thread CheckThread = new Thread(check);
                CheckThread.start();

            }
        }else {
            // not enough phones in the store
        }

        return null;
    }

    private void deleteOrder(Orders orders){

        for (IphoneOrder x : orders.getIphoneOrders()){
            iphoneOrderRepo.delete(x);
        }

        ordersRepo.delete(orders);
    }

    private String getUsername(String header1){
        String token = header1.substring(7);
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        Pattern pattern = Pattern.compile("\"sub\":\"[a-zA-Z]*\"");
        String username = "";

        Matcher matcher = pattern.matcher(payload);
        while (matcher.find()) {
            int start=matcher.start();
            int end=matcher.end();
            username = payload.substring(start+7,end-1);
        }
        return username;
    }

}


