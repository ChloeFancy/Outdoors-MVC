package DAO;
import com.alibaba.fastjson.JSONObject;
import model.FollowEntity;
import model.UserEntity;

import java.util.List;

public interface FollowDAO{
    Boolean ifCanBeFollowedBy(int idFollowed,int idFollower);
//    List<JSONObject> addCanBeFollowed(List<JSONObject> list,int clientID);
    List<JSONObject> findFollower(int id,int clientID) throws Exception;
    List<JSONObject> findFollowed(int id,int clientID) throws Exception;
}
