package DAO;

import com.alibaba.fastjson.JSONArray;

public interface CommentDAO {
    JSONArray findByStrategy(int idStrategy);
}
