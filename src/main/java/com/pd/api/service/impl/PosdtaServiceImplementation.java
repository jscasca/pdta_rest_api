package com.pd.api.service.impl;

import java.util.List;

import com.pd.api.db.DAO;
import com.pd.api.entity.Author;
import com.pd.api.entity.Book;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.PosdtaVoting;
import com.pd.api.entity.User;
import com.pd.api.entity.UserVote;
import com.pd.api.entity.Work;
import com.pd.api.entity.aux.AuthorWrapper;
import com.pd.api.entity.aux.WorkWrapper;
import com.pd.api.exception.DuplicateResourceException;
import com.pd.api.exception.InvalidStateException;

public class PosdtaServiceImplementation {

    public static List<Posdta> getPosdtas(int first, int limit) {
        return null;
        //return DAO.getAll(Author.class, first, limit);
    }
    
    public static PosdtaVoting upvotePosdta(String username, Long posdtaId) {
        User user = DAO.getUserByUsername(username);
        Posdta posdta =DAO.get(Posdta.class, posdtaId);
        UserVote vote = DAO.getUnique(UserVote.class, "where user = ? and posdta = ?", user, posdta);
        if(vote != null) {
            if(vote.isUpvote()) {                
                throw new InvalidStateException("You already upvoted this posdta");
            } else {
                //convert vote
                vote.makeUpvote();
                posdta.getVotes().changeToUpvote();
            }
        } else {
            vote = new UserVote(user, posdta, true);
            posdta.getVotes().upvote();
        }
        DAO.put(vote);
        posdta = DAO.put(posdta);
        return posdta.getVotes();
    }
    
    public static void removeUpvote(String username, Long posdtaId) {
        User user = DAO.getUserByUsername(username);
        Posdta posdta =DAO.get(Posdta.class, posdtaId);
        UserVote vote = DAO.getUnique(UserVote.class, "select v from UserVote where user = ? and posdta = ?", user, posdta);
        if(vote == null) {
            throw new InvalidStateException("You have not voted this posdta");
        }
        if(vote.isDownvote()) {
            throw new InvalidStateException("You have not voted this posdta");
        }
        DAO.delete(vote);
    }
    
    public static PosdtaVoting downvotePosdta(String username, Long posdtaId) {
        User user = DAO.getUserByUsername(username);
        Posdta posdta =DAO.get(Posdta.class, posdtaId);
        UserVote vote = DAO.getUnique(UserVote.class, "where user = ? and posdta = ?", user, posdta);
        if(vote != null) {
            if(vote.isDownvote()) {                
                throw new InvalidStateException("You already downvoted this posdta");
            } else {
                //convert vote
                vote.makeDownvote();
                posdta.getVotes().changeToDownvote();
            }
        } else {
            vote = new UserVote(user, posdta, false);
            posdta.getVotes().downvote();
        }
        DAO.put(vote);
        posdta = DAO.put(posdta);
        return posdta.getVotes();
    }
}
