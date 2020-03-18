package com.darcy.nacos.core.catalog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Transactional(value = CatalogRepositoryConfig.TX_MANAGER_NAME, readOnly = true)
public @interface CatalogReadOnlyTx {

}
