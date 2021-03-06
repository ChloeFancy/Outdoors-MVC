package DAO.Impl;

import DAO.UserDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import controller.BaseController;
import model.SpotEntity;
import model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import util.BasicResponse;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class UserDAOImpl extends BaseDAOImpl<UserEntity> implements UserDAO {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;
    //用户是否存在
    @Override
    public boolean hasUser(UserEntity userEntity) throws Exception{
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        String hql_tel = "from UserEntity as a where tel = \'"+userEntity.getTel()+"\'";
        String hql_mail = "from UserEntity as a where mail = \'"+userEntity.getMail()+"\'";
        Query query = s.createQuery(hql_mail);
        List list_mail=query.list();
        System.out.println(userEntity.getMail());
        System.out.println("list_mail"+list_mail);
        query = s.createQuery(hql_tel);
        List list_tel=query.list();
        System.out.println(userEntity.getTel());
        System.out.println("list_tell"+list_tel);
        if(list_mail.isEmpty()&&list_tel.isEmpty()//都找不到
                &&!(userEntity.getMail()==null&&userEntity.getTel()==null)//都不为空
                &&userEntity.getPassword()!=null)//都不为空
            return false;
        tx.commit();
        s.close();
        return true;
    }

    @Override
    public JSONArray findUsersNameLike(UserEntity userEntity,String name) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        String hql = "";
        List<JSONObject> resultList = new ArrayList<>();
        if(userEntity!=null){
            int currentUser = userEntity.getId();

            hql = "select u from UserEntity u where u.name like '%"+name+"%' and u.id <> "+currentUser;

            Query query= s.createQuery(hql);
            List<UserEntity> list = query.list();

            for (UserEntity object : list) {

                JSONObject json = new JSONObject();
                json.put("id",object.getId());
                json.put("name",object.getName());
                json.put("photoPath",object.getPhotoPath());

                String findFollow = "select f from FollowEntity f where f.idFollower = "+currentUser
                        + " and f.idFollowed = "+object.getId();
                Query query1 = s.createQuery(findFollow);
                List<UserEntity> list1 = query1.list();
                json.put("canBeFollowed",!(list1.size()>0));

                resultList.add(json);
            }
        }else{
            hql = "select u from UserEntity u where u.name like '%"+name+"%'";
            Query query= s.createQuery(hql);
            List<UserEntity> list = query.list();

            for (UserEntity object : list) {

                JSONObject json = new JSONObject();
                json.put("id",object.getId());
                json.put("name",object.getName());
                json.put("photoPath",object.getPhotoPath());
                json.put("canBeFollowed",true);

                resultList.add(json);
            }
        }
        tx.commit();
        return JSONArray.parseArray(JSON.toJSONString(resultList));
    }

    @Override
    public UserEntity login(UserEntity userEntity) {
        String tel = userEntity.getTel();
        String mail = userEntity.getMail();
        String password = userEntity.getPassword();

        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String hql = "";
        if(tel!=null){
            //手机登录
            hql = "select u from UserEntity u where u.tel="+tel+" and u.password = "+password;
        }else if(mail!=null){
            //邮件登录
            hql = "select u from UserEntity u where u.mail="+mail+" and u.password = "+password;
        }
        Query query= s.createQuery(hql);
        List list = query.list();
        tx.commit();
        if(list.size()>0){
            return (UserEntity)list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public JSONObject findBrief(int id, int client) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String hql = "from UserEntity u where u.id = "+id;
        Query query = s.createQuery(hql);
        List result = query.list();
        try{
            UserEntity userEntity = (UserEntity)result.get(0);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",userEntity.getId());
            jsonObject.put("photoPath",userEntity.getPhotoPath());
            jsonObject.put("name",userEntity.getName());
            hql = "from FollowEntity f where f.idFollowed="+id+" and f.idFollower = "+client;
            System.out.println(hql);

            query = s.createQuery(hql);
            result = query.list();
            jsonObject.put("canBeFollowed",!(result.size()>0));
            return jsonObject;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
