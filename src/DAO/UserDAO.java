package DAO;

import com.alibaba.fastjson.JSONArray;
import model.UserEntity;
import util.BasicResponse;

public interface UserDAO {
    JSONArray findAllUser();
    UserEntity login(UserEntity userEntity);
}
