package com.pd.api;

import com.pd.api.db.DAO;
import com.pd.api.entity.Author;
import com.pd.api.entity.Book;
import com.pd.api.entity.Credential;
import com.pd.api.entity.Language;
import com.pd.api.entity.Role;
import com.pd.api.entity.User;
import com.pd.api.entity.Work;
import com.pd.api.entity.aux.MemberRegistration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ApplicationTest {

    public static void main(String[] args) {
        
        /*
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/lang.txt"));
            String line;
            while((line = br.readLine()) != null) {
                String[] pairs = line.split("~");
                if(pairs.length == 2) {
                    Language lang = new Language(pairs[1].toUpperCase());
                    DAO.put(lang);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        Language ja = DAO.getLanguageByCode("ja");
        Language en = DAO.getLanguageByCode("en");
        Language es = DAO.getLanguageByCode("es");
        Book b = DAO.get(Book.class, 3L);
        b.setLanguage(en);
        DAO.put(b);
        /*
        Author rowling = new Author("J.K. Rowling");
        DAO.put(rowling);*/
        /*
        User user = DAO.getUserByUsername("test2");
        User following = DAO.getUserById(1L);
        user.addFollowee(following);
        DAO.put(user);
        System.out.println(user);*/ 
        
        /*
         * public static User followUser(String username, Long id) {
        User user = DAO.getUserByUsername(username);
        User following = DAO.getUserById(id);
        user.addFollower(following);
        DAO.put(user);
        return following;
    }
         */
    }
}
