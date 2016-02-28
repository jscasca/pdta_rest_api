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
        
        List<Book> favorites = getUserFavorites(id, 0, UserInfo.DEFAULT_LIMIT);
        List<Book> wishlisted = getUserWishlisted(id, 0, UserInfo.DEFAULT_LIMIT);
        List<Book> reading = getUserReading(id, 0, UserInfo.DEFAULT_LIMIT);
        List<Posdta> posdtas = getUserPosdtas(id, 0, UserInfo.DEFAULT_LIMIT);
        //List<User> followers = getUserFollowers(id, 0, UserInfo.DEFAULT_LIMIT);
        //List<User> following = getUsersFollowing(id, 0, UserInfo.DEFAULT_LIMIT);
        int followerCount = getUserFollowerCount(id);
        int followingCount = getUserFollowingCount(id);
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
        DAO.put(user);
        //Create event
        EventWithUser followEvent = new EventWithUser(user, Event.EventType.STARTED_FOLLOWING, following);
        DAO.put(followEvent);
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
        return DAO.getAllBooksFromQuery("select distinct bw.book from BookWishlisted bw where bw.user.id = ?", first, limit, userId);
    }
    
    public static List<Book> getUserWishlisted(Long userId, int first, int limit) {
        return DAO.getAllBooksFromQuery("select distinct bw.book from BookWishlisted bw where bw.user.id = ?", first, limit, userId);
    }
    
    public static List<Book> getUserReading(Long userId, int first, int limit) {
        return DAO.getAllBooksFromQuery("select distinct br.book from BookReading br where br.user.id = ?", first, limit, userId);
    }
    
    public static List<Posdta> getUserPosdtas(Long userId, int first, int limit) {
        return DAO.getAll(Posdta.class, "where user.id = ?", "", first, limit, userId);
    }
}
