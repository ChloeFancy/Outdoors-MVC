package DAO.Impl;

import DAO.RecommendDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import model.RecommendEntity;
import model.SpotEntity;
import model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.plugin.javascript.navig.LinkArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecommendDAOImpl implements RecommendDAO {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");

    BaseDAOImpl<SpotEntity> spotDao = (BaseDAOImpl<SpotEntity>) context.getBean("baseDaoImpl");
    BaseDAOImpl<RecommendEntity> recommendEntityBaseDAO = ( BaseDAOImpl<RecommendEntity> )context.getBean("baseDaoImpl");
    //获取推荐景点信息
    @Override
    public List<SpotEntity> getRecSpot(RecommendEntity recommendEntity) throws Exception{
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        List<RecommendEntity> list = recommendEntityBaseDAO.findByQuery(recommendEntity);
        List<SpotEntity> resultList = new ArrayList<>();
        List<SpotEntity> allSpotList;

        int count=0;
        SpotEntity spotEntity = new SpotEntity();

        if (list.size() >= 6) {
            for (RecommendEntity object : list) {
                spotEntity.setId(object.getId());
                resultList.add(spotDao.findById(spotEntity));
                if (++count >= 6) {
                    break;
                }
            }
        } else {
            allSpotList = spotDao.findList(spotEntity);
            System.out.println(JSON.toJSONString(allSpotList));
            System.out.println();
            for (RecommendEntity object : list) {
                spotEntity.setId(object.getIdSpot());
                resultList.add(spotDao.findById(spotEntity));
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
        return resultList;
    }

    //访问量最高的6个景点
    @Override
    public List<SpotEntity> getMostPopularSpots() throws Exception {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        List<SpotEntity> resultList = new ArrayList<>();
        SpotEntity spotEntity = new SpotEntity();

        String hql = "SELECT B.idSpot, SUM(B.count) FROM BrowseEntity B GROUP BY B.idSpot ORDER BY SUM(B.count) desc ";
        Query query = s.createQuery(hql);
        List<Object> list = query.list();
        Iterator iterator = list.iterator();
        int count=0;
        while(iterator.hasNext()){
            Object[] obj = (Object[])iterator.next();
            spotEntity.setId(Integer.parseInt(obj[0].toString()));
            resultList.add(spotDao.findById(spotEntity));
            if (++count >= 6) {
                break;
            }
        }
        return resultList;
    }
}
