package nz.co.xingsoft.memribox.server.persistence.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractDao<T> {

    protected static final int MAX_RESULTS_DEFAULT = 100;

    @Inject
    protected CommonDao commonDao;

    protected Session session() {
        return commonDao.currentSession();
    }

    public void remove(final T t) {
        commonDao.removeEntity(t);
    }

    public void saveOrUpdate(final T t) {
        commonDao.saveOrUpdate(t);
    }

}
