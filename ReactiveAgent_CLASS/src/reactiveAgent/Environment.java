package reactiveAgent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Environment {

    private final Cell[][] grid;
    private final ReactiveAgent agent;
    private final int numIterations;
    static public int currentIteration;

    private Random random = new Random();
    final private double dirtRate = 0.0001;

    public Environment(int numLines, int numColumns, int numIterations) {
        this.numIterations = numIterations;
        this.grid = new Cell[numLines][numColumns];
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[0].length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        this.grid[2][2].addWall();
        this.grid[2][3].addWall();
        this.grid[2][4].addWall();
        this.grid[5][2].addWall();

        this.agent = new ReactiveAgent(getCell(6, 3));

        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[0].length; j++) {
                if(!this.grid[i][j].hasAgent() && !this.grid[i][j].hasWall()){
                    this.grid[i][j].addDirt();
                }
            }
        }
    }

    public void run() {
        for (currentIteration = 0; currentIteration < numIterations; currentIteration++) {
            dirtUpdate();
            agent.act(this);
            fireUpdatedEnvironment();
        }
    }


    private void dirtUpdate(){
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[0].length; j++) {
                if(!this.grid[i][j].hasAgent() && !this.grid[i][j].hasWall() && !this.grid[i][j].isDirty()){
                    int iterationsSinceLastVisit = currentIteration - this.grid[i][j].getLastIteration();
                    double dirtProbability = iterationsSinceLastVisit * dirtRate;
                    if(random.nextDouble() < dirtProbability){
                        this.grid[i][j].addDirt();
                    }
                }
            }
        }
    }

    public Cell getNorthCell(Cell cell) {
        return (cell.getLine() > 0)? grid[cell.getLine() - 1][cell.getColumn()] : null;
    }

    public boolean hasNorthCell(Cell cell){
        return getNorthCell(cell) != null;
    }

    public Cell getSouthCell(Cell cell) {
        return (cell.getLine() < grid.length - 1)? grid[cell.getLine() + 1][cell.getColumn()] : null;
    }

    public boolean hasSouthCell(Cell cell){
        return getSouthCell(cell) != null;
    }

    public Cell getEastCell(Cell cell) {
        return (cell.getColumn() < grid[0].length - 1)? grid[cell.getLine()][cell.getColumn() + 1] : null;
    }

    public boolean hasEastCell(Cell cell){
        return getEastCell(cell) != null;
    }

    public Cell getWestCell(Cell cell) {
        return (cell.getColumn() > 0)? grid[cell.getLine()][cell.getColumn() - 1] : null;
    }

    public boolean hasWestCell(Cell cell){
        return getWestCell(cell) != null;
    }

    public int getNumLines() {
        return grid.length;
    }

    public int getNumColumns() {
        return grid[0].length;
    }

    public final Cell getCell(int line, int column) {
        return grid[line][column];
    }

    public Color getCellColor(int line, int column) {
        return grid[line][column].getColor();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Environment:\n");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].hasWall()) {
                    s.append('W');
                } else if (grid[i][j].hasAgent()) {
                    s.append('A');
                }  else if (grid[i][j].isDirty()) {
                    s.append('D');
                }else {
                    s.append('.');
                }
            }
            s.append('\n');
        }
        return s.toString();
    }

    //listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<EnvironmentListener>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }
}
