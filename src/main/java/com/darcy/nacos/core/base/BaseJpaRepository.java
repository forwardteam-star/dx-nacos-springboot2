package com.darcy.nacos.core.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;

public interface BaseJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    Map<Class, BeanPropertyRowMapper> BEAN_PROPERTY_ROW_MAPPER_MAP = new HashMap<>();

    @SuppressWarnings("unchecked")
    default <S> BeanPropertyRowMapper<S> newBeanPropertyRowMapper(Class<S> clazz) {
        if (BEAN_PROPERTY_ROW_MAPPER_MAP.get(clazz) == null) {
            BEAN_PROPERTY_ROW_MAPPER_MAP.put(clazz, new BeanPropertyRowMapper(clazz));
        }
        return BEAN_PROPERTY_ROW_MAPPER_MAP.get(clazz);
    }

    JdbcOperations getJdbcTemplate();

    <S extends T> void update(S entity);

    <S extends T> void update(Iterable<S> entities);

    <S extends T> void persist(S entity);

    <S extends T> void persist(Iterable<S> entities);
}
