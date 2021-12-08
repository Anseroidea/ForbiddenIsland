package player;

public class Action {

    private String action;
    private boolean counts;

    public Action(String action, boolean counts) {
        this.action = action;
        this.counts = counts;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isCounts() {
        return counts;
    }

    public void setCounts(boolean counts) {
        this.counts = counts;
    }
}
