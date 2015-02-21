package com.pd.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.pd.api.db.DAO;
import com.pd.api.entity.Author;
import com.pd.api.entity.Book;
import com.pd.api.entity.Language;
import com.pd.api.entity.Work;

public class DataSetBuilder {

    enum Type {USER, AUTHOR, OTHER};
    public static void main(String[] args) throws IOException {
        
    }
    
    public static void buildFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File("src/test/resources/testingData.txt")));
        String line;
        Type type = Type.OTHER;
        Author last = null;
        Work work = null;
        while((line = br.readLine())!= null) {
            if(line.startsWith("##")) {
                if(line.contains("USERS")) type = Type.USER;
                if(line.contains("AUTHORS")) type = Type.AUTHOR;
            } else {
                switch(type) {
                case USER: insertUser(line);break;
                case AUTHOR: 
                    if(line.startsWith("#")) {
                        if(line.startsWith("#work")) {
                            work = insertWork(last, line);
                        } else {
                            if(work == null) continue;
                            
                        }
                    } else {
                        last = insertAuthor(line);
                    }
                
                break;
                default: ;;
                } 
            }
        }
        br.close();
    }
    
    public static void insertUser(String line) {
        
    }
    
    public static Author insertAuthor(String line) {
        Author author = new Author(line);
        return DAO.put(author);
    }
    
    public static Work insertWork(Author author, String line) {
        String[] data = line.split(" ");
        if(data.length < 2) return null;
        String[] workInfo = data[1].split("~");
        if(workInfo.length < 2) return null;
        String title = workInfo[0];
        Language lang = DAO.getLanguageByCode(workInfo[1]);
        if(lang == null) return null;
        Work work = new Work(author, title, lang);//Author, title, language
        DAO.put(work);
        Book book = new Book(work);
        DAO.put(book);
        return work;
    }
    
    public static void insertBook(Work work, String line) {
        String[] data = line.split(" ");
        if(data.length < 2) return;
        String[] bookInfo = data[1].split("~");
        if(bookInfo.length < 2) return;
        String title = bookInfo[0];
        Language lang = DAO.getLanguageByCode(bookInfo[1]);
        if(lang == null) return;
        Book book = new Book(work, title, lang); //Work, title, lang
        DAO.put(book);
    }
}
