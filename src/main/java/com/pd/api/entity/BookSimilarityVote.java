package com.pd.api.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

/**
 * Created by tin on 17/11/18.
 */
@Entity
@Table(name="book_similarity_vote")
public class BookSimilarityVote {

    @Id
    private BookSimilarityVoteKeys id;

    @Type(type="org.hibernate.type.NumericBooleanType")
    @Column(columnDefinition = "TINYINT", nullable = false)
    private boolean vote; // True is on favour; False is against

    public BookSimilarityVote() {}
    public BookSimilarityVote(User user, BookSimilarity similarity, boolean vote) {
        this.id = new BookSimilarityVoteKeys(user, similarity);
        this.vote = vote;
    }

    public User getUser() {
        return this.id.getUser();
    }

    public BookSimilarity getBookSimilarity() {
        return this.id.getBookSimilarity();
    }

    public boolean getVote() {
        return this.vote;
    }
}
