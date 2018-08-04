package com.pd.api.entity.aux;

import com.pd.api.entity.Comment;

import java.util.List;

/**
 * Created by tin on 16/07/18.
 */
public class CommentTree {

    private String name = "";

    private List<CommentNode> threads;

    public CommentTree(List<CommentNode> threads) {
        this.threads = threads;
    }

    public List<CommentNode> getNodes() {
        return threads;
    }

    public static CommentTree createCommentTree(List<CommentNode> nodes) {
        return new CommentTree(nodes);
    }
}
