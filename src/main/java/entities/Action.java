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
    private String action;
    private int time;

    // json field names
    public static final String FIELD_ACTION = "action";
    public static final String FIELD_TIME = "time";

    // make one
    public Action(String action, int time) {
        this.action=action;
        this.time=time;
    }

    // default constructor for Jackson
    public Action() {
        super();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
        sb.append("action='").append(action).append('\'');
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
