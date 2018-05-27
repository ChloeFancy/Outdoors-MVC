package DAO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface StrategyDAO {
    JSONArray findArticlesByKeyword(String keyword);
    JSONObject getArticleAndComments(int id);
}
