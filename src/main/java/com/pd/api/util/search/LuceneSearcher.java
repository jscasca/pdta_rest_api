package com.pd.api.util.search;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import com.pd.api.util.LuceneIndexer;

public class LuceneSearcher {

    private QueryParser parser = null;
    private IndexSearcher searcher = null;
    
    public static final String DEFAULT_FIELD = "name";
    
    public LuceneSearcher() throws IOException { this(DEFAULT_FIELD);}
    public LuceneSearcher(String field) throws IOException {
        parser = new QueryParser(field, new StandardAnalyzer());
        searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(new File(LuceneIndexer.INDEX_DIR))));
    }
    
    public List<Document> search(String queryString, int start, int limit) throws ParseException, IOException {
        List<Document> docs = new LinkedList<Document>();
        Query query = parser.parse(queryString);
        int numHits = start + limit > 0 ? limit : 1;
        TopScoreDocCollector collector = TopScoreDocCollector.create(numHits, true);
        searcher.search(query, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        for(int i = start; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document doc = searcher.doc(docId);
            docs.add(doc);
        }
        return docs;
    }
    /*
    public TopDocs performSearch(String queryString, int n)
    throws IOException, ParseException {
        Query query = parser.parse(queryString);
        return searcher.search(query, n);
    }
    public Document getDocument(int docId)
    throws IOException {
        return searcher.doc(docId);
    }
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
     */
}
