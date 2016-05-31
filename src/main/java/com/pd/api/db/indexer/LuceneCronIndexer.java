package com.pd.api.db.indexer;

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

public class LuceneCronIndexer {

    public static final String INDEX_DIR = "./tmp/lucene_index";
    public static final String BOOK_TYPE = "BOOK";
    public static final String AUTHOR_TYPE = "AUTHOR";
    
    private static final int DEFAULT_INDEX_BATCH = 10000;
    private static final int INITIAL_INDEX_BATCH = 100000;
    
    private boolean reIndexFlag = false;
    
    public LuceneCronIndexer() { }
    
    public void setReIndexFlag() {
        reIndexFlag = true;
    }
    
    public void index() {
        index(DEFAULT_INDEX_BATCH);
    }
    
    public void index(int indexSize) {
        List<Book> booksToIndex = DAO.getBooksSinceLastIndex(indexSize);
        List<Author> authorsToIndex = DAO.getAuthorsSinceLastIndex(indexSize);
        if(!booksToIndex.isEmpty() || !authorsToIndex.isEmpty()){
            index(false, booksToIndex, authorsToIndex);
        }
    }
    
    public void reindexAll() {
        List<Book> booksToIndex = DAO.newBooksToIndex(new BookIndex(0L,0), INITIAL_INDEX_BATCH);
        List<Author> authorsToIndex = DAO.newAuthorsToIndex(new AuthorIndex(0L,0), INITIAL_INDEX_BATCH);
        index(true, booksToIndex, authorsToIndex);
    }
    
    public void index(boolean delete, List<Book> books, List<Author> authors) {
        try {
            IndexWriter index = getIndexWriter();
            if(delete)index.deleteAll();
            BookIndex bIndex = indexBooks(index, books);
            AuthorIndex aIndex = indexAuthors(index, authors);
            index.close();
            if(bIndex != null)DAO.put(bIndex);
            if(aIndex != null)DAO.put(aIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private IndexWriter getIndexWriter() throws IOException {
        return new IndexWriter(FSDirectory.open(new File(INDEX_DIR)), getIndexWriterConfig());
    }
    
    private IndexWriterConfig getIndexWriterConfig() {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        return new IndexWriterConfig(Version.LUCENE_40, analyzer);
    }
    
    private BookIndex indexBooks(IndexWriter writer, List<Book> books) throws IOException {
        if(books.isEmpty())return null;
        Long lastId = 0L;
        for(Book book : books) {
            Document doc = getDocumentFromBook(book);
            writer.addDocument(doc);
            lastId = book.getId();
        }
        return new BookIndex(lastId, books.size());
    }
    
    private Document getDocumentFromBook(Book book) {
        Document doc = new Document();
        doc.add(new TextField("name", book.getTitle(), Field.Store.YES));
        doc.add(new TextField("author", book.getWork().getAuthor().getName().toString(), Field.Store.YES));
        doc.add(new StringField("lang", book.getLanguage().getCode(), Field.Store.YES));
        doc.add(new LongField("id", book.getId(), Field.Store.YES));
        doc.add(new StringField("type", BOOK_TYPE, Field.Store.YES));
        return doc;
    }
    
    private AuthorIndex indexAuthors(IndexWriter writer, List<Author> authors) throws IOException {
        if(authors.isEmpty()) return null;
        Long lastId = 0L;
        for(Author author : authors) {
            Document doc = getDocumentFromAuthor(author);
            writer.addDocument(doc);
            lastId = author.getId();
        }
        return new AuthorIndex(lastId, authors.size());
    }
    
    public Document getDocumentFromAuthor(Author author) {
        Document doc = new Document();
        doc.add(new TextField("name", author.getName(), Field.Store.YES));
        doc.add(new LongField("id", author.getId(), Field.Store.YES));
        doc.add(new StringField("type", AUTHOR_TYPE, Field.Store.YES));
        return doc;
    }
}
