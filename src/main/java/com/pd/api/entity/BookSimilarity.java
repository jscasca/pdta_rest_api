package com.pd.api.entity;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tin on 17/11/18.
 */
@Entity
@Table(name="book_similarity",  uniqueConstraints=@UniqueConstraint(columnNames={"original_book_id", "similar_book_id"}))
public class BookSimilarity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @ManyToOne
    @JoinColumn(name="original_book_id")
    private Book original;

    @ManyToOne
    @JoinColumn(name="similar_book_id")
    private Book similar;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Type(type="timestamp")
    protected Date start = new Date();

    @Transient
    protected Boolean vote = null;

    //transient votes
    @Formula("(SELECT COUNT(*) FROM book_similarity_vote ent WHERE ent.similarity_id = id AND ent.vote = '1')")
    protected int upvotes;

    @Formula("(SELECT COUNT(*) FROM book_similarity_vote ent WHERE ent.similarity_id = id AND ent.vote = '0')")
    protected int downvotes;

    public BookSimilarity() {}
    public BookSimilarity(Book original, Book similar, User suggesting) {
        this.original = original;
        this.similar = similar;
        this.user = suggesting;
    }

    public BookSimilarity(BookSimilarity similarity) {
        this.id = similarity.getId();
        this.original = similarity.getOriginal();
        this.similar = similarity.getSimilar();
        this.start = similarity.getDate();
        this.user = similarity.getUser();
        //same for votes
    }

    public Long getId() {
        return id;
    }

    public Book getOriginal() {
        return original;
    }

    public Book getSimilar() {
        return similar;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return start;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public Boolean getVote() {
        return vote;
    }

    public void setVote(boolean voteValue) {
        this.vote = voteValue;
    }
}
