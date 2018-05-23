package DAO.Impl;

import DAO.UserDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import controller.BaseController;
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
public class UserDAOImpl extends BaseDAOImpl<UserEntity> implements UserDAO {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    //用户是否存在
    @Override
    public boolean hasUser(UserEntity userEntity) throws Exception{
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        String hql_tel = "from UserEntity as a where tel = \'"+userEntity.getTel()+"\'";
        String hql_mail = "from UserEntity as a where mail = \'"+userEntity.getMail()+"\'";
        Query query = s.createQuery(hql_mail);
        List list_mail=query.list();
        System.out.println(userEntity.getMail());///
        System.out.println("list_mail"+list_mail);
        query = s.createQuery(hql_tel);
        List list_tel=query.list();
        System.out.println(userEntity.getTel());////
        System.out.println("list_tell"+list_tel);
//        if ((list_mail != null&&userEntity.getMail()!=null)
//                || (list_tel != null&&userEntity.getTel()!=null)
//                ||(userEntity.getTel()==null&&userEntity.getMail()==null) //电话邮箱都没有
//                ||userEntity.getPassword()==null                            //没有密码
//        ) {
//            return true;
//        }
        if(list_mail.isEmpty()&&list_tel.isEmpty()//都找不到
                &&!(userEntity.getMail()==null&&userEntity.getTel()==null)//都不为空
                &&userEntity.getPassword()!=null)//都不为空
            return false;
        System.out.println("***");
        tx.commit();
        s.close();
        //sessionFactory.close();//不能关！！！
        System.out.println("*****");
        return true;
    }

    @Override
    public JSONArray findUsersNameLike(UserEntity userEntity,String name) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        int currentUser = userEntity.getId();

        String hql = "select u from UserEntity u where u.name like '%"+name+"%' and u.id <> "+currentUser;
        Query query= s.createQuery(hql);
        List<UserEntity> list = query.list();
        List<JSONObject> resultList = new ArrayList<>();

        for (UserEntity object : list) {

            JSONObject json = new JSONObject();
            json.put("id",object.getId());
            json.put("name",object.getName());
            json.put("mail",object.getMail());
            json.put("tel",object.getMail());

            String findFollow = "select f from FollowEntity f where f.idFollower = "+currentUser
                    + " and f.idFollowed = "+object.getId();
            Query query1 = s.createQuery(findFollow);
            List<UserEntity> list1 = query1.list();
            json.put("canBeFollowed",!(list1.size()>0));

            resultList.add(json);
        }
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
        if(list.size()>0){
            return (UserEntity)list.get(0);
        }else{
            return null;
        }
    }
}
