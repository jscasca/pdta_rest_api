package com.pd.api.util;

import java.util.List;
import java.util.Map;

import com.pd.api.db.DAO;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;

public class RecommendationUtil {

    public static Map<Long, Double> getUserBooksRecommendations(User user) {
        List<Posdta> posdtas = DAO.getAll(Posdta.class, "where user = ?", "", 0, 50, user);
        return null;
    }
}
