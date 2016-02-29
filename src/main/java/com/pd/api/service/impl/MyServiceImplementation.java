package com.pd.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.joda.time.Hours;

import com.pd.api.db.DAO;
import com.pd.api.entity.Book;
import com.pd.api.entity.Credential;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.Role;
import com.pd.api.entity.User;
import com.pd.api.entity.UserRecommendations;
import com.pd.api.entity.aux.LibraryView;
import com.pd.api.entity.aux.LoggedInWrapper;
import com.pd.api.exception.GeneralException;
import com.pd.api.util.RecommendationUtil;

public class MyServiceImplementation {
    
    public static final Long thresholdSinceLastCalculated = (long) (7 * 24 * 60 * 60 * 1000);
    public static final Long thresholdSinceLastPosdta = (long) (24 * 60 * 60 * 1000);
    public static final int DEFAULT_RECOMMENDATIONS = 20;
    
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);

    public static User getMe(String username) {
        User user = DAO.getUserByUsername(username);
        if(user == null) throw new GeneralException("User is null");
        return user;
    }
    
    public static LoggedInWrapper logIn(String username) {
        Credential credential = DAO.getUniqueByUsername(Credential.class, username);
        return new LoggedInWrapper(credential);
    }
    
    //TODO: allow the limit to be passed as a parameter
    public static LibraryView getUserLibraryView(String username) {
        User user = DAO.getUserByUsername(username);
        LibraryView lv = DAO.getUserLibraryView(user);
        return lv;
    }
    
    public static List<Book> getUserRecommendations(String username, int start, int limit) {
        User user = DAO.getUserByUsername(username);
        UserRecommendations recommendations = DAO.get(UserRecommendations.class, user.getId());
        HashMap<Long, Double> recMap = recommendations.getRecommendations();
        List<Book> recommendedBooks = new ArrayList<Book>();
        CalculateUserRecommendations calculateRecommendations = new CalculateUserRecommendations(user);
        for(Map.Entry<Long, Double> entry : recMap.entrySet()) {
            Book b = DAO.get(Book.class, entry.getKey());
            recommendedBooks.add(b);
            if(recommendedBooks.size() > limit) break;
        }
        Future future = executor.submit(calculateRecommendations);
        return recommendedBooks;
    }
    
    /*
    public static void calculateUserRecommendations(User user) {
        UserRecommendations recommendations = DAO.get(UserRecommendations.class, user.getId());
        Posdta p = DAO.getFirst(Posdta.class, " where obj.user = ? order by id desc", user);
        
        Long timeSinceLastCalculation = (new Date()).getTime() - recommendations.getLastTime().getTime();
        
        if(timeSinceLastCalculation > thresholdSinceLastCalculated || 
                (p.getFinish().getTime() > recommendations.getLastTime().getTime() && timeSinceLastCalculation > thresholdSinceLastPosdta)) {
            //Calculate the rec again
            HashMap<Long, Double> recommendationMap = RecommendationUtil.getSortedRecommendations(user, DEFAULT_RECOMMENDATIONS);
            recommendations.setRecommendations(recommendationMap);
            DAO.put(recommendations);
        }
    }*/
    
    private static class CalculateUserRecommendations implements Callable {

        private final User user;
        
        public CalculateUserRecommendations(User u) {
            this.user = u;
        }
        
        @Override
        public Object call() throws Exception {
            calculateRecommendations(user);
            return null;
        }
        
        private void calculateRecommendations(User u) {
            UserRecommendations recommendations = DAO.get(UserRecommendations.class, u.getId());
            Posdta p = DAO.getFirst(Posdta.class, " where obj.user = ? order by id desc", user);
            Long timeSinceLastCalculation = (new Date()).getTime() - recommendations.getLastTime().getTime();
            if(timeSinceLastCalculation > thresholdSinceLastCalculated || 
                    (p.getFinish().getTime() > recommendations.getLastTime().getTime() && timeSinceLastCalculation > thresholdSinceLastPosdta)) {
                HashMap<Long, Double> recommendationMap = RecommendationUtil.getSortedRecommendations(user, DEFAULT_RECOMMENDATIONS);
                recommendations.setRecommendations(recommendationMap);
                DAO.put(recommendations);
            }
        }
        
    }
}
