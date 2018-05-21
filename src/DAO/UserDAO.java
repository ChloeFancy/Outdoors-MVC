package DAO;

import com.alibaba.fastjson.JSONArray;
import model.UserEntity;
import util.BasicResponse;

public interface UserDAO {
    JSONArray findAllUser();
    void login(UserEntity userEntity, BasicResponse basicResponse);
}
