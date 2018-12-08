package com.pd.api.entity.aux;

import com.pd.api.db.DAO;
import com.pd.api.entity.Club;
import com.pd.api.entity.ClubReading;
import com.pd.api.entity.User;

import java.util.List;

/**
 * Created by tin on 7/10/18.
 */
public class ClubView {

    private Club club;
    private List<User> members;
    private List<ClubReading> reading;
    private List<ClubReading> finished;
    private List<ClubReading> wishlisted;
    // add reading
    // add wishlist
    // maybe even add starting threads?

    public ClubView(Club club) {
        this.club = club;
        members = DAO.getClubMembers(club, 0, 100);
        reading = DAO.getClubReading(club, 0, 5);
        finished = DAO.getClubFinished(club, 0, 25);
        wishlisted = DAO.getClubWishlisted(club, 0, 50);
    }

    public Club getClub() {
        return club;
    }

    public List<User> getMembers() {
        return members;
    }
    public List<ClubReading> getReading() {
        return reading;
    }

    public List<ClubReading> getFinished() {
        return finished;
    }

    public List<ClubReading> getWishlisted() {
        return wishlisted;
    }
}
