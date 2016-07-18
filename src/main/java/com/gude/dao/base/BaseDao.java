package com.gude.dao.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Gude
 * @Date 2015/12/27.
 */

public abstract class BaseDao<T> {
    @Resource
    private SessionFactory sessionFactory;
//    @Resource(name="sessionFactory")
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory= sessionFactory;
//    }
//    public SessionFactory getSessionFactory(){
//        return sessionFactory;
//    }
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    public void save(T obj){
       getSession().save(obj);
    }

    public void delete(T obj){
        getSession().delete(obj);
    }

    public void update(T obj){
        getSession().update(obj);

    }



    public abstract T findById(int id);

}
