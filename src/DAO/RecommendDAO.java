package DAO;

import model.RecommendEntity;
import model.SpotEntity;

import java.util.List;

public interface RecommendDAO {
    List<SpotEntity> getRecSpot(RecommendEntity recommendEntity) throws Exception;
    List<SpotEntity> getMostPopularSpots() throws Exception;

}
