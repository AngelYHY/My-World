package freestar.myapp;

/**
 * Created by freestar on 2016/12/14.
 * 生日实体类
 */

public class Birthday {

    public Birthday() {
    }

    public Birthday(String name, String time) {
        this.name = name;
        this.time = time;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
