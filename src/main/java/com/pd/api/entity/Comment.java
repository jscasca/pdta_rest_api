package com.pd.api.entity;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tin on 4/11/17.
 * Comments can be used in communities, clubs, books and a myriad of things
 */
@Entity
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @ManyToOne
    @JoinColumn(name="user_id")
    protected User user;

    @Type(type="timestamp")
    protected Date start = new Date();

    private String text;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Comment parent;

    @Formula("(SELECT COUNT(*) FROM comment c WHERE c.parent_id = id)")
    protected int replies;

    protected Comment(){}

    public Comment(User user, String text) {
        this.text = text;
        this.user = user;
    }

    public Comment(User user, String text, Comment parent) {
        this.text = text;
        this.parent = parent;
        this.user = user;
    }


    public Long getId() { return id; }

    public Date getDate() {return start; }

    public String getText(){
        return text;
    }

    public Comment getParent(){
        return parent;
    }

    public User getUser(){
        return user;
    }

    public int countReplies() {
        return replies;
    }

    public String toString() {
        return "[" + id + ":" + user.getUserName() + ":" + text + "]";
    }
}
