package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public void create(String name) {
        if (this.roleDao.findByName(name) != null) {
            this.roleDao.save(new Role(name));
        } else {

        }
    }

    @Override
    public List<Role> getAllRole() {
        return this.roleDao.findAll();
    }

    @Override
    public Role findByName(String name) {
        return this.roleDao.findByName(name);
    }
}
