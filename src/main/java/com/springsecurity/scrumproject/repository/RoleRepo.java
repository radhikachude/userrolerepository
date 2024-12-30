package com.springsecurity.scrumproject.repository;

import com.springsecurity.scrumproject.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Roles,Long> {
}
