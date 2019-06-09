package com.halfmoon.home.repository;

import com.halfmoon.home.entity.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * 角色dao
 * @author: xuzhangwang
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
}
