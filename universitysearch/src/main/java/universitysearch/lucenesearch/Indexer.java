package universitysearch.lucenesearch;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;

public class Indexer {
    private IndexWriter writer;

    public Indexer(Path indexDir) throws IOException {
        // create the index
        if(writer == null) {
            writer = new IndexWriter(FSDirectory.open(indexDir), new IndexWriterConfig(new StandardAnalyzer()));
        }
    }

    /**
     * This method will add the items into index
     */
    public void index(IndexItem indexItem) throws IOException {

        // deleting the item, if already exists
        writer.deleteDocuments(new Term(IndexItem.ID, indexItem.getId().toString()));

        Document doc = new Document();

        doc.add(new TextField(IndexItem.ID, indexItem.getId().toString(), Field.Store.YES));
        doc.add(new TextField(IndexItem.TITLE, indexItem.getTitle(), Field.Store.YES));
        doc.add(new TextField(IndexItem.CONTENT, indexItem.getContent(), Field.Store.YES));

        // add the document to the index
        writer.addDocument(doc);
    }

    public void deleteDocuments(Query term) {
        try {
            writer.deleteDocuments(term);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closing the index
     */
    public void close() throws IOException {
        writer.close();
    }
}