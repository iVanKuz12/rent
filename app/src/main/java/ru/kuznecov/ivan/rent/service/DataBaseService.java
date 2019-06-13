package ru.kuznecov.ivan.rent.service;

import ru.kuznecov.ivan.rent.pojo.User;

public interface DataBaseService {
    void update (User user);
    void saveUser(User user);
    User readUser();
    void deleteUser();
}
