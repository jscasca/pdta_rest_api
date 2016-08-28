package com.pd.api.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;

import com.pd.api.db.DAO;
import com.pd.api.entity.Book;
import com.pd.api.entity.Event;
import com.pd.api.entity.EventWithUser;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.LibraryView;
import com.pd.api.entity.aux.UserInfo;
import com.pd.api.entity.aux.UserToUser;
import com.pd.api.exception.GeneralException;

public class UserServiceImplementation {
    
    public static List<User> getUsers(int first, int limit) {
        //TODO: implement method
        return DAO.getAll(User.class, first, limit);
    }

    public static User getUserById(Long id) {
        User user = DAO.get(User.class, id);
        if(user == null) throw new GeneralException("User not found");
        return user;
    }
    
    public static LibraryView getUserLibraryView(Long id) {
        User user = DAO.get(User.class, id);
        if(user == null) throw new GeneralException("User not found");
        return DAO.getUserLibraryView(id);
    }
    
    public static UserInfo getUserInfo(Long id) {
        User user = DAO.get(User.class, id);
        if(user == null) throw new GeneralException("User not found");
        int followerCount = getUserFollowerCount(id);
        int followingCount = getUserFollowingCount(id);
        int favorites = DAO.getUserFavoriteCount(id);
        int reading = DAO.getUserReadingCount(id);
        int wishlisted = DAO.getUserWishlistCount(id);
        int posdtas = DAO.getUserPosdtaCount(id);
        UserInfo info = new UserInfo(user, reading, wishlisted, favorites, posdtas, followerCount, followingCount);
        return info;
    }
    
    public static UserToUser getUserInteractions(String username, Long userId) {
        User owner = DAO.getUserByUsername(username);
        User viewed = DAO.getUserById(userId);
        return new UserToUser(owner, viewed);
    }
    
    public static User followUser(String username, Long id) {
        User user = DAO.getUserByUsername(username);
        User following = DAO.getUserById(id);
        user.addFollowee(following);
        user = DAO.put(user);
        //Create event
        DAO.saveEventWithUser(new EventWithUser(user, Event.EventType.STARTED_FOLLOWING, following));
        return following;
    }
    
    public static User unfollowUser(String username, Long id) {
        User user = DAO.getUserByUsername(username);
        User following = DAO.getUserById(id);
        user.removeFollowee(following);
        DAO.put(user);
        return following;
    }
    
    /**
     * Get the users that USERID is following
     * @param userId
     * @param first
     * @param limit
     * @return
     */
    public static List<User> getUserFollowers(Long userId, int first, int limit) {
        User user = DAO.getUserById(userId);
        return DAO.getAllUsersFromQuery("select distinct us from User us where ? in elements(us.following)", first, limit, user);
    }
    
    public static int getUserFollowerCount(Long userId) {
        Query query = DAO.createNativeQueryWithParams("select count(*) from followers where user_id = ?", userId);
        int count = ((BigInteger)query.getSingleResult()).intValue();
        return count;
    }
    
    /**
     * Get the users that are following USERID
     * @param userId
     * @param first
     * @param limit
     * @return
     */
    public static List<User> getUsersFollowing(Long userId, int first, int limit) {
        User user = DAO.getUserById(userId);
        return DAO.getAllUsersFromQuery("select distinct us.following from User us where us = ?", first, limit, user);
    }
    
    public static int getUserFollowingCount(Long userId) {
        Query query = DAO.createNativeQueryWithParams("select count(*) from followers where follower_id = ?", userId);
        int count = ((BigInteger)query.getSingleResult()).intValue();
        return count;
    }
    
    public static List<Book> getUserFavorites(Long userId, int first, int limit) {
        return DAO.getUserFavorites(userId, first, limit);
    }
    
    public static List<Book> getUserWishlisted(Long userId, int first, int limit) {
        return DAO.getUserWishlisted(userId, first, limit);
    }
    
    public static List<Book> getUserReading(Long userId, int first, int limit) {
        return DAO.getUserReading(userId, first, limit);
    }
    
    public static List<Posdta> getUserPosdtas(Long userId, int first, int limit) {
        return DAO.getUserPosdtas(userId, first, limit);
    }
}
