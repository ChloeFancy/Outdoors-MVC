package DAO.Impl;

import DAO.StrategyDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.StrategyEntity;
import model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class StrategyDAOImpl implements StrategyDAO {
    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    public JSONArray findArticlesByKeyword(String keyword) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String hql = "select u from StrategyEntity u where u.title like '%"+keyword+"%'";
        Query query= s.createQuery(hql);
        List<StrategyEntity> list = query.list();
        List<StrategyEntity> resultList = new ArrayList<>();
        for (StrategyEntity object : list) {
            resultList.add(object);
        }

        hql = "select u from StrategyEntity u where u.content like '%"+keyword+"%'";
        query= s.createQuery(hql);
        List<StrategyEntity> list_1 = query.list();
        for (StrategyEntity object : list_1) {
            resultList.add(object);
        }

        return JSONArray.parseArray(JSON.toJSONString(resultList));
    }
}
