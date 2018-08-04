package com.pd.api;


import com.pd.api.db.DAO;
import com.pd.api.entity.*;
import com.pd.api.entity.aux.CommentNode;
import com.pd.api.entity.aux.CommentTree;
import com.pd.api.entity.aux.MemberRegistration;
import com.pd.api.entity.aux.PosdtaWrapper;
import com.pd.api.exception.DuplicateResourceException;
import com.pd.api.security.AuthTools;
import com.pd.api.security.SocialLogin;
import com.pd.api.security.SocialLoginUsername;
import com.pd.api.service.impl.BookServiceImplementation;
import com.pd.api.service.impl.CommentServiceImplementation;
import com.pd.api.service.impl.SearchServiceImplementation;
import com.pd.api.util.LuceneIndexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

public class ApplicationTest {

    public static void main(String[] args) throws IOException {
        //testFavoritesList();
        //Credential c = DAO.get(Credential.class, 1L);
        //c.setPassword(AuthTools.encode("notroot"));
        //DAO.put(c);
        Long cId = 4L;
        reply(reply(cId, "Your welcome!"), "No problem");
        Long bookId = 16L;
//        Long replyTo = makeComment(bookId);
//        Long reply = reply(replyTo);
        CommentTree interactions = getCommentTree(bookId);
//        System.out.println(interactions);
        printCommentTree(interactions);
//        List<CommentNode> node = interactions.getNodes();
//        for(int i = 0; i < node.size(); i++) {
//            CommentNode c = node.get(i);
//        }
    }

    public static void printCommentTree(CommentTree tree) {
        List<CommentNode> nodes = tree.getNodes();
        for(int i = 0; i < nodes.size(); i++) {
            printCommentNode(nodes.get(i), 0);
        }
    }

    public static void printCommentNode(CommentNode node, int level) {
        System.out.println(tabs(level) + node.getComment());
        List<CommentNode> nodes = node.getNodes();
        for(int i = 0; i < nodes.size(); i++) {
            printCommentNode(nodes.get(i), level + 1);
        }
    }

    public static String tabs(int level) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < level; i++) {
            sb.append('|');
        }
        return sb.toString();
    }

    public static CommentTree getCommentTree(Long bookId) {
        return CommentServiceImplementation.getBookCommentTree(bookId);
    }

    public static Long reply(Long commentId) { return reply(commentId, "Thanks!");}
    public static Long reply(Long commentId, String reply) {
        Comment r = CommentServiceImplementation.reply("tin", commentId, reply);
        return r.getId();
    }

    public static Long makeComment(Long bookId) {
        String username = "tin";
        Comment c = CommentServiceImplementation.startBookThread(username, bookId, "Nice read");
        System.out.println(c);
        return c.getId();
    }
    
    public static void savePosdta() {
        String username = "tin";
        Long bookId = 1000L;
        PosdtaWrapper wrapper = new PosdtaWrapper("Random stuff", 1);
        BookServiceImplementation.savePosdta(username, bookId, wrapper);
        Book book = DAO.get(Book.class, bookId);
        System.out.println(book);
    }
    
    public static User testSocialUsername(SocialLoginUsername username) {
        Query q = DAO.createQuery("Select obj.user from SocialLogin obj where obj.providerUserId = ? and obj.socialProvider.name = ?", username.getId(), username.getProvider());
        try {
            return (User)q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static void getProviders() {
        Query q = DAO.createQuery("SELECT obj FROM " + SocialProvider.class.getName() + " obj");
        List<SocialProvider> providers = q.getResultList();
        for(SocialProvider p : providers) {
            System.out.println(p.getProviderName());
        }
    }
    
    public static void testSuggestions() {
        User user = DAO.getUserById(7L);
        BookServiceImplementation.updateSuggestions(user.getUserName());
    }
    
    public static void testMostRead() {
        List<Book> mostRead = BookServiceImplementation.getMostRead(0, 10);
        System.out.println(mostRead);
        
    }
    
    public static void testFavoritesList() {
        List<User> usersThatFavorited = BookServiceImplementation.getBookFavorites(2L, 0, 10);
        System.out.println(usersThatFavorited);
    }
    
    public static void testSearch() throws IOException {
        //index first
        //LuceneIndexer.index();
        System.out.println("done indexing");
        List<Book> bookRetrieved = SearchServiceImplementation.searchBooks("sputnik", 0, 10);
        System.out.println(bookRetrieved);
    }
}
