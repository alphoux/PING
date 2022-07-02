import React from "react"

export default class NavBar extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
        <div className="action-bar flex justify-between">
            <div >
                <button className="btn action-bar-btn">Open</button>
                <button className="btn action-bar-btn">Shortcuts</button>
            </div>
            <div>
                <button className="btn action-bar-btn">Spell</button>
            </div>
            <div>
               <button className="btn action-bar-btn">Build</button>
               <button className="btn action-bar-btn">Run</button>
               <button className="btn action-bar-btn">Stop</button>
            </div>
        </div>
        )
    }
}