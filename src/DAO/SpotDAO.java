package DAO;

import com.alibaba.fastjson.JSONArray;

public interface SpotDAO {
    JSONArray findSpotsNameLike(String name);
}
