package DAO.Impl;

import DAO.SpotDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.SpotEntity;
import model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class SpotDAOImpl implements SpotDAO {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    public JSONArray findSpotsNameLike(String name) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String hql = "select u from SpotEntity u where u.name like '%"+name+"%'";
        Query query= s.createQuery(hql);
        List<SpotEntity> list = query.list();

        return JSONArray.parseArray(JSON.toJSONString(list));
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
