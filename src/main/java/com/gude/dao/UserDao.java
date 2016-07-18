package com.gude.dao;

import com.gude.dao.base.BaseDao;
import com.gude.entity.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Gude
 * @Date 2015/12/27.
 */
@Repository
public class UserDao extends BaseDao<User> {
    public void save2(User user){
        getSession().save(user);
    }

    @Override
    public User findById(int id) {

        return (User) getSession().get(User.class,id);
    }
    public List<User> listAll(){
        Query query= getSession().createQuery("from User");
        return  query.list();
    }
}
