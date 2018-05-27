package DAO.Impl;

import DAO.BaseDAO;
import DAO.FollowDAO;
import model.FollowEntity;
import model.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FollowDAOImpl extends BaseDAOImpl<FollowEntity> implements FollowDAO {


    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");

    BaseDAOImpl<UserEntity> dao = (BaseDAOImpl<UserEntity>) context.getBean("baseDaoImpl");

    @Override
    public List<UserEntity> findFollower(FollowEntity followEntity) throws Exception {
        List<UserEntity> resultList = new ArrayList<>();
        List list = findByQuery(followEntity);//包含follower的Follow集
        Iterator iterator = list.iterator();
        FollowEntity tempFollow;

        while(iterator.hasNext()){
            //获取当前follower的id，构造tempUser
            tempFollow = (FollowEntity)iterator.next();
            UserEntity tempUser=new UserEntity();
            tempUser.setId(tempFollow.getIdFollower());
            System.out.println(tempUser.getId());
            //根据id查询详细信息
            tempUser=dao.findById(tempUser);
            resultList.add(tempUser);
        }
        return resultList;
    }
    public List<UserEntity> findFollowed(FollowEntity followEntity) throws Exception{
        List<UserEntity> resultList = new ArrayList<>();
        List list = findByQuery(followEntity);//包含follower的Follow集
        Iterator iterator = list.iterator();
        FollowEntity tempFollow;
        while(iterator.hasNext()){
            //获取当前followed的id，构造tempUser
            UserEntity tempUser=new UserEntity();
            tempFollow = (FollowEntity)iterator.next();
            tempUser.setId(tempFollow.getIdFollowed());
            //根据id查询详细信息
            tempUser=dao.findById(tempUser);
            resultList.add(tempUser);
        }
        return resultList;
    }

    BaseDAOImpl<FollowEntity> followEntityBaseDAO = (BaseDAOImpl<FollowEntity>) context.getBean("baseDaoImpl");

    @Override
    public Boolean unfollow(FollowEntity followEntity) throws Exception {
        FollowEntity one =  followEntityBaseDAO.findByQuery(followEntity).get(0);
        followEntityBaseDAO.delete(one);
        return null;
    }
}
