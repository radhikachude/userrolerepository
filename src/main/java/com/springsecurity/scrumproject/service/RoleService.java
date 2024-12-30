package com.springsecurity.scrumproject.service;

import com.springsecurity.scrumproject.model.Roles;
import com.springsecurity.scrumproject.repository.RoleRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class RoleService {
    @Autowired
    private RoleRepo roleRepository;

    public Roles createRole(Roles roles) {
        try {
            return roleRepository.save(roles);
        } catch (ObjectOptimisticLockingFailureException e)
        { // Handle exception, maybe retry or log
            throw new RuntimeException("Failed to save role due to concurrent modification.", e); }
        }

    public List<Roles> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Roles> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }

    public void deleteRole(Roles roles) {
        roleRepository.delete(roles);
    }

    public Roles updateRoleById(Long id, Roles rolesDetails) {
        Optional<Roles> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            Roles roles = roleOptional.get();
            roles.setName(rolesDetails.getName());
            return roleRepository.save(roles);
        } else {
            throw new RuntimeException("Role not found with id " + id);
        }
    }
}