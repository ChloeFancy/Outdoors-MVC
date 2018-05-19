package DAO.Impl;

import DAO.UserDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import util.BasicResponse;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    public JSONArray findAllUser() {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String hql = "select u from UserEntity u as user";
        Query query= s.createQuery(hql);
        List<Object[]> list = query.list();
        List<JSONObject> resultList = new ArrayList<>();
        for (Object[] object : list) {

            JSONObject json = new JSONObject();
            json.put("id",object[0]);
            json.put("name",object[1]);
            json.put("mail",object[2]);
            json.put("password",object[3]);

            resultList.add(json);
        }
        return JSONArray.parseArray(JSON.toJSONString(resultList));
    }

    @Override
    public void login(UserEntity userEntity,BasicResponse basicResponse) {
        String tel = userEntity.getTel();
        String mail = userEntity.getMail();
        String password = userEntity.getPassword();

        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        if(tel!=null){
            //手机登录


        }else if(mail!=null){
            //邮件登录
        }
    }
}
