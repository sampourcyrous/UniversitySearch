package universitysearch.lucenesearch;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;

public class Searcher {

    public IndexSearcher searcher;
    public QueryParser contentQueryParser;
    public IndexReader indexReader;
    public StandardAnalyzer analyzer;

    public Searcher(Path indexDir) throws IOException {
        // open the index directory to search
        Directory directory = FSDirectory.open(indexDir);
        indexReader = DirectoryReader.open(directory);
        searcher = new IndexSearcher(indexReader);

        analyzer = new StandardAnalyzer();

        // defining the query parser to search items by content field.
//        contentQueryParser = new QueryParser(IndexItem.CONTENT, analyzer);
        contentQueryParser  = new MultiFieldQueryParser(
                new String[] {IndexItem.CONTENT, IndexItem.TITLE},
                analyzer);
    }


    /**
     * This method is used to find the indexed items by the content.
     *
     * @param queryString - the query string to search for
     */
    public TopDocs findByContent(String queryString, int numOfResults) throws ParseException, IOException {
        // create query from the incoming query string.
//        String fuzzySearchQueryString = queryString + "~";
//        String wildCardSearchQueryString = queryString + "*";

//        Query query = contentQueryParser.parse(fuzzySearchQueryString);
//        Query query = new FuzzyQuery(new Term("ContentText", queryString));
//        BooleanQuery.Builder finalQuery = new BooleanQuery.Builder();
//        finalQuery.add(query, BooleanClause.Occur.SHOULD);
//        Query query1 = new WildcardQuery(new Term("ContentText", queryString));
//        finalQuery.add(query1, BooleanClause.Occur.SHOULD);
//        contentQueryParser.
        Query query2 = contentQueryParser.parse(queryString);


        // execute the query and get the results
        BooleanQuery.Builder finalQuery = new BooleanQuery.Builder();
//        finalQuery.add(query, BooleanClause.Occur.SHOULD);
        finalQuery.add(query2, BooleanClause.Occur.SHOULD);
        TopDocs queryResults = searcher.search(finalQuery.build(), numOfResults);


        if (queryResults.totalHits > 0) {
            return queryResults;
        }
        else return null;

    }

//    public void close() throws IOException {
//        searcher.close();
//    }
}