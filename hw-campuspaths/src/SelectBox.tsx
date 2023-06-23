import React, {Component} from 'react';

interface SelectBoxProps {
    draw(start: string, end: string): void;
}

interface SelectBoxState {
    buildings : string[]
    start : string;
    end : string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class SelectBox extends Component<SelectBoxProps, SelectBoxState> {

    constructor(props : SelectBoxProps) {
        super(props);
        this.state = {
            buildings : [],
            start : "",
            end : ""
        }
        this.loadBuildings();
    }

    render() {
        let startOptions : any = [];
        let endOptions : any = [];
        startOptions.push(<option key={"default"} value={""}>--Please choose an option--</option>);
        endOptions.push(<option key={"default"} value={""}>--Please choose an option--</option>);
        for (let i = 0; i < this.state.buildings.length; i++) {
            let building : string = this.state.buildings[i];
            startOptions.push(<option key={building} value={building}> {building} </option>);
            endOptions.push(<option key={building} value={building}> {building} </option>);
        }

        return (
            <div id="selection">
            <br/>
            Find A Path
            <br/>
            <div id="drop-downs">
                From
                <select
                    name="start"
                    id="start-select"
                    onChange={(e) => {this.setState({start : e.target.value})}}
                >
                    {
                        startOptions
                    }
                </select>
                To
                <select
                    name="end"
                    id="end-select"
                    onChange={(e) => {this.setState({end : e.target.value})}}
                >
                    {
                        endOptions
                    }
                </select>
            </div>
            <br/>
            <button onClick={() => {this.props.draw(this.state.start, this.state.end);}}>Draw</button>
            <button onClick={() => {this.props.draw("", ""); this.resetDropDowns()}}>Reset</button>
            </div>
        );
    }

    async loadBuildings() {
        try {
            let res = await fetch("http://localhost:4567/listBuildings");
            if (!res.ok) {
                alert("FETCH ERROR - Status: " + res.status);
                return;
            }
            let json = await res.json();
            let buildingList : string[] = [];
            for (let i = 0; i < json.length; i++) {
                buildingList.push(json[i]);
            }
            this.setState({buildings : buildingList});
            console.log("Loaded Buildings Successfully");
        } catch (e) {
            alert("SERVER ERROR");
            console.log(e);
        }
    }

    resetDropDowns() {
        let startSelect = document.getElementById("start-select") as HTMLSelectElement;
        let endSelect = document.getElementById("end-select") as HTMLSelectElement;
        startSelect.selectedIndex = 0;
        endSelect.selectedIndex = 0;
    }
}

export default SelectBox;
