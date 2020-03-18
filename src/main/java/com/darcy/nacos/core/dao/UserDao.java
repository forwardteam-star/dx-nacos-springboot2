package com.darcy.nacos.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darcy.nacos.core.catalog.CatalogReadOnlyTx;
import com.darcy.nacos.core.model.Users;

@CatalogReadOnlyTx
public interface UserDao extends JpaRepository<Users, String> {
}
