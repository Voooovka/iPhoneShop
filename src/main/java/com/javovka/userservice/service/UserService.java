package com.javovka.userservice.service;

import com.javovka.userservice.models.Role;
import com.javovka.userservice.models.User;
import java.util.List;

public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> gerUsers();

}
