package amaralus.apps.rogue.entities.world;


import java.util.ArrayList;
import java.util.List;

import static amaralus.apps.rogue.generators.RandomGenerator.excRandInt;

public class Room extends Area {

    private LevelArea levelArea;
    private List<Corridor> corridors;
    private boolean darkRoom;
    private boolean exitRoom = false;

    public Room(List<List<Cell>> cells) {
        super(cells);
        corridors = new ArrayList<>();
    }

    @Override
    public Cell getRandCell() {
        return getCell(excRandInt(0, getWidth() - 1), excRandInt(0, getHeight() - 1));
    }

    @Override
    public void destroy() {
        levelArea = null;
        corridors.clear();
    }

    public LevelArea getLevelArea() {
        return levelArea;
    }

    public void setLevelArea(LevelArea levelArea) {
        this.levelArea = levelArea;
    }

    public List<Corridor> getCorridors() {
        return corridors;
    }

    public void setCorridors(List<Corridor> corridors) {
        this.corridors = corridors;
    }

    public void addCorridor(Corridor corridor) {
        corridors.add(corridor);
    }

    public boolean isDarkRoom() {
        return darkRoom;
    }

    public void setDarkRoom(boolean darkRoom) {
        this.darkRoom = darkRoom;
    }

    public boolean isExitRoom() {
        return exitRoom;
    }

    public void setExitRoom(boolean exitRoom) {
        this.exitRoom = exitRoom;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
