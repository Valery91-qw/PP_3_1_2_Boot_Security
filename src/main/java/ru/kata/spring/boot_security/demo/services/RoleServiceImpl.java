package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

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
        if (this.roleDao.findByName(name).isEmpty()) {
            this.roleDao.save(new Role(name));
        } else {

        }
    }

    @Override
    public Role get() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

}
