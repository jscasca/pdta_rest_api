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
import com.pd.api.entity.Book;
import com.pd.api.util.LuceneIndexer;

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
    public static List<Book> searchBooks(String queryString, int start, int limit) throws IOException {
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return books;
    }
}
