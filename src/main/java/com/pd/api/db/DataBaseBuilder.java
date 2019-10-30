package com.pd.api.db;

import com.pd.api.entity.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import antlr.debug.Event;

import com.pd.api.security.SocialLogin;
import com.pd.api.db.indexer.*;

public class DataBaseBuilder {

    protected static String serverName = "localhost";
    protected static String schema = "posdta_1_0";
    
    private DataBaseBuilder() {};
    
    public static Configuration config() {
        AnnotationConfiguration ac = new AnnotationConfiguration();
        
        ac.addAnnotatedClass(Author.class);
        ac.addAnnotatedClass(Book.class);
        ac.addAnnotatedClass(BookFavorited.class);
        ac.addAnnotatedClass(BookReading.class);
        ac.addAnnotatedClass(BookRecordRequest.class);
        ac.addAnnotatedClass(BookWishlisted.class);
        ac.addAnnotatedClass(BookSuggestions.class);
        /*Book Details*/
        ac.addAnnotatedClass(BookDetail.class);
        /*Book similarities*/
        ac.addAnnotatedClass(BookSimilarity.class);
        ac.addAnnotatedClass(BookSimilarityVote.class);
        ac.addAnnotatedClass(Comment.class);
        ac.addAnnotatedClass(CommentThread.class);
        ac.addAnnotatedClass(Credential.class);
        ac.addAnnotatedClass(Event.class);
        ac.addAnnotatedClass(EventWithBook.class);
        ac.addAnnotatedClass(EventWithUser.class);
        ac.addAnnotatedClass(EventWithPosdta.class);
        ac.addAnnotatedClass(Club.class);
        ac.addAnnotatedClass(ClubMembership.class);
        ac.addAnnotatedClass(ClubReading.class);
        /**
         * Comments
         */
        ac.addAnnotatedClass(Comment.class);
        ac.addAnnotatedClass(CommentThread.class);
        ac.addAnnotatedClass(ClubThread.class);
        ac.addAnnotatedClass(BookThread.class);
        //
        ac.addAnnotatedClass(Language.class);
        ac.addAnnotatedClass(Posdta.class);
        ac.addAnnotatedClass(PosdtaVoting.class);
        ac.addAnnotatedClass(Role.class);
        ac.addAnnotatedClass(User.class);
        ac.addAnnotatedClass(UserBookInteraction.class);
        ac.addAnnotatedClass(UserVote.class);
        ac.addAnnotatedClass(VerificationToken.class);
        ac.addAnnotatedClass(Work.class);
        
        ac.addAnnotatedClass(UserRecommendations.class);
        
        ac.addAnnotatedClass(SocialProvider.class);
        ac.addAnnotatedClass(SocialLogin.class);
        
        ac.addAnnotatedClass(AuthorIndex.class);
        ac.addAnnotatedClass(BookIndex.class);
        
        ac.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        ac.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        ac.setProperty("hibernate.connection.url", "jdbc:mysql://" + serverName +  "/" + schema + "?useUnicode=true&amp;characterEncoding=UTF-8");
        ac.setProperty("hibernate.connection.username", "root");
        ac.setProperty("hibernate.connection.password", "root");
        
        return ac;
    }
    
    public static void runCreate(boolean exec) {
        SchemaExport se = new SchemaExport(config());
        se.setFormat(true);
        se.setDelimiter(";");
        se.setOutputFile("src/test/resources/posdta.sql");
        se.create(true, exec);
    }
    
    public static void runUpdate(boolean exec) {
        SchemaUpdate su = new SchemaUpdate(config());
        su.setFormat(true);
        su.setDelimiter(";");
        su.setOutputFile("src/test/resources/posdta-update.sql");
        su.execute(true, exec);
    }
    
    public static void main(String[] args) {
        //runCreate(false);
        runUpdate(false);
    }
}
