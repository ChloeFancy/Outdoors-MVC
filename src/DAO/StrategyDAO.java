package DAO;

import com.alibaba.fastjson.JSONArray;

public interface StrategyDAO {
    JSONArray findArticlesByKeyword(String keyword);
}
