package DAO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.UserEntity;
import util.BasicResponse;

public interface UserDAO {
    //用户是否存在
    boolean hasUser(UserEntity userEntity) throws Exception;
    JSONArray findUsersNameLike(UserEntity userEntity,String name);
    UserEntity login(UserEntity userEntity);
    JSONObject findBrief(int id,int client);
}
