package universitysearch;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import universitysearch.lucenesearch.Searcher;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by zubairbaig on 3/12/16.
 */
public class MasterSearch extends DBManager{
    private static SessionFactory factory;
    SearchResult searchResult;

    Session session;

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }



    public String searchCoursesAndFiles(String query) {
        session = factory.openSession();
        searchResult = new SearchResult();

        Searcher searcher = null;
        try {
            searcher = new Searcher(Paths.get(System.getenv("OPENSHIFT_DATA_DIR") + "/index"));
            TopDocs results = searcher.findByContent(query, 10);
            if (results !=  null) {
                ScoreDoc[] hits = results.scoreDocs;

                Document document;
                Query queryObj = searcher.contentQueryParser.parse(query);
                QueryScorer queryScorer = new QueryScorer(queryObj);
                SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<mark><strong>", "</strong></mark>");
                SimpleHTMLEncoder htmlEncoder = new SimpleHTMLEncoder();
                Highlighter highlighter = new Highlighter(htmlFormatter, htmlEncoder, queryScorer);

                for (ScoreDoc hit : hits) {
                    document = searcher.searcher.doc(hit.doc);
                    String fileTitle = document.get("title");
                    String text = document.get("content");

                    String sql = "SELECT * FROM files WHERE name = :fileTitle";

                    SQLQuery sqlQuery = session.createSQLQuery(sql);
                    sqlQuery.setParameter("fileTitle", fileTitle);
                    sqlQuery.addEntity(File.class);
                    File file = (File) sqlQuery.uniqueResult();

                    TokenStream tokenStream = TokenSources.getTokenStream(searcher.indexReader, hit.doc, "content", searcher.analyzer);
                    try {
                        TextFragment[] textFragments = highlighter.getBestTextFragments(tokenStream, text, false, 1);
                        for (TextFragment textFragment : textFragments) {
                            if (textFragment != null && textFragment.getScore() > 0) {
                                file.setBlurb(textFragment.toString().replace("\n", " ").trim());
                            }
                        }
                    } catch (InvalidTokenOffsetsException e) {
                        e.printStackTrace();
                    }
                    searchResult.addFile(file);
                }
            }

            query = "%" + query + "%";
            String sql = "SELECT * FROM courses WHERE course_code LIKE :query";

            SQLQuery sqlQuery = session.createSQLQuery(sql);
            sqlQuery.addEntity(Course.class);
            sqlQuery.setParameter("query", query);
            List<Course> sqlResults = (List<Course>) sqlQuery.list();
            for (Course course : sqlResults) {
                searchResult.addCourse(course);
            }

            String tagSql = "FROM File where id IN (SELECT fileId FROM Tags t where t.text LIKE :query)";
            org.hibernate.Query tagQuery = session.createQuery(tagSql);
            tagQuery.setString("query", query + "%");
            List<File> files = tagQuery.list();
            for (File file : files) {
                searchResult.addFile(file);
            }
            return getJsonResultObj(searchResult);




        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getJsonResultObj(SearchResult searchResult) {
        String res = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            res = mapper.writeValueAsString(searchResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
