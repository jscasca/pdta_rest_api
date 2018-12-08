package com.pd.api.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tin on 23/09/18.
 */
@Embeddable
public class ClubMembershipKeys implements Serializable {

    @ManyToOne
    @JoinColumn(name="user_id")
    protected User user;

    @ManyToOne
    @JoinColumn(name="club_id")
    protected Club club;

    public ClubMembershipKeys() {}
    public ClubMembershipKeys(User user, Club club) {
        this.user = user;
        this.club = club;
    }

    public User getUser() {
        return user;
    }

    public Club getClub() {
        return club;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
