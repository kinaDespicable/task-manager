package dev.taskManager.backend.services.implementation;

import dev.taskManager.backend.entities.Role;
import dev.taskManager.backend.errorHandlers.exceptions.RoleAlreadyExistException;
import dev.taskManager.backend.errorHandlers.exceptions.RoleNotFoundException;
import dev.taskManager.backend.repositories.RoleRepository;
import dev.taskManager.backend.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(Role role) {
        if( roleRepository.findByRoleName(role.getRoleName()).isPresent())
            throw new RoleAlreadyExistException("Role with name: ["+role.getRoleName()+"] already exist");
        return roleRepository.save(role);
    }

    @Override
    public Role fetchRoleById(Integer id) {
        return roleRepository.findById(id).orElseThrow(
                ()-> new RoleNotFoundException("Role with id: ["+id+"] not found"));
    }

    @Override
    public Role fetchRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(
                ()-> new RoleNotFoundException("Role with name: ["+roleName+"] not found")
        );
    }

    @Override
    public List<Role> fetchAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role updateRole(Integer id, Role role) {
        return roleRepository.findById(id).map(fetchedRole->{
            if(Objects.nonNull(role.getRoleName()) && !"".equalsIgnoreCase(role.getRoleName()))
                fetchedRole.setRoleName(role.getRoleName());
            return roleRepository.save(fetchedRole);
        }).get();
    }

    @Override
    public Boolean deleteRole(Integer id) {
        roleRepository.deleteById(id);
        return true;
    }

}
