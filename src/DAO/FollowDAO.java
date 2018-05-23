package DAO;
import model.FollowEntity;
import model.UserEntity;

import java.util.List;

public interface FollowDAO{
    List<UserEntity> findFollower(FollowEntity followEntity) throws Exception;
    List<UserEntity> findFollowed(FollowEntity followEntity) throws Exception;
    Boolean unfollow(FollowEntity followEntity) throws Exception;
}
