package com.pd.api.entity.aux;

import com.pd.api.entity.Comment;

import java.util.List;

/**
 * Created by tin on 16/07/18.
 */
public class CommentNode {

    private Comment comment;

    private List<CommentNode> replies;

    public CommentNode(Comment parent, List<CommentNode> replies) {
        this.comment = parent;
        this.replies = replies;
    }

    public List<CommentNode> getNodes() {
        return replies;
    }

    public Comment getComment() {
        return comment;
    }

    public static CommentNode createCommentNode(Comment comment, List<CommentNode> replies) {
        return new CommentNode(comment, replies);
    }
}
