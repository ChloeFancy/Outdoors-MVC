package DAO.Impl;

import DAO.RecommendDAO;
import model.RecommendEntity;
import model.SpotEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecommendDAOImpl implements RecommendDAO {

    private SessionFactory sessionFactory ;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
//    ApplicationContext context =
//            new ClassPathXmlApplicationContext("applicationContext.xml");

//    BaseDAOImpl<SpotEntity> spotDao = (BaseDAOImpl<SpotEntity>) context.getBean("baseDaoImpl");
//    BaseDAOImpl<RecommendEntity> recommendEntityBaseDAO = ( BaseDAOImpl<RecommendEntity> )context.getBean("baseDaoImpl");

    //获取推荐景点信息
    @Override
    public List<SpotEntity> getRecSpot(RecommendEntity recommendEntity) throws Exception{
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        String hql = "from RecommendEntity r where r.idUser = "+recommendEntity.getIdUser();
        Query query = s.createQuery(hql);

        List<RecommendEntity> list = query.list();
        List<SpotEntity> resultList = new ArrayList<>();
        List<SpotEntity> allSpotList;

        int count=0;

        if (list.size() >= 6) {
            for (RecommendEntity object : list) {
//                SpotEntity spotEntity = new SpotEntity();
//                spotEntity.setId(object.getIdSpot());
                hql = "from SpotEntity where id = "+object.getIdSpot();
                query = s.createQuery(hql);
                resultList.add((SpotEntity) query.list().get(0));
                if (++count >= 6) {
                    break;
                }
            }

        } else {
            SpotEntity spotEntity = new SpotEntity();

            hql = "from SpotEntity ";
            query = s.createQuery(hql);
            allSpotList = query.list();
            for (RecommendEntity object : list) {
                hql = "from SpotEntity where id = "+object.getIdSpot();
                query = s.createQuery(hql);
                resultList.add((SpotEntity) query.list().get(0));
                count++;
            }
            //找出取6-count个不包含在resultList里的景点
            for(SpotEntity objectS:allSpotList){
                boolean flag=false;//不被包含
                for (RecommendEntity objectR : list) {
                    if (objectS.getId()==objectR.getIdSpot()) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    resultList.add(objectS);
                    if (++count >= 6) {
                        break;
                    }
                }
            }
        }
        tx.commit();
        return resultList;
    }

    //访问量最高的6个景点
    @Override
    public List<SpotEntity> getMostPopularSpots() throws Exception {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        List<SpotEntity> resultList = new ArrayList<>();

        String hql = "SELECT B.idSpot, SUM(B.count) FROM BrowseEntity B GROUP BY B.idSpot ORDER BY SUM(B.count) desc ";
        Query query = s.createQuery(hql);
        List<Object> list = query.list();
        Iterator iterator = list.iterator();
        int count=0;
        while(iterator.hasNext()){
            Object[] obj = (Object[])iterator.next();
            hql = "from SpotEntity where id = "+Integer.parseInt(obj[0].toString());
            query = s.createQuery(hql);
            resultList.add((SpotEntity) query.list().get(0));
            if (++count >= 6) {
                break;
            }
        }
        tx.commit();
        return resultList;
    }
}
