package com.pd.api.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.pd.api.db.DAO;
import com.pd.api.entity.Author;
import com.pd.api.entity.Book;

public class LuceneIndexer {
    
    public static final String INDEX_DIR = "./tmp/lucene_index";
    public static final String BOOK_TYPE = "BOOK";
    public static final String AUTHOR_TYPE = "AUTHOR";
    
    private static LuceneIndexer instance = null;
    
    static {
        System.out.print("Indexing DB...");
        instance = new LuceneIndexer();
        long startTime = System.nanoTime();
        try {
            instance.buildIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        System.out.println("Indexing took [" + (endTime - startTime)/1000000 + "] miliseconds");
    }
    
    private IndexWriterConfig getIndexWriterConfig() {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        return new IndexWriterConfig(Version.LUCENE_40, analyzer);
    }
    
    private LuceneIndexer() {
        //TODO: set up the indexer
    }
    
    public static LuceneIndexer getInstance() {
        if(instance == null) {
            instance = new LuceneIndexer();
        }
        return instance;
    }

    private void buildIndex() throws IOException {
        IndexWriter index = getIndexWriter();
        index.deleteAll();
        indexBooks(index);
        indexAuthors(index);
        index.close();
    }
    
    private IndexWriter getIndexWriter() throws IOException {
        return new IndexWriter(FSDirectory.open(new File(INDEX_DIR)), getIndexWriterConfig());
    }
    
    public void indexBook(Book book) {
        //TODO: append book to object to clear
        /*
        try {
            IndexWriter writer = getBookIndexWriter();
            Document doc = getDocumentFromBook(book);
            writer.addDocument(doc);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: put code here
        }*/
    }
    
    public void indexAuthor(Author author) {
        //TODO: same as above
        /*
        try {
            IndexWriter writer = getAuthorIndexWriter();
            Document doc = getDocumentFromAuthor(author);
            writer.addDocument(doc);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: crazy stuff here
        }*/
    }
    
    private Document getDocumentFromBook(Book book) {
        Document doc = new Document();
        doc.add(new TextField("name", book.getTitle(), Field.Store.YES));
        doc.add(new TextField("author", book.getWork().getAuthorsNames(), Field.Store.YES));
        doc.add(new StringField("lang", book.getLanguage().getCode(), Field.Store.YES));
        doc.add(new LongField("id", book.getId(), Field.Store.YES));
        doc.add(new StringField("type", BOOK_TYPE, Field.Store.YES));
        return doc;
    }
    
    public Document getDocumentFromAuthor(Author author) {
        Document doc = new Document();
        doc.add(new TextField("name", author.getName(), Field.Store.YES));
        doc.add(new LongField("id", author.getId(), Field.Store.YES));
        doc.add(new StringField("type", AUTHOR_TYPE, Field.Store.YES));
        return doc;
    }
    
    private void indexBooks(IndexWriter writer) throws IOException {
        
        List<Book> books = DAO.getAll(Book.class, 0, 0);
        for(Book book : books) {
            Document doc = getDocumentFromBook(book);
            writer.addDocument(doc);
        }
    }
    
    private void indexAuthors(IndexWriter writer) throws IOException {
        
        List<Author> authors = DAO.getAll(Author.class, 0, 0);
        for(Author author : authors) {
            Document doc = getDocumentFromAuthor(author);
            writer.addDocument(doc);
        }
    }
    
    /*
    Lucene Documentation
    
    A text field is a sequence of terms that has been tokenized while a string 
    field is a single term (although it can also be multivalued.)
    
    Punctuation and spacing is ignored for text fields. Text tends to be 
    lowercased, stemmed, and even stop words removed. You tend to search text 
    using a handful of keywords whose exact order is not required, although 
    quoted phrases can be used as well. Fuzzy queries can be done on individual 
    terms (words). Wildcards as well.
    
    String fields are literal character strings with all punctuation, spacing, 
    and case preserved. Anything other than exact match is done using wildcards, 
    although I suppose fuzzy query should work as well.
    
    String fields are useful for facets and filter queries or display.
    
    Text fields are useful for keyword search.
     */
}
