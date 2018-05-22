package DAO;

import com.alibaba.fastjson.JSONArray;
import model.UserEntity;
import util.BasicResponse;

public interface UserDAO {
    //用户是否存在
    boolean hasUser(UserEntity userEntity) throws Exception;
    JSONArray findAllUser();
    UserEntity login(UserEntity userEntity);
}
