package com.pd.api.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pd.api.db.DAO;
import com.pd.api.entity.Book;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;
import com.pd.api.entity.Work;

public class RecommendationUtil {

    public static HashMap<Long, Double> getSortedRecommendations(User user, int max) {
        //TODO sort and order to return only a certain number of recommendations
        return getUserBooksRecommendations(user);
    }
    
    public static HashMap<Long, Double> getUserBooksRecommendations(User user) {
        List<Posdta> posdtas = DAO.getAll(Posdta.class, "where user = ?", "", 0, 100, user);
        HashMap<Long, Integer> userBooks = new HashMap<Long, Integer>();
        HashMap<Long, Double> userRecommendations = new HashMap<Long, Double>();
        List<Book> books = new ArrayList<Book>();
        for(Posdta p : posdtas) {
            userBooks.put(p.getBook().getId(), p.getRating());
            books.add(p.getBook());
        }
            
        List<User> similarUsers = DAO.getAllUsersFromQuery("SELECT distinct(p.user) from Posdta p where p.book in (?)", 0, 10000, books);
        for(User u : similarUsers) {
            List<Posdta> similarPosdtas = DAO.getUserPosdtas(u.getId(), 0, 10000);
                List<Posdta> possibleRecommendations = new ArrayList<Posdta>();
                int userSimilarity = 0;
                int userShared = 0;
                int userTotal = similarPosdtas.size();
                for(Posdta similarPosdta : similarPosdtas) {
                    if(userBooks.containsKey(similarPosdta.getBook().getId())) {
                        userSimilarity += 3 - Math.abs(userBooks.get(similarPosdta.getBook().getId()) - similarPosdta.getRating());
                        userShared++;
                    } else {
                        possibleRecommendations.add(similarPosdta);
                    }
                }
                for(Posdta possibleRecommendation : possibleRecommendations) {
                    Double recommendationWeight = ((double)(userShared/userTotal)*userSimilarity);
                    if(possibleRecommendation.getRating() < 3) recommendationWeight = recommendationWeight * (-1);
                    if(userRecommendations.containsKey(possibleRecommendation.getBook().getId())) {
                        userRecommendations.put(possibleRecommendation.getBook().getId(), recommendationWeight + userRecommendations.get(possibleRecommendation.getBook().getId()));
                    } else {
                        userRecommendations.put(possibleRecommendation.getBook().getId(), recommendationWeight);
                    }
                }
        }
        return userRecommendations;
    }
}
