package entities;

/**
 * Represents one action
 *
 * NOTE: there's no limitations on either action name or time
 * null/zero/negative are all accepted
 *
 */
public class Action {

    // privates
    private String name;
    private int time;

    // json field names
    public static final String FIELD_NAME = "action";
    public static final String FIELD_TIME = "time";

    // make one
    // avoid NPEs later with
    public Action(String name, int time) {
        this.name=name;
        this.time=time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Action{");
        sb.append("name='").append(name).append('\'');
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
