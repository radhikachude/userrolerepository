package com.springsecurity.scrumproject.controller;

import com.springsecurity.scrumproject.model.Roles;
import com.springsecurity.scrumproject.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/test")
    public String testapi(HttpServletRequest httpRequest) {
        return "Welcome to Scrum Master...!" + httpRequest.getSession().getId();
    }

    @PostMapping
    public ResponseEntity<Roles> createRole(@RequestBody Roles roles) {
        Roles createdRoles = roleService.createRole(roles);
        return ResponseEntity.ok(createdRoles);
    }

    @GetMapping
    public ResponseEntity<List<Roles>> getAllRoles() {
        List<Roles> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> getRoleById(@PathVariable Long id) {
        Roles roles = roleService.getRoleById(id).orElseThrow(() -> new RuntimeException("Role not found with id " + id));
        return ResponseEntity.ok(roles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable Long id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRole(@RequestBody Roles roles) {
        roleService.deleteRole(roles);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Roles> updateRoleById(@PathVariable Long id, @RequestBody Roles rolesDetails) {
        Roles updatedRoles = roleService.updateRoleById(id, rolesDetails);
        return ResponseEntity.ok(updatedRoles);
    }
}

