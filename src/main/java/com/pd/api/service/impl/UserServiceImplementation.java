package com.pd.api.service.impl;

import java.util.List;

import com.pd.api.db.DAO;
import com.pd.api.entity.Book;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;
import com.pd.api.exception.GeneralException;

public class UserServiceImplementation {
    
    public static List<User> getUsers(int first, int limit) {
        //TODO: implement method
        return DAO.getAll(User.class, first, limit);
    }

    public static User getUserById(Long id) {
        User user = DAO.get(User.class, id);
        if(user == null) throw new GeneralException("User not found", "user");
        return user;
    }
    
    public static User followUser(String username, Long id) {
        User user = DAO.getUserByUsername(username);
        User following = DAO.getUserById(id);
        user.addFollowee(following);
        DAO.put(user);
        return following;
    }
    
    public static User unfollowUser(String username, Long id) {
        User user = DAO.getUserByUsername(username);
        User following = DAO.getUserById(id);
        user.removeFollowee(following);
        DAO.put(user);
        return following;
    }
    
    public static List<User> getUserFollowers(Long userId, int first, int limit) {
        User user = DAO.getUserById(userId);
        return DAO.getAllUsersFromQuery("select distinct us from User us where ? in elements(us.following)", first, limit, user);
    }
    
    public static List<User> getUsersFollowing(Long userId, int first, int limit) {
        User user = DAO.getUserById(userId);
        return DAO.getAllUsersFromQuery("select distinct us.following from User us where us = ?", first, limit, user);
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
