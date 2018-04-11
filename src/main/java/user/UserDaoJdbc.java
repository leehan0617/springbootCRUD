package user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoJdbc implements IUserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @param id
     * @return User
     * @Comment 이 메소드는 위험하다. (아이디 없는사람을 조회하면 에러가 뜬다.
     *  무조건 1개가 떠야하기 때문에, 0개가 떠도 에러다. List로 감싸서 리턴을 시켜주면 에러 발생을 방지할 수 있다.
     */
    @Override
    public User getUserById(String id) {
        String sql = "SELECT id, name FROM user WHERE id = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public List<User> getUserList() {
        String sql = "SELECT id, password, name FROM user";
        // Spring에서 제공하는 BeanPropertyRowMapper을 사용하면 Entity에 대한 RowMapper 클래스를 작성할 필요가 없다.
        // 그러나 이건 sql에서 UserRowMapper에 정의한 모든 컬럼을 조회하지 않으면 에러가 발생한다. (예: SELECT id, name FROM user 와 같이 비밀번호를 안가져올라고 하면 에러가 발생)
        RowMapper<User> rowMapper = new UserRowMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int addUser(User user) {
        String sql = "INSERT INTO user (id, password, name) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, user.getId(), user.getPassword(), user.getName());
    }

    @Override
    public int updateUser(User user) {
        String sql = "UPDATE user SET password = ?, name = ? WHERE id = ? AND password = ?";
        return jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getId(), user.getPassword());
    }

    @Override
    public int deleteUser(String id) {
        String sql = "DELETE FROM user WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
