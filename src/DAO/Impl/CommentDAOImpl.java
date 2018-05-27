package DAO.Impl;

import DAO.CommentDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.SpotEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CommentDAOImpl implements CommentDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public JSONArray findByStrategy(int idStrategy) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String hql = "select c.content,u.name,u.id from CommentEntity c,UserEntity u where u.id = c.idWriter and  c.idStrategy="+idStrategy ;
        Query query= s.createQuery(hql);
        List<Object[]> list = query.list();
        List<JSONObject> result = new ArrayList<>();
        for(Object[] objects : list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content",objects[0]);
            jsonObject.put("name",objects[1]);
            jsonObject.put("id",objects[2]);
            result.add(jsonObject);
        }
        return JSONArray.parseArray(JSON.toJSONString(result));
    }
}
