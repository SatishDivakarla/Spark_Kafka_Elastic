package com.useractivity.model;

import java.io.Serializable;

/**
 * Quadrant model class
 * Created by SatishDivakarla on 4/24/15.
 */
public class Quadrant implements Serializable{

    /**
     * X-Coordinate
     */
    private int coordinateX;
    /**
     * Y-Coordinate
     */
    private int coordinateY;
    /**
     * Timestamp in which an activity occured
     */
    private long timestamp;
    /**
     * Quadrant Id
     */
    private int id;

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
