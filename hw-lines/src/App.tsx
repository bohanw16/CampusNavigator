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

import React, { Component } from "react";
import EdgeList from "./EdgeList";
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
  edges : string[][]
}

class App extends Component<{}, AppState> { // <- {} means no props.

  constructor(props: any) {
    super(props);
    this.state = {
      // TODO: store edges in this state
      edges : []
    };
  }

  render() {
    return (
      <div>
        <h1 id="app-title">Line Mapper!</h1>
        <div>
          {/* TODO: define props in the Map component and pass them in here */}
          <Map edges={this.state.edges}/>
        </div>
        <EdgeList
          onChange={(value) => {
            // TODO: Modify this onChange callback to store the edges in the state
            // console.log("EdgeList onChange", value);
            this.updateEdges(value);
          }}
        />
      </div>
    );
  }

  updateEdges(value : string) {
    if (value.length === 0) {
        console.log("empty edge list");
        this.setState({edges : []});
        return;
    }
    let lines : string[] = value.split("\n");
    let newEdgeList : string[][] = [];
    let invalid : boolean = false;
    for (let i = 0; i < lines.length; i++) {
        let line : string[] = lines[i].split(" ");
        if (line.length !== 5) {
            invalid = true;
            break;
        }
        for (let j = 0; j < 4; j++) {
            if (!Number(line[j]) || parseInt(line[j]) < 0 || parseInt(line[j]) > 4000) {
                invalid = true;
                break;
            }
        }
        newEdgeList[i] = line;
    }
    if (invalid) {
        alert("Invalid Input");
    } else {
        console.log(newEdgeList);
        this.setState({edges : newEdgeList});
    }
  }

}

export default App;
