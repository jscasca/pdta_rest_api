package com.pd.api.entity;

import javax.persistence.*;

/**
 * Created by tin on 23/09/18.
 */
@Entity
@Table(name="thread_club")
@PrimaryKeyJoinColumn(name="id")
public class ClubThread extends CommentThread {
    @ManyToOne
    @JoinColumn(name="club_id")
    private Club club;

    @Transient
    private String className = "Group Thread";

    public ClubThread(){}

    public ClubThread(Club club) {
        super(ThreadType.CLUB);
        this.club = club;
    }

    public Club getClub(){
        return club;
    }
}
