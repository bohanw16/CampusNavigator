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

package pathfinder;

import graph.Graph;

import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;

import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

/**
 * This represents a campus map built with data parsed from campus_buildings.csv and campus_paths.csv.
 * It allows clients to check for buildings' shortnames, get information of short names and long names
 *      of the buildings, and find the shortest path from one location to the other.
 */
public class CampusMap implements ModelAPI {

    // AF(this) =
    //      a campus map represented by graph, in which each Point is a location and
    //          each Double is the distance between two buildings
    //      building data are stored in campusBuildings, in which key=building's short name and
    //          value=building that has x,y coordinates, short name and long name
    //      path data are stored in campusPaths, in which each element represents a path that
    //          has x,y coordinates of its two end points and its distance
    //      DEBUG is used to faster the running time when DEBUG == false

    // Rep Invariant:
    //      graph != null
    //      campusBuildings != null
    //      campusPaths != null
    //      Every key and entry in campusBuildings != null
    //      Every entry in campusPath != null
    //      All coordinates of the buildings and paths are non-negative
    //      Each building is unique
    //      Each outgoing path from the same building is unique

    private Graph<Point, Double> graph;
    private Map<String, CampusBuilding> campusBuildings;
    private List<CampusPath> campusPaths;
    private final boolean DEBUG = false;

    /**
     * Throws an exception if the Rep Invariant is violated
     */
    private void checkRep() {
        assert (graph != null) : "graph is null";
        assert (campusBuildings != null) : "campusBuildings is null";
        assert (campusPaths != null) : "campusPaths is null";

        if (DEBUG) {
            // for CampusBuilding elements
            Set<String> buildingNames = campusBuildings.keySet();
            for (String buildingName : buildingNames) {
                assert (buildingName != null) : "key of campusBuildings is null";
                CampusBuilding building = campusBuildings.get(buildingName);
                assert (building != null) : "value of campusBuildings is null";
                assert (building.getX() >= 0) : "x of a campusBuilding is negative";
                assert (building.getY() >= 0) : "y of a campusBuilding is negative";
            }
            // for CampusPath entries
            for (CampusPath path : campusPaths) {
                assert (path != null) : "entry of campusPath is null";
                assert (path.getX1() >= 0) : "x1 of a CampusPath is negative";
                assert (path.getX2() >= 0) : "x2 of a CampusPath is negative";
                assert (path.getY1() >= 0) : "y1 of a CampusPath is negative";
                assert (path.getY2() >= 0) : "y2 of a CampusPath is negative";
            }
        }
    }

    /**
     * @spec.effects construct an CampusMap with data parsed from campus_buildings.csv and campus_paths.csv
     */
    public CampusMap() {
        graph = new Graph<>();
        campusBuildings = new HashMap<>();
        for (CampusBuilding building : CampusPathsParser.parseCampusBuildings("campus_buildings.csv")) {
            campusBuildings.put(building.getShortName(), building);
        }
        campusPaths = CampusPathsParser.parseCampusPaths("campus_paths.csv");

        for (CampusPath path : campusPaths) {
            Point p1 = new Point(path.getX1(), path.getY1());
            Point p2 = new Point(path.getX2(), path.getY2());
            graph.addNode(p1);
            graph.addNode(p2);
            graph.addEdge(p1, p2, path.getDistance());
        }

        checkRep();
    }

    @Override
    public boolean shortNameExists(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        checkRep();
        boolean res = campusBuildings.containsKey(shortName);
        checkRep();
        return res;
    }

    @Override
    public String longNameForShort(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        checkRep();
        CampusBuilding building = campusBuildings.get(shortName);
        if (building == null) {
            throw new IllegalArgumentException("short name non-existing");
        }
        String res = building.getLongName();
        checkRep();
        return res;
    }

    @Override
    public Map<String, String> buildingNames() {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        checkRep();
        Map<String, String> res = new HashMap<>();
        for (String shortName : campusBuildings.keySet()) {
            res.put(shortName, campusBuildings.get(shortName).getLongName());
        }
        checkRep();
        return res;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        checkRep();
        if (startShortName == null || endShortName == null ||
                !shortNameExists(startShortName) || !shortNameExists(endShortName)) {
            throw new IllegalArgumentException("short name is null or non-existing");
        }
        CampusBuilding startBuilding = campusBuildings.get(startShortName);
        CampusBuilding endBuilding = campusBuildings.get(endShortName);
        Point start = new Point(startBuilding.getX(), startBuilding.getY());
        Point end = new Point(endBuilding.getX(), endBuilding.getY());

        checkRep();
        return Dijkstra.findShortestPath(graph, start, end);
    }

}
