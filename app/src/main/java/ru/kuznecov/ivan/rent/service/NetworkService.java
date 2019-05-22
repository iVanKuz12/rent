package ru.kuznecov.ivan.rent.service;

import ru.kuznecov.ivan.rent.model.User;

public interface NetworkService {
    User signIn(String email, String password);
    String getEmail(String email);
}
