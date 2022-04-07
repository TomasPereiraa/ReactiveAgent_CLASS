package reactiveAgent;

import java.awt.Color;

public class Cell {
    private final int line, column;
    private ReactiveAgent agent;
    private boolean hasWall;
    private boolean dirty;
    private int lastIteration;

    public Cell(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public Color getColor() {
        if (hasAgent()) {
            return agent.getColor();
        }
        if (hasWall()) {
            return Color.BLUE;
        }
        if (dirty) {
            return Color.LIGHT_GRAY;
        }
        return Color.WHITE;
    }

    public boolean hasAgent() {
        return agent != null;
    }

    public ReactiveAgent getAgent() {
        return agent;
    }

    public void setAgent(ReactiveAgent agent) {
        this.agent = agent;
        if(hasAgent()){
            if(isDirty()) {
                removeDirt();
            }
            lastIteration=Environment.currentIteration+1;
        }
    }

    public boolean hasWall() {
        return hasWall;
    }

    public void addWall() {
        this.hasWall = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void addDirt() {
        this.dirty = true;
    }

    public void removeDirt() {
        this.dirty = false;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getLastIteration() {
        return lastIteration;
    }
}