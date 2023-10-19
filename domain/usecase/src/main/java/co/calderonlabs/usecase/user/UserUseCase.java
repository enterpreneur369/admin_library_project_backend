package co.calderonlabs.usecase.user;

import co.calderonlabs.model.book.Book;
import co.calderonlabs.model.user.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserUseCase {
    public static final List<User> userList = new ArrayList<User>() {{
        add(new User(1, "Juan Perez"));
        add(new User(2, "Maria Garcia"));
        add(new User(3, "Roberto Rodriguez"));
    }};

    public List<User> getUsers() {
        return this.userList;
    }

    public User createUser(User newUser) {
        if (newUser.getId() == null) {
            Integer lastId = userList.stream()
                    .map(User::getId)
                    .max(Integer::compareTo)
                    .orElse(0);

            newUser.setId(lastId + 1);
        }

        this.userList.add(newUser);
        return newUser;
    }

    public User getUserById(Integer id) {
        Optional<User> userOptional = userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
    }

    public User updateUser(Integer id, User userToUpdate) {
        Optional<User> existingUserOptional = userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setName(userToUpdate.getName());
            return existingUser;
        } else {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
    }

    public void deleteUser(Integer userId) {
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId().equals(userId)) {
                iterator.remove();
                return;
            }
        }
        throw new IllegalArgumentException("Usuario no encontrado con ID: " + userId);
    }
}
