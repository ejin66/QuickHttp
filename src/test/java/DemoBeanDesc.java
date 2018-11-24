/**
 * Created by ejin on 2018/3/27.
 */
public class DemoBeanDesc {

    private String name;
    private int id;
    private int order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "DemoBeanDesc{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", order=" + order +
                '}';
    }
}
