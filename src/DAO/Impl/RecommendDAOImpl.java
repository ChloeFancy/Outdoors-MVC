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
import sun.plugin.javascript.navig.LinkArray;

import java.util.ArrayList;
import java.util.List;

public class RecommendDAOImpl extends BaseDAOImpl<RecommendEntity> implements RecommendDAO {

    private SessionFactory sessionFactory;

    //获取推荐景点信息
    @Override
    public List<SpotEntity> getRecSpot(RecommendEntity recommendEntity) throws Exception{
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        List<RecommendEntity> list = findByQuery(recommendEntity);
        List<SpotEntity> resultList = new ArrayList<>();
        List<SpotEntity> allSpotList;

        int count=0;
        SpotEntity spotEntity = new SpotEntity();
        BaseDAOImpl<SpotEntity> spotDao = new BaseDAOImpl<>();
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

    @Override
    public List<SpotEntity> getMostPopularSpots() throws Exception {


        return null;
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
