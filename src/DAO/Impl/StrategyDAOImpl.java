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

        String hql = "select u.id,u.name,s.title,s.content,s.idSpot from StrategyEntity s,UserEntity u where (s.title like '%"+keyword+"%' " +
                "or s.content like '%\"+keyword+\"%' ) " +
                "and u.id = s.idWriter";
        Query query= s.createQuery(hql);
        List<Object[]> list = query.list();
        List<JSONObject> resultList = new ArrayList<>();
        for (Object[] object : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("writerId",object[0]);
            jsonObject.put("writerName",object[1]);
            jsonObject.put("title",object[2]);
            jsonObject.put("content",object[3]);
            jsonObject.put("idSpot",object[4]);
            resultList.add(jsonObject);
        }

        return JSONArray.parseArray(JSON.toJSONString(resultList));
    }
}
