package com.pd.api.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.pd.api.db.DAO;
import com.pd.api.entity.Author;
import com.pd.api.entity.Book;
import com.pd.api.exception.GeneralException;
import com.pd.api.util.LuceneIndexer;
import com.pd.api.util.search.LuceneSearcher;

public class SearchServiceImplementation {
    
    private static StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);

    /**
     * 
     * @param queryString
     * @param start
     * @param limit
     * @return
     * @throws IOException
     */
    public static List<Book> searchBooks(String queryString, int start, int limit) {
        List<Book> books = new LinkedList<Book>();
        try {
            Query query = new QueryParser(Version.LUCENE_40, "title", analyzer).parse(queryString);
            IndexReader reader = IndexReader.open(FSDirectory.open(new File(LuceneIndexer.INDEX_DIR)));
            IndexSearcher searcher = new IndexSearcher(reader);
            int numHits = start + limit > 0 ? limit : 1;
            TopScoreDocCollector collector = TopScoreDocCollector.create(numHits, true);
            searcher.search(query, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            for(int i = start; i < hits.length; i++) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                Book book = DAO.get(Book.class, Long.parseLong(d.get("id"),10));
                books.add(book);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new GeneralException("The query could not be parsed");
        } catch (IOException e) {
            e.printStackTrace();
            throw new GeneralException("The index could not be read");
        }
        return books;
    }
    
    /*
    private IndexSearcher searcher = null;
    private QueryParser parser = null;
    public SearchEngine() throws IOException {
        searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(new File("index-directory"))));
        parser = new QueryParser("content", new StandardAnalyzer());
    }

    public TopDocs performSearch(String queryString, int n)
    throws IOException, ParseException {
        Query query = parser.parse(queryString);
        return searcher.search(query, n);
    }

    public Document getDocument(int docId)
    throws IOException {
        return searcher.doc(docId);
    }
    */
    public static List<Object> findAnything(String queryString, int start, int limit) {
        List<Object> results = new LinkedList<Object>();
        try {
            LuceneSearcher searcher = new LuceneSearcher();
            List<Document> docs = searcher.search(queryString, start, limit);
            for(int i = 0; i < docs.size(); i++) {
                Document d = docs.get(i);
                Object o = getObjectFromDocument(d);
                if(o != null )results.add(o);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new GeneralException("The query could not be parsed");
        } catch (IOException e) {
            e.printStackTrace();
            throw new GeneralException("The index could not be read");
        }
        return results;
    }
    
    private static Object getObjectFromDocument(Document doc) {
        String type = doc.get("type");
        long id = doc.getField("id").numericValue().longValue();
        if(LuceneIndexer.BOOK_TYPE.equals(type)) {
            return DAO.get(Book.class, id);
        }
        if(LuceneIndexer.AUTHOR_TYPE.equals(type)) {
            return DAO.get(Author.class, id);
        }
        return null;
    }
    
    public static List<Author> searchAuthors(String queryString, int start, int limit) {
        List<Author> authors = new LinkedList<Author>();
        try {
            Query query = new QueryParser(Version.LUCENE_40, "name", analyzer).parse(queryString);
            IndexReader reader = IndexReader.open(FSDirectory.open(new File(LuceneIndexer.INDEX_DIR)));
            IndexSearcher searcher = new IndexSearcher(reader);
            int numHits = start + limit > 0 ? limit : 1;
            TopScoreDocCollector collector = TopScoreDocCollector.create(numHits, true);
            searcher.search(query, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            for(int i = start; i < hits.length; i++) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                Author author = DAO.get(Author.class, Long.parseLong(d.get("id"),10));
                authors.add(author);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new GeneralException("The query could not be parsed");
        } catch (IOException e) {
            e.printStackTrace();
            throw new GeneralException("The index could not be read");
        }
        return authors;
    }
}
