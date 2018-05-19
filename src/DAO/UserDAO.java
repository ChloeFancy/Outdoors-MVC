package DAO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.UserEntity;
import util.BasicResponse;

import java.util.List;

public interface UserDAO{
    JSONArray findAllUser();
    void login(UserEntity userEntity,BasicResponse basicResponse);
}
