package DAO.Impl;

import DAO.BaseDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseDAOImpl<T> implements BaseDAO<T> {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    public String insert(T t) throws Exception {
        try{
            Session s = sessionFactory.openSession();
            Transaction tx = s.beginTransaction();
            s.save(t);
            tx.commit();
            s.close();
//            sessionFactory.close();
            return "success";
        }catch (Exception ex){
            ex.printStackTrace();
            return "fail";
        }
    }

    @Override
    public T findOne(T t) throws Exception {
        //登录专用
        //实现了login功能，因为判断实例是否相等时只判断了id、pwd
        try{
            Session s = sessionFactory.openSession();
            Transaction tx = s.beginTransaction();
            String className = t.getClass().getName();
            className = className.substring(className.indexOf(".")+1);

            String hql = "from "+className+" as a";
            Query query = s.createQuery(hql);

            List<T> list = query.list();

            for(T tmp : list){
                if(tmp.equals(t)){
                    tx.commit();
                    return tmp;
                }
            }
            tx.commit();
            return null;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public T findById(T t) throws Exception {
        try{
            Session s = sessionFactory.openSession();
            Transaction tx = s.beginTransaction();
            String className = t.getClass().getName();
            className = className.substring(className.indexOf(".")+1);


            Method m = t.getClass().getMethod("getId");
            String value = m.invoke(t).toString();

            String hql = "from "+className+" as a where a.id="+value;
            Query query = s.createQuery(hql);

            List<T> list = query.list();

            tx.commit();
            return list.size()>0?list.get(0):null;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public List<T> findByQuery(T t) throws Exception {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String className = t.getClass().getName();
        className = className.substring(className.indexOf(".")+1);
        Field[] field = t.getClass().getDeclaredFields();

        System.out.println(className);

        StringBuffer hql = new StringBuffer("from "+className+" as a ");

        for(int j=0 ; j<field.length; j++) { //遍历所有属性
            String name = field[j].getName(); //获取属性的名字
            System.out.println("name:"+name);
            String get = "get";
            if(name.equals("id")){
                //跳过id
                continue;
            }
            if(name.charAt(1)>='a'&&name.charAt(1)<='z'){
                get+=name.substring(0,1).toUpperCase()+name.substring(1);
            }
            System.out.println(get);
            Method m = t.getClass().getMethod(get);
            String value = ""+ m.invoke(t);
            if(!value.equals("null")){
                if(hql.indexOf("&")==-1){
                    hql.append("where ");
                }
                hql.append("a."+name+"='"+value + "'&");
            }
            //System.out.println(hql);
        }

        System.out.println(hql.substring(0,hql.length()-1).replace("&"," and "));
        Query query = s.createQuery(hql.substring(0,hql.length()-1).replace("&"," and "));

        List list = query.list();
        Iterator iterator = list.iterator();

        List<T> resultList = new ArrayList<>();
        T administratorEntity=null;
        while(iterator.hasNext()){
            administratorEntity = (T)iterator.next();
            resultList.add(administratorEntity);
        }
        return resultList;
    }

    @Override
    public List<T> findList(T t) throws Exception {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        String className = t.getClass().getName();
        className = className.substring(className.indexOf(".")+1);
        String hql = "from "+className+" as a";
        Query query = s.createQuery(hql);

        List list = query.list();
        Iterator iterator = list.iterator();

        List<T> resultList = new ArrayList<>();

        T administratorEntity=null;

        while(iterator.hasNext()){
            administratorEntity = (T)iterator.next();

            resultList.add(administratorEntity);
        }
        return resultList;
    }

    @Override
    public List<T> findPage(T t, int pageSize, int pageNum) throws Exception {
        return null;
    }

    @Override
    public long countAll(T t) throws Exception {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String className = t.getClass().getName();
        className = className.substring(className.indexOf(".")+1);
        String hql = "select count(*) from " +className+
                " as a";
        Query query = s.createQuery(hql);

        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public int update(T t) throws Exception {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        s.update(t);
        tx.commit();
        return 0;
    }

    @Override
    public int delete(T t) throws Exception {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        s.delete(t);
        tx.commit();

        return 0;
    }


//    @Override
//    public int deleteById(String id,String ident) throws Exception {
//        Session s = sessionFactory.openSession();
//        Transaction tx = s.beginTransaction();
////ident=Student/Teacher/Administrator
//        String hql = "delete from " +ident.concat("Entity")+
//                " as a where a."+ident.substring(0,1).toLowerCase()+"id="+id;
//        System.out.print(hql);
//        Query query = s.createQuery(hql);
//
//        query.executeUpdate();
//        tx.commit();
//
//        return 0;
//    }


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
