package DAO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.StrategyEntity;

import java.util.List;

public interface StrategyDAO {
    JSONArray findArticlesByKeyword(String keyword);
    JSONObject getArticleAndComments(int id);
    List<JSONObject> findStrategyWithWriterName(int idSpot);
}
