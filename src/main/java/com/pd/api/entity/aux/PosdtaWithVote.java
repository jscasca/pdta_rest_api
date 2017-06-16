package com.pd.api.entity.aux;

import com.pd.api.entity.Posdta;
import com.pd.api.entity.UserVote;

/**
 * Created by tin on 3/05/17.
 */
public class PosdtaWithVote extends Posdta {

    private UserVote vote;

    protected String className = "PosdtaWithVote";

    public PosdtaWithVote() {}
    public PosdtaWithVote(UserVote vote) {
        id = vote.getPosdta().getId();
        work = vote.getPosdta().getWork();
        user = vote.getPosdta().getUser();
        book = vote.getPosdta().getBook();
        start = vote.getPosdta().getStart();
        finish = vote.getPosdta().getFinish();
        posdta = vote.getPosdta().getPosdta();
        rating = vote.getPosdta().getRating();
        votes = vote.getPosdta().getVotes();
        this.vote = vote;
    }

    public UserVote getVote() {
        return vote;
    }
}
