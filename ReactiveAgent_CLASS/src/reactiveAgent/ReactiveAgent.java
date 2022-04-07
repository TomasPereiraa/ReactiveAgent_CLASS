package reactiveAgent;

import java.awt.Color;

public class ReactiveAgent implements Agent {

    private Cell cell;

    public ReactiveAgent(Cell cell) {
        this.cell = cell;
        this.cell.setAgent(this);
    }

    public void act(Environment environment) {
        Perception perception = buildPerception(environment);
        Action action = decide(perception);
        execute(action, environment);
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell.setAgent(null);
        this.cell = cell;
        this.cell.setAgent(this);
    }

    public Color getColor() {
        return Color.black;
    }

    private Perception buildPerception(Environment environment) {
        return new Perception(
                environment.getNorthCell(cell),
                environment.getSouthCell(cell),
                environment.getEastCell(cell),
                environment.getWestCell(cell));
    }

    //Modify according to the exerciseX\
    private Action decide(Perception perception) {
        //return decide_a(perception);
        //return decide_b(perception);
        //return decide_c(perception);
        return decide_d(perception);
    }

    private void execute(Action action, Environment environment) {

        Cell nextCell = null;

        if (action == Action.NORTH && environment.hasNorthCell(cell)) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH && environment.hasSouthCell(cell)) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST && environment.hasWestCell(cell)) {
            nextCell = environment.getWestCell(cell);
        } else if (action == Action.EAST && environment.hasEastCell(cell)) {
            nextCell = environment.getEastCell(cell);
        }

        if (nextCell != null && !nextCell.hasWall()) {
            setCell(nextCell);
        }
    }

    private Action decide_a(Perception perception) {

        // TODO
        // Implement the decision process of a basic version of the reactive agent,
        // which should simply wander around the world avoiding hitting the walls.
        // This agent doesn’t care about dirt, yet (but it always cleans visited
        // cells on this and the next exercises).

        if(perception.getE()!=null && !perception.getE().hasAgent()) {
            return Action.EAST;
        }
        if(perception.getN()!=null && !perception.getN().hasAgent()) {
            return Action.NORTH;
        }
        if(perception.getS()!=null && !perception.getS().hasAgent()) {
            return Action.SOUTH;
        }
        if(perception.getW()!=null && !perception.getW().hasAgent()) {
            return Action.WEST;
        }
        return Action.WEST;
    }

    private Action decide_b(Perception perception) {

        // TODO
        // The agent should now be able to perceive dirt in adjacent
        // cells and use that information in its decision process.
        // This agent prefers to visit dirty cells first.
        if(perception.getE()!=null && !perception.getE().hasAgent() && perception.getE().isDirty()){
            return Action.EAST;
        }
        if(perception.getN()!=null && !perception.getN().hasAgent() && perception.getN().isDirty()){
            return Action.NORTH;
        }
        if(perception.getS()!=null && !perception.getS().hasAgent() && perception.getS().isDirty()){
            return Action.SOUTH;
        }
        if(perception.getW()!=null && !perception.getW().hasAgent() && perception.getW().isDirty()){
            return Action.WEST;
        }
        return Action.WEST;
    }

    private Action decide_c(Perception perception) {
        int LastInt=Integer.MAX_VALUE;
        Action returnaValor = null;

        if(perception.getE()!=null && !perception.getE().hasWall()){
            LastInt=perception.getE().getLastIteration();
            returnaValor=Action.EAST;
        }
        if(perception.getN()!=null && !perception.getN().hasWall()){
            if(perception.getN().getLastIteration() <= LastInt){
                LastInt=perception.getN().getLastIteration();
                returnaValor=Action.NORTH;
            }
        }
        if(perception.getS()!=null && !perception.getS().hasWall()){
            if(perception.getS().getLastIteration() <= LastInt){
                LastInt=perception.getS().getLastIteration();
                returnaValor=Action.SOUTH;
            }
        }
        if(perception.getW()!=null && !perception.getW().hasWall()){
            if(perception.getW().getLastIteration() <= LastInt){
                LastInt=perception.getW().getLastIteration();
                returnaValor=Action.WEST;
            }
        }
        return returnaValor;
    }

    private Action decide_d(Perception perception) {

        // TODO
        // Finally, implement a decision process in which the agent, besides
        // preferring to visit dirty cells first, it prefers to visit cells that
        // were visited the longest. It should consider the second criterium as a
        // tiebreaker, when more than one adjacent cell is dirty or if there are
        // no dirty adjacent cells.
        int LastInt=Integer.MAX_VALUE;
        Action returnaValor = null;

        if(perception.getE()!=null && perception.getE().isDirty()){
            LastInt=perception.getE().getLastIteration();
            returnaValor=Action.EAST;
        }
        if(perception.getN()!=null && perception.getN().isDirty()){
            if(perception.getN().getLastIteration() <= LastInt){
                LastInt=perception.getN().getLastIteration();
                returnaValor=Action.NORTH;
            }
        }
        if(perception.getS()!=null && perception.getS().isDirty()){
            if(perception.getS().getLastIteration() <= LastInt){
                LastInt=perception.getS().getLastIteration();
                returnaValor=Action.SOUTH;
            }
        }
        if(perception.getW()!=null && perception.getW().isDirty()){
            if(perception.getW().getLastIteration() <= LastInt){
                LastInt=perception.getW().getLastIteration();
                returnaValor=Action.WEST;
            }
        }
        if(returnaValor != null) {
            return returnaValor;
        }
        return decide_c(perception);
    }
}