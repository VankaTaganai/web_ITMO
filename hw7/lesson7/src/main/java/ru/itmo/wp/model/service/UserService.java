package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

/** @noinspection UnstableApiUsage*/
public class UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private static final String PASSWORD_SALT = "177d4b5f2e4f4edafa7404533973c04c513ac619";

    public void validateRegistration(User user, String password) throws ValidationException {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8 letters");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4 characters");
        }
        if (password.length() > 12) {
            throw new ValidationException("Password can't be longer than 12 characters");
        }
    }

    public long validateSwitchUser(User user, String userId, String value) throws ValidationException {
        if (!isAdmin(user)) {
            throw new ValidationException("You have no administrator rights");
        }

        if (!"Enable".equals(value) && !"Disable".equals(value)) {
            throw new ValidationException("Wrong button parameter");
        }

        long id;
        try {
            id = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new ValidationException("No user with id = " + userId);
        }

        if (find(id) == null) {
            throw new ValidationException("No user with id = " + userId);
        }

        return id;
    }

    public void register(User user, String password) {
        userRepository.save(user, getPasswordSha(password));
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User find(long id) {
        return userRepository.find(id);
    }

    public User validateAndFindByLoginAndPassword(String login, String password) throws ValidationException {
        User user = userRepository.findByLoginAndPasswordSha(login, getPasswordSha(password));
        if (user == null) {
            throw new ValidationException("Invalid login or password");
        }
        return user;
    }

    public boolean isAdmin(User user) {
        if (user == null) {
            return false;
        } else {
            return find(user.getId()).isAdmin();
        }
    }

    public void switchAdmin(long id, boolean adminValue) {
        userRepository.switchAdmin(find(id), adminValue);
    }
}
