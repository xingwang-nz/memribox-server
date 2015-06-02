package nz.co.xingsoft.memribox.server.persistence.dao;

import nz.co.xingsoft.memribox.server.common.QueryParameter;
import nz.co.xingsoft.memribox.server.persistence.entity.User;

import org.springframework.stereotype.Component;

@Component
public class UserDao
        extends AbstractDao<User> {

    public User getUserByUserName(final String username) {
        return commonDao.getSingleResultByQuery(
                "select u from User u left join fetch u.roles where upper(u.username)=:username", new QueryParameter(
                        "username", username.toUpperCase()));
    }

}
