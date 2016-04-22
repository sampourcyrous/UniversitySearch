package universitysearch;

/**
 * Created by zubairbaig on 3/24/16.
 */
public class Tags {
    public String text;
    public int id, fileId;

    public Tags() {
    }

    public Tags(String text, int fileId) {
        this.text = text;
        this.fileId = fileId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
