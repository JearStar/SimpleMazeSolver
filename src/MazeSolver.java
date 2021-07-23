import java.util.ArrayList;
import java.util.List;

public class MazeSolver {
    int[][] maze;
    Position startingPosition;
    List<Position> directions;

    public MazeSolver(int[][] maze) {
        this.maze = maze;
        startingPosition = getStartingPosition(maze);
        directions = new ArrayList<>();
    }

    //EFFECTS: solves maze by printing out coordinates of path to console
    public void solveMaze() {
        List<Position> path = new ArrayList<>();
        List<Position> posWorklist = new ArrayList<>();
        List<List<Position>> pathWorklist = new ArrayList<>();
        solvePos(startingPosition, path, posWorklist, pathWorklist);
        if (directions.isEmpty()) {
            System.out.println("No Solution");
        } else {
            for (Position p : directions) {
                System.out.println("(" + p.getX() + ", " + p.getY() + ")");
            }
        }
    }

    //MODIFIES: directions
    //EFFECTS: solve function for position
    public boolean solvePos(Position pos, List<Position> path, List<Position> posWorklist,
                            List<List<Position>> pathWorklist) {
        if (isSolved(pos)) {
            path.add(pos);
            if (directions.isEmpty() || path.size() < directions.size()) {
                directions = path;
            }
            return true;
        } else {
            path.add(pos);
            List<Position> nextMoves = getNextMoves(pos, path);
            for (Position p : nextMoves) {
                pathWorklist.add(path);
            }
            posWorklist.addAll(nextMoves);
            return solveLOP(posWorklist, pathWorklist);
        }
    }

    //MODIFIES: posWorklist, pathWorklist
    //EFFECTS: solve function for list of position
    public boolean solveLOP(List<Position> posWorklist, List<List<Position>> pathWorklist) {
        if (posWorklist.isEmpty()) {
            return false;
        } else {
            Position firstPos = posWorklist.get(posWorklist.size() - 1);
            List<Position> firstPath = pathWorklist.get(pathWorklist.size() - 1);
            posWorklist.remove(posWorklist.size() - 1);
            pathWorklist.remove(pathWorklist.size() - 1);
            return solvePos(firstPos, firstPath, posWorklist, pathWorklist);
        }
    }

    //EFFECTS: returns true if position is at destination
    public boolean isSolved(Position position) {
        return maze[position.getY()][position.getX()] == 4;
    }

    //EFFECTS: retrieves and returns the next valid moves
    public List<Position> getNextMoves(Position position, List<Position> path) {
        List<Position> nextMoves = new ArrayList<>();
        Position up = new Position();
        Position down = new Position();
        Position left = new Position();
        Position right = new Position();

        up.setX(position.getX());
        up.setY(position.getY() - 1);

        down.setX(position.getX());
        down.setY(position.getY() + 1);

        left.setX(position.getX() - 1);
        left.setY(position.getY());

        right.setX(position.getX() + 1);
        right.setY(position.getY());

        List<Position> temp = new ArrayList<>();

        temp.add(up);
        temp.add(down);
        temp.add(left);
        temp.add(right);


        for (Position pos : temp) {
            if (isValidMove(pos, path)) {
                nextMoves.add(pos);
            }
        }
        return nextMoves;
    }


    //EFFECTS: returns true if position is a valid move
    public boolean isValidMove(Position pos, List<Position> path) {
        return isNotOutOfBounds(pos) && isNotInWall(pos) && isNotInPath(pos, path);
    }

    //EFFECTS: returns true if a position is not out of bounds
    public boolean isNotOutOfBounds(Position pos) {
        return pos.getY() >= 0 && pos.getY() < maze.length && pos.getX() >= 0 && pos.getX() < maze[pos.getY()].length;
    }

    //EFFECTS: returns true if position is not in a wall
    public boolean isNotInWall(Position pos) {
        return maze[pos.getY()][pos.getX()] != 0;
    }

    //EFFECTS: returns true if position has not been visited yet
    public boolean isNotInPath(Position pos, List<Position> visited) {
        for (Position p : visited) {
            if (pos.getX() == p.getX() && pos.getY() == p.getY()) {
                return false;
            }
        }
        return true;
    }


    //REQUIRES: only one starting position on maze
    //EFFECTS: finds the starting position on the maze
    public Position getStartingPosition(int[][] maze) {
        Position startingPosition = new Position();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 3) {
                    startingPosition.setY(i);
                    startingPosition.setX(j);
                    break;
                }

            }
        }
        return startingPosition;
    }


}
