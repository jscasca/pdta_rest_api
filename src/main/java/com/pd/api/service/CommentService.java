package com.pd.api.service;

import com.pd.api.entity.Comment;
import com.pd.api.entity.CommentThread;
import com.pd.api.entity.ThreadForBook;
import com.pd.api.entity.aux.CommentTree;
import com.pd.api.security.CustomUserData;
import com.pd.api.service.impl.CommentServiceImplementation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by tin on 29/11/17.
 */
@Controller
@RequestMapping(value = "/api/comments")
public class CommentService {

    /**
     * Start a threat in a book
     * @param userData
     * @param bookId
     */
    @Secured("ROLE_USER")
    @RequestMapping(value="/books/{id:[0-9]+}/threads", method = RequestMethod.POST)
    @ResponseBody
    public Comment starBookThread(@ModelAttribute final CustomUserData userData,
                              @PathVariable("id") final Long bookId,
                              @RequestBody final String comment) {
        return CommentServiceImplementation.startBookThread(userData.getUsername(), bookId, comment);
    }

    //TODO: implement to return a tree of comments
    @RequestMapping(value="/books/{id:[0-9]+}/threads", method = RequestMethod.GET)
    @ResponseBody
    public List<CommentThread> getBookThreads(@PathVariable("id") final Long bookId,
                                              @RequestParam(value="start", defaultValue="0") final int start,
                                              @RequestParam(value="limit", defaultValue="100") final int limit){
        return CommentServiceImplementation.getBookThreads(bookId, start, limit);
    }

    @RequestMapping(value="/books/{id:[0-9]+}/commenttree")
    @ResponseBody
    public CommentTree getBookCommentTree(@PathVariable("id") final Long bookId) {
        return CommentServiceImplementation.getBookCommentTree(bookId);
    }

    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/replies", method = RequestMethod.POST)
    @ResponseBody
    public Comment reply(@ModelAttribute final CustomUserData userData,
                         @PathVariable("id") final Long commentId,
                         @RequestBody final String comment) {
        //TODO: implement
        return CommentServiceImplementation.reply(userData.getUsername(), commentId, comment);
    }
}
