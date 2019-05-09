package ru.kuznecov.ivan.rent.service;

import ru.kuznecov.ivan.rent.model.User;

public interface DataBaseService {

    void saveUser(User user);
    User readUser();
}
