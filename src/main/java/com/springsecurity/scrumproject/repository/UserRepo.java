package com.springsecurity.scrumproject.repository;

import com.springsecurity.scrumproject.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Long> {
    public Users findByUsername(String username);
}
