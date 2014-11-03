package com.pd.api.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.pd.api.db.DAO;
import com.pd.api.entity.Book;

public class LuceneIndexer {
    
    public static final String INDEX_DIR = "./tmp/lucene_index";

    public static void index() throws IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);
        IndexWriter writer = new IndexWriter(FSDirectory.open(new File(INDEX_DIR)), config);
        
        writer.deleteAll();//index all book names
        List<Book> books = DAO.getAll(Book.class, 0, 0);
        for(Book book : books) {
            Document doc = new Document();
            doc.add(new TextField("title",book.getTitle(), Field.Store.YES));
            doc.add(new StringField("id", book.getId().toString(), Field.Store.YES));
            doc.add(new StringField("type", "BOOK", Field.Store.YES));
            writer.addDocument(doc);
        }
        writer.close();
    }
}
