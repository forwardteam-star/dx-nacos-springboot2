package com.darcy.nacos.core.base;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * 使用 @SuppressWarnings("unused") 的原因是, 此类的方法只会被 spring data jpa 的框架用到.
 *
 * @see org.springframework.data.jpa.repository.config.EnableJpaRepositories
 * @see SimpleJpaRepository
 */
@SuppressWarnings("unused")
public class EnhancedJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T, ID> {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private EntityManager entityManager;

    public EnhancedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(entityManager));
    }

    public EnhancedJpaRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public JdbcOperations getJdbcTemplate() {
        return namedParameterJdbcTemplate.getJdbcOperations();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public <S extends T> void update(S entity) {
        Session session = entityManager.unwrap(Session.class);
        session.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public <S extends T> void update(Iterable<S> entities) {
        Session session = entityManager.unwrap(Session.class);
        for (Object entity : entities) {
            session.update(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public <S extends T> void persist(S entity) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(entity);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public <S extends T> void persist(Iterable<S> entities) {
        Session session = entityManager.unwrap(Session.class);
        for (Object entity : entities) {
            session.persist(entity);
        }
    }

    private DataSource getDataSource(EntityManager entityManager) {
        SessionFactoryImpl sf = entityManager.getEntityManagerFactory().unwrap(SessionFactoryImpl.class);
        return ((DatasourceConnectionProviderImpl) sf.getServiceRegistry().getService(ConnectionProvider.class)).getDataSource();
    }

}
