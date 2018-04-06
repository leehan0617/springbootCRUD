package user;

import java.util.List;

public interface IUserDao {
    User getUserById(String id);

    List<User> getUserList();

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(String id);
}
