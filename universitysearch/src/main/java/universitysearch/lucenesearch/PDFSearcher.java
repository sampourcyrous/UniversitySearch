package universitysearch.lucenesearch;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by zubairbaig on 3/11/16.
 */
public class PDFSearcher {
    private static final String INDEX_DIR = System.getenv("OPENSHIFT_DATA_DIR" + "/index");

    public static IndexItem indexPDF(java.io.File file) throws IOException {
        PDDocument document = PDDocument.load(file);
        String content = new PDFTextStripper().getText(document);
        document.close();
        return new IndexItem((long)file.getName().hashCode(), file.getName(), content);
    }

    public static IndexItem indexTxt(java.io.File file) throws IOException {
        FileReader content = new FileReader(file);
        BufferedReader br = new BufferedReader(content);

        StringBuffer sb = new StringBuffer();

        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }

        return new IndexItem((long)file.getName().hashCode(), file.getName(), sb.toString());
    }
}
