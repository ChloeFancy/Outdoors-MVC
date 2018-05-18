package DAO;

import com.alibaba.fastjson.JSONArray;
import model.UserEntity;

import java.util.List;

public interface UserDAO{
    JSONArray findAllUser();
}
