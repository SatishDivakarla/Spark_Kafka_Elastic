package com.useractivity.spark.streaming;

import com.useractivity.model.Quadrant;
import org.apache.spark.api.java.function.Function;
import scala.Int;

import java.util.regex.Pattern;

/**
 * Created by SatishDivakarla on 4/24/15.
 */
public class QuadrantCreator {

    static class CoordinatesToQuadrant implements Function<String, Quadrant> {
        private static final Pattern COMMA = Pattern.compile(",");

        @Override
        public Quadrant call(String line) {
            String[] tok = COMMA.split(line);
            int coordinateY = Integer.parseInt(tok[2]);
            int coordinateX = Integer.parseInt(tok[1]);
            Quadrant quadrant = new Quadrant();
            quadrant.setTimestamp(Long.parseLong(tok[0]));
            quadrant.setCoordinateX(coordinateX);
            quadrant.setCoordinateY(coordinateY);
            int id = getQuadrant(coordinateX,coordinateY);
            quadrant.setId(id);
            return quadrant;
        }

        private int getQuadrant(int coordinateX, int coordinateY) {
            if (coordinateX >= 0 && coordinateX<=700) {
                return coordinateY <= 400 ? 1 : 3;
            } else {
                return coordinateY <= 400 ? 2 : 4;
            }
        }
    }
}


