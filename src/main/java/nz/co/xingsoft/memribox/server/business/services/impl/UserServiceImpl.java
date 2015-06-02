package nz.co.xingsoft.memribox.server.business.services.impl;

import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

import nz.co.xingsoft.memribox.server.business.dto.ErrorCode;
import nz.co.xingsoft.memribox.server.business.dto.RegistrationRequest;
import nz.co.xingsoft.memribox.server.business.exception.RegistrationException;
import nz.co.xingsoft.memribox.server.business.services.UserService;
import nz.co.xingsoft.memribox.server.common.RoleCode;
import nz.co.xingsoft.memribox.server.persistence.dao.RoleDao;
import nz.co.xingsoft.memribox.server.persistence.dao.UserDao;
import nz.co.xingsoft.memribox.server.persistence.entity.User;
import nz.co.xingsoft.memribox.server.security.util.SaltedSHA256PasswordEncoder;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Named("userService")
@Transactional
public class UserServiceImpl
        implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private DozerBeanMapper beanMapper;

    @Inject
    private UserDao userDao;

    @Inject
    private RoleDao roleDao;

    @Inject
    private SaltedSHA256PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        final User user = userDao.getUserByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("The user with name %s was not found", username));
        }

        return user;
    }

    @Override
    public void regsiter(@Valid final RegistrationRequest request)
            throws RegistrationException {

        LOGGER.info("Register new user {}", request.getUsername());

        final User existUser = userDao.getUserByUserName(request.getUsername());

        if (existUser != null) {
            throw new RegistrationException(ErrorCode.USER_ALREADY_EXISTS, String.format("User %s already exists", request.getUsername()));
        }

        final User newUser = beanMapper.map(request, User.class);

        newUser.setCreatedTime(new Date());

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        newUser.setRoles(Collections.singleton(roleDao.getRoleByCode(RoleCode.USER)));

        userDao.saveOrUpdate(newUser);
    }
}
