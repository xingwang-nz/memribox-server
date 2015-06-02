package nz.co.xingsoft.memribox.server.persistence.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.common.QueryParameter;
import nz.co.xingsoft.memribox.server.common.QueryRange;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;

@Component
public class CommonDaoImpl implements CommonDao {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public void removeEntity(final Object entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    @Override
    public void saveOrUpdate(final Object entity) {
        this.saveOrUpdate(entity, false);
    }

    @Override
    public void saveOrUpdate(final Object entity, final boolean flush) {
        currentSession().saveOrUpdate(entity);
        if (flush) {
            currentSession().flush();
        }
    }

    @Override
    public <E> E retrieve(final Class<E> clazz, final Serializable id) {
        return (E) sessionFactory.getCurrentSession().get(clazz, id);
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void update(final Object entity) {
        this.update(entity, false);
    }

    @Override
    public void update(final Object entity, final boolean flush) {
        currentSession().merge(entity);
        if (flush) {
            currentSession().flush();
        }
    }

    @Override
    public void save(final Object entity) {
        this.save(entity, false);
    }

    @Override
    public void save(final Object entity, final boolean flush) {
        currentSession().persist(entity);
        if (flush) {
            currentSession().flush();
        }
    }

    /**
     * @see nz.co.CommonDao.t4.dao.CommonDao#getSingleResultByNamedQuery(java.lang.String,
     *      nz.co.ecngroup.t4.common.QueryParameter[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public <E extends Object> E getSingleResultByNamedQuery(final String namedQuery, final QueryParameter... parameters) {
        return (E) singleResultQuery(QueryType.NAMED_QUERY, namedQuery, parameters);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E extends Object> List<E> getListResultByNamedQuery(final String namedQuery, final QueryParameter... parameters) {
        return (List<E>) listResultQuery(QueryType.NAMED_QUERY, namedQuery, null, parameters);
    }

    @Override
    public <E extends Object> E getSingleResultByQuery(final String queryString, final QueryParameter... parameters) {
        return (E) singleResultQuery(QueryType.HQL, queryString, parameters);
    }

    @Override
    public <E extends Object> List<E> getListResultByQuery(final String queryString, final QueryParameter... parameters) {
        return listResultQuery(QueryType.HQL, queryString, null, parameters);
    }

    @Override
    public <E extends Object> E getSingleResultByNativeQuery(final String queryString, final QueryParameter... parameters) {
        return (E) singleResultQuery(QueryType.NATIVE_QUERY, queryString, parameters);
    }

    @Override
    public <E extends Object> List<E> getListResultByNativeQuery(final String queryString, final QueryParameter... parameters) {
        return listResultQuery(QueryType.NATIVE_QUERY, queryString, null, parameters);
    }

    @SuppressWarnings("rawtypes")
    private <E> E singleResultQuery(final QueryType queryType, final String queryString, final QueryParameter... parameters) {
        final Query query = createQuery(queryType, queryString);

        if (parameters != null && parameters.length > 0) {
            for (final QueryParameter parameter : parameters) {
                final Object bindParams = parameter.getValue();
                if (Collection.class.isInstance(bindParams)) {
                    query.setParameterList(parameter.getName(), (Collection) bindParams);
                } else {
                    query.setParameter(parameter.getName(), bindParams);
                }
            }
        }
        try {
            return (E) query.uniqueResult();
        } catch (final NonUniqueResultException e) {
            final List results = query.list();
            return results.size() > 0 ? (E) results.get(0) : null;
        }
    }

    @Override
    public <E extends Object> List<E> getListResultByNamedQuery(final String namedQuery, final QueryRange queryRange, final QueryParameter... parameters) {
        return (List<E>) listResultQuery(QueryType.NAMED_QUERY, namedQuery, queryRange, parameters);
    }

    @Override
    public <E extends Object> List<E> getListResultByQuery(final String queryString, final QueryRange queryRange, final QueryParameter... parameters) {
        return (List<E>) listResultQuery(QueryType.HQL, queryString, queryRange, parameters);
    }

    @Override
    public <E extends Object> List<E> getListResultByNativeQuery(final String queryString, final QueryRange queryRange, final QueryParameter... parameters) {
        return (List<E>) listResultQuery(QueryType.NATIVE_QUERY, queryString, queryRange, parameters);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    private <E> List<E> listResultQuery(final QueryType queryType, final String queryString, final QueryRange queryRange, final QueryParameter... parameters) {
        final Query query = createQuery(queryType, queryString);
        if (parameters != null && parameters.length > 0) {
            for (final QueryParameter parameter : parameters) {
                final Object bindParams = parameter.getValue();
                if (Collection.class.isInstance(bindParams)) {
                    query.setParameterList(parameter.getName(), (Collection) bindParams);
                } else {
                    query.setParameter(parameter.getName(), bindParams);
                }
            }
        }

        if (queryRange != null) {
            query.setFirstResult(queryRange.getStartRow()).setMaxResults(queryRange.getMaxResults());
        }

        return query.list();
    }

    private Query createQuery(final QueryType queryType, final String queryString) {
        Query query;
        switch (queryType) {
        case NAMED_QUERY:
            query = currentSession().getNamedQuery(queryString);
            break;
        case HQL:
            query = currentSession().createQuery(queryString);
            break;
        case NATIVE_QUERY:
            query = currentSession().createSQLQuery(queryString);
            break;
        default:
            query = currentSession().createQuery(queryString);
            break;
        }

        return query;
    }

    private static enum QueryType {
        NAMED_QUERY, HQL, NATIVE_QUERY;
    }

    @Override
    public <E> long countAll(final Class<E> clazz) {
        final Criteria criteria = currentSession().createCriteria(clazz);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    public void flushAndClear() {
        if (currentSession().isDirty()) {
            currentSession().flush();
            currentSession().clear();
        }

    }

}
