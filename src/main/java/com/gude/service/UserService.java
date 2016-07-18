package com.gude.service;

import com.gude.dao.UserDao;
import com.gude.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Gude
 * @Date 2015/12/27.
 */
@Service
public class UserService {
    @Resource
    UserDao userDao;

    @CachePut(value = "user", key = "#user.username")
    public void save(User user) {
        userDao.save(user);
    }

    @Cacheable(value = "user", key = "#id")
    public User findById(int id) {
        User user = userDao.findById(id);

        return user;
    }

    /**
     * 查询结果不为空 才放进缓存 unless
     * @return
     */
    @Cacheable(value = "defaultCache",unless = "#result==null")
    public List<User> listAll() {
        return userDao.listAll();
    }
}
