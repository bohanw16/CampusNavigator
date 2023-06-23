/*
 * Copyright (C) 2022 Kevin Zatloukal and James Wilcox.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import SelectBox from "./SelectBox";
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    edges : string[]
}

class App extends Component<{}, AppState> {

    constructor(props: any) {
        super(props);
        this.state = {
            edges : []
        };
    }

    render() {
//         return (
//             <p>Here's the beginning of your AMAZING CampusPaths GUI!</p>
//         );
        return (
            <div>
                <h1 id="app-title">UW campus Map</h1>
                <div>
                    <Map edges={this.state.edges}/>
                </div>
                <SelectBox
                    draw={(start, end) => {
                        this.drawEdges(start, end);
                    }}
                />
            </div>
        );
    }

    async drawEdges(start : string, end : string) {
        try {
            console.log("fetching path from " + start + " to " + end);
            if(start.length === 0 || end.length === 0) {
                console.log("empty edge list");
                this.setState({edges : []});
                return;
            }
            let res = await fetch("http://localhost:4567/findPath?start=" + start + "&end=" + end);
            if (!res.ok) {
                alert("FETCH ERROR - Status: " + res.status);
                return;
            }
            let json = await res.json();
            let path = json["path"];
            let newEdgeList : string[] = [];
            // path => [segment, segment ...]
            // segment => {"start": point, "end": point, cost: double}
            // point => {"x": double, "y": double}
            for(let i = 0; i < path.length; i++) {
                let startPoint = path[i]["start"];
                let endPoint = path[i]["end"];
                let line = "" + parseFloat(startPoint["x"]) + " " + parseFloat(startPoint["y"]) + " " +
                            parseFloat(endPoint["x"]) + " " + parseFloat(endPoint["y"]) + " red";
                newEdgeList[i] = line;
            }
            this.setState({edges : newEdgeList});
        } catch(e) {
            alert("Server Error");
            console.log(e);
        }
    }

}

export default App;
