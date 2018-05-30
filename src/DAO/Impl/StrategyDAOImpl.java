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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class StrategyDAOImpl implements StrategyDAO {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    CommentDAOImpl commentDAO = (CommentDAOImpl) context.getBean("commentDAOImpl");
    BaseDAOImpl<StrategyEntity> strategyEntityBaseDAO = (BaseDAOImpl<StrategyEntity>) context.getBean("baseDaoImpl");

    @Override
    public JSONArray findArticlesByKeyword(String keyword) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String hql = "select u.id,u.name,s.title,s.content,s.idSpot,s.id from StrategyEntity s,UserEntity u where (s.title like '%"+keyword+"%' " +
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
            jsonObject.put("id",object[5]);

            resultList.add(jsonObject);
        }
        tx.commit();
        return JSONArray.parseArray(JSON.toJSONString(resultList));
    }

    @Override
    public JSONObject getArticleAndComments(int id) {
        StrategyEntity tmp = new StrategyEntity();
        tmp.setId(id);
        try {
            tmp = strategyEntityBaseDAO.findById(tmp);
            if(tmp==null){
                return null;
            }

            JSONArray comments = commentDAO.findByStrategy(id);
            JSONObject result = new JSONObject();
            result.put("comments",comments);

            Session s = sessionFactory.openSession();
            Transaction tx = s.beginTransaction();

            String hql = "select u from StrategyEntity s,UserEntity u where u.id = "+tmp.getIdWriter();
            Query query= s.createQuery(hql);
            List<UserEntity> list = query.list();
            tx.commit();
            JSONObject strategy = JSON.parseObject(JSON.toJSONString(tmp));
            strategy.put("writerName",list.get(0).getName());
            result.put("strategy",strategy);
            return result;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<JSONObject> findStrategyWithWriterName(int idSpot) {

        try {
            Session s = sessionFactory.openSession();
            Transaction tx = s.beginTransaction();

            String hql = "select u.id, u.name, u.photoPath, s.title, s.content, s.idSpot, s.id" +
                    " from StrategyEntity s,UserEntity u where u.id = s.idWriter and s.idSpot = "+ idSpot;
            Query query= s.createQuery(hql);
            List<Object[]> list = query.list();
            List<JSONObject> resultList = new ArrayList<>();
            for (Object[] object : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("writerId",object[0]);
                jsonObject.put("writerName",object[1]);
                jsonObject.put("userPhoto",object[2]);
                jsonObject.put("title",object[3]);
                jsonObject.put("content",object[4]);
                jsonObject.put("idSpot",object[5]);
                jsonObject.put("id",object[6]);//strategy id
                resultList.add(jsonObject);
            }
            tx.commit();
            return resultList;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
