package com.pd.api.entity;

import javax.persistence.*;

/**
 * Created by tin on 29/11/17.
 */
@Entity
@Table(name="thread")
@Inheritance(strategy=InheritanceType.JOINED)
public class CommentThread {

    public enum ThreadType {
        BOOK,
        CLUB
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    private ThreadType type;
//
//    @OneToOne
//    @JoinColumn(name="comment_id")
//    private Comment comment;

    protected CommentThread() {}
    protected CommentThread(ThreadType type){
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public ThreadType getType() {
        return type;
    }
}
