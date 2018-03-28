/**
 * Created by ejin on 2018/3/27.
 */
public class DemoBeanDesc {

    private String desc;
    private int id;
    private String title;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "DemoBeanDesc{" +
                "desc='" + desc + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
