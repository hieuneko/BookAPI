package com.phamhieu.bookapi.persistence.user;

import com.phamhieu.bookapi.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.phamhieu.bookapi.persistence.user.UserEntityMapper.*;
import static org.apache.commons.collections4.IterableUtils.toList;

@Repository
@RequiredArgsConstructor
public class UserStore {

    private final UserRepository repository;

    public List<User> findAll() {
        return toUsers(toList(repository.findAll()));
    }

    public Optional<User> findUserById(final UUID userId) {
        return repository.findById(userId).map(UserEntityMapper::toUser);
    }

    public User addUser(final User user) {
        return toUser(repository.save(toUserEntity(user)));
    }

    public Optional<User> findByUsername(final String user) {
       return repository.findByUsername(user).map(UserEntityMapper::toUser);
    }

    public List<User> findByNameContain(final String name) {
        return toUsers(repository.findByUsernameOrFirstnameOrLastname(name, name, name));
    }

    public User updateUser(final User user) {
        return toUser(repository.save(toUserEntity(user)));
    }

    public void deleteUser(final UUID userId) {
        repository.deleteById(userId);
    }
}
