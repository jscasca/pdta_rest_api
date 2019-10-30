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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Comment parent;

    @ManyToOne
    @JoinColumn(name="thread_id")
    private CommentThread thread;

    @Formula("(SELECT COUNT(*) FROM comment c WHERE c.parent_id = id)")
    protected int replies;

    protected Comment(){}

    public Comment(User user, String text, CommentThread thread) {
        this.text = text;
        this.user = user;
        this.thread = thread;
    }

    public Comment(User user, String text, Comment parent) {
        this.text = text;
        this.parent = parent;
        this.user = user;
        this.thread = parent.thread;
    }


    public Long getId() { return id; }

    public Date getDate() {return start; }

    public String getText(){
        return text;
    }

    public Comment getParent(){
        return parent;
    }

    public CommentThread getThread() {
        return thread;
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
