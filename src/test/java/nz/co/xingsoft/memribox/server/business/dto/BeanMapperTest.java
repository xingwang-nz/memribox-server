package nz.co.xingsoft.memribox.server.business.dto;

import static org.fest.assertions.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.SpringApplicationContextTest;
import nz.co.xingsoft.memribox.server.TestConstants;
import nz.co.xingsoft.memribox.server.persistence.entity.Role;
import nz.co.xingsoft.memribox.server.persistence.entity.User;

import org.dozer.DozerBeanMapper;
import org.junit.Test;

public class BeanMapperTest
        extends SpringApplicationContextTest {

    @Inject
    private DozerBeanMapper dozerBeanMapper;

    @Test
    public void testMapUserDetailsDto() {

        final User user = new User();
        user.setId(10L);
        user.setUsername("xing");
        user.setPassword(TestConstants.TEST_HASHED_PASSWORD);

        final Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setCode("ADMIN");
        roles.add(role);
        role = new Role();
        role.setCode("USER");
        roles.add(role);
        user.setRoles(roles);

        final UserDetailsDto userDetailsDto = dozerBeanMapper.map(user, UserDetailsDto.class);
        assertThat(userDetailsDto.getUsername()).isEqualTo("xing");
        assertThat(userDetailsDto.getUserRoles()).hasSize(2);
    }
}
