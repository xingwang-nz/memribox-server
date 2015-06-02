package nz.co.xingsoft.memribox.server.persistence.dao;

import nz.co.xingsoft.memribox.server.common.QueryParameter;
import nz.co.xingsoft.memribox.server.common.RoleCode;
import nz.co.xingsoft.memribox.server.persistence.entity.Role;

import org.springframework.stereotype.Component;

@Component
public class RoleDao
        extends AbstractDao<Role> {

    public Role getRoleByCode(final RoleCode roleCode) {
        return commonDao.getSingleResultByQuery("select r from Role r where code=:code", new QueryParameter("code", roleCode.toString()));
    }
}
