package com.pd.api.entity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by tin on 17/11/18.
 */
@Embeddable
public class BookSimilarityVoteKeys implements Serializable {
    @ManyToOne
    @JoinColumn(name="user_id")
    protected User user;

    @ManyToOne
    @JoinColumn(name="similarity_id")
    protected BookSimilarity similarity;

    public BookSimilarityVoteKeys() {}
    public BookSimilarityVoteKeys(User user, BookSimilarity similairty) {
        this.user = user;
        this.similarity = similairty;
    }

    public User getUser() {
        return user;
    }

    public BookSimilarity getBookSimilarity() {
        return similarity;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBookSimilarity(BookSimilarity similarity) {
        this.similarity = similarity;
    }
}
