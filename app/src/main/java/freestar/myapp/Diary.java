package freestar.myapp;

/**
 * Created by freestar on 2016/12/14.
 */

public class Diary {
    public Diary() {
    }

    public Diary(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
