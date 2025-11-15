package project.evolution.entities.traits;

public enum Direction {
    N,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW;



    public Direction opposite(){
        return switch (this) {
            case N -> S;
            case NE -> SW;
            case E -> W;
            case SE -> NW;
            case S -> N;
            case SW -> NE;
            case W -> E;
            case NW -> SE;
        };
    }

    public Vector2d toUnitVector2d(){
        return switch (this) {
            case N -> new Vector2d(0,1);
            case NE -> new Vector2d(1,1);
            case E -> new Vector2d(1,0);
            case SE -> new Vector2d(1,-1);
            case S -> new Vector2d(0,-1);
            case SW -> new Vector2d(-1,-1);
            case W -> new Vector2d(-1,0);
            case NW -> new Vector2d(-1,1);
        };
    }

    public static Direction geneToDirection(int gene){
        return switch (gene){
            case 0 -> Direction.N;
            case 1 -> Direction.NE;
            case 2 -> Direction.E;
            case 3 -> Direction.SE;
            case 4 -> Direction.S;
            case 5 -> Direction.SW;
            case 6 -> Direction.W;
            case 7 -> Direction.NW;
            default -> throw new IllegalStateException("Unexpected value");
        };
    }

    public int directionToValue(){
        return switch (this){
            case N -> 0;
            case NE -> 1;
            case E -> 2;
            case SE -> 3;
            case S -> 4;
            case SW -> 5;
            case W -> 6;
            case NW -> 7;
        };
    }

    public Direction rotate(int rotation){
        return Direction.geneToDirection((this.directionToValue()+rotation)%8);
    }

    public boolean northDirection(){
        return this.equals(Direction.NW) || this.equals(Direction.N) || this.equals(Direction.NE);
    }

    public boolean southDirection(){
        return this.equals(Direction.SW) || this.equals(Direction.S) || this.equals(Direction.SE);
    }

}
