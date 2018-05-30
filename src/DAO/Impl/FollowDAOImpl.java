package DAO.Impl;

import DAO.BaseDAO;
import DAO.FollowDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import model.FollowEntity;
import model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FollowDAOImpl implements FollowDAO {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Boolean ifCanBeFollowedBy(int idFollowed, int idFollower) {
        if(idFollower==0) return true;
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String findFollow = "from FollowEntity f where f.idFollower = "+idFollower
                + " and f.idFollowed = "+idFollowed;
        Query query1 = s.createQuery(findFollow);
        List<UserEntity> list1 = query1.list();
        tx.commit();

        return !(list1.size()>0);
    }

//    @Override
//    public List<JSONObject> addCanBeFollowed(List<JSONObject> list, int clientID) {
//        System.out.println(JSON.toJSONString(list));
//
//        if(clientID>0){
//            for(JSONObject user : list){
//                user.put("canBeFollowed",
//                        this.ifCanBeFollowedBy(Integer.parseInt(user.get("id").toString())
//                                ,clientID));
//            }
//        }
//        return list;
//    }

    @Override
    public List<JSONObject> findFollower(int id,int clientID) throws Exception {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String hql = "select  u.id,u.name,u.photoPath from UserEntity u ,FollowEntity f where u.id = f.idFollower " +
                "and f.idFollowed = "+id;

        Query query = s.createQuery(hql);

        List<Object[]> list = query.list();
        List<JSONObject> userList = new ArrayList<>();
        for(Object[] objects : list){
            JSONObject user = new JSONObject();
            user.put("id",objects[0]);
            user.put("name",objects[1]);
            user.put("photoPath",objects[2]);

            //先全部写true，然后再在后续的函数中，参照发送HTTP请求的客户端信息，判断canBeFollowed字段的值
            user.put("canBeFollowed",ifCanBeFollowedBy(Integer.parseInt(objects[0].toString()),clientID));

            userList.add(user);
        }

        tx.commit();
        return userList;
    }

    @Override
    public List<JSONObject> findFollowed(int id,int clientID) throws Exception{

        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String hql = "select  u.id,u.name,u.photoPath from UserEntity u ,FollowEntity f where u.id = f.idFollowed " +
                "and f.idFollower = "+id;

        Query query = s.createQuery(hql);

        List<Object[]> list = query.list();
        List<JSONObject> userList = new ArrayList<>();
        for(Object[] objects : list){
            JSONObject user = new JSONObject();
            user.put("id",objects[0]);
            user.put("name",objects[1]);
            user.put("photoPath",objects[2]);

            //先全部写true，然后再在后续的函数中，参照发送HTTP请求的客户端信息，判断canBeFollowed字段的值
            user.put("canBeFollowed",ifCanBeFollowedBy(Integer.parseInt(objects[0].toString()),clientID));

            userList.add(user);
        }
        tx.commit();
        return userList;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

//
//    @Override
//    public Boolean unfollow(FollowEntity followEntity) throws Exception {
//        FollowEntity one =  followEntityBaseDAO.findByQuery(followEntity).get(0);
//        followEntityBaseDAO.delete(one);
//        return null;
//    }
}
