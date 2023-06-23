/*
 * Copyright (C) 2023 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2023 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package campuspaths;

import campuspaths.utils.CORSFilter;

import com.google.gson.Gson;
import spark.*;
import pathfinder.CampusMap;
import pathfinder.Dijkstra;
import pathfinder.datastructures.*;
import java.util.*;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().
        CampusMap cm = new CampusMap();
        Set<String> buildings = cm.buildingNames().keySet();

        Spark.get("findPath", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startStr = request.queryParams("start");
                String endStr = request.queryParams("end");
                if (startStr == null || endStr == null) {
                    Spark.halt(400, "must have start and end");
                }

                Path<Point> shortestPath = null;
                try {
                    shortestPath = cm.findShortestPath(startStr, endStr);
                } catch (Exception e) {
                    Spark.halt(400, "invalid parameters");
                }

                return new Gson().toJson(shortestPath);
            }
        });

        Spark.get("listBuildings", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return new Gson().toJson(buildings);
            }
        });
    }

}
