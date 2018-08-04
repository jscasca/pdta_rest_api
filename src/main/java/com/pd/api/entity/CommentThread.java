package com.pd.api.entity;

import javax.persistence.*;

/**
 * Created by tin on 29/11/17.
 */
@Entity
@Table(name="thread")
@Inheritance(strategy=InheritanceType.JOINED)
public class CommentThread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @OneToOne
    @JoinColumn(name="comment_id")
    private Comment comment;

    protected CommentThread(){}

    protected CommentThread(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment(){
        return comment;
    }
}
