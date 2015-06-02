package nz.co.xingsoft.memribox.server.persistence.dao;

import java.io.Serializable;
import java.util.List;

import nz.co.xingsoft.memribox.server.common.QueryParameter;
import nz.co.xingsoft.memribox.server.common.QueryRange;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface CommonDao {
    void removeEntity(final Object entity);

    void saveOrUpdate(final Object entity);

    void saveOrUpdate(final Object entity, boolean flush);

    void update(final Object entity);

    void update(final Object entity, boolean flush);

    void save(final Object entity);

    void save(final Object entity, boolean flush);

    <E> E retrieve(final Class<E> clazz, final Serializable id);

    <E extends Object> E getSingleResultByNamedQuery(String namedQuery, QueryParameter... parameters);

    <E extends Object> List<E> getListResultByNamedQuery(String namedQuery, QueryParameter... parameters);

    <E extends Object> E getSingleResultByQuery(String queryString, QueryParameter... parameters);

    <E extends Object> List<E> getListResultByQuery(String queryString, QueryParameter... parameters);

    <E extends Object> E getSingleResultByNativeQuery(String queryString, QueryParameter... parameters);

    <E extends Object> List<E> getListResultByNativeQuery(String queryString, QueryParameter... parameters);

    <E extends Object> List<E> getListResultByNamedQuery(String namedQuery, QueryRange queryRange, QueryParameter... parameters);

    <E extends Object> List<E> getListResultByQuery(String queryString, QueryRange queryRange, QueryParameter... parameters);

    <E extends Object> List<E> getListResultByNativeQuery(String queryString, QueryRange queryRange, QueryParameter... parameters);

    <E> long countAll(final Class<E> clazz);

    SessionFactory getSessionFactory();

    Session currentSession();

    void flushAndClear();

}
