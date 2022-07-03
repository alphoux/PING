import React from "react"
import Modal from "./Modal";
import spell from "./Utils";

export default class NavBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showing: false
        }
        this.setShowing = this.setShowing.bind(this)
    }

    setShowing() {
        this.setState({
            showing: !this.state.showing
        })
    }

    addOptions() {
        if (this.props.dyslexia) {
            return (
                <div>
                    <button id='spell-btn 'className="btn action-bar-btn" onClick={spell}>Spell</button>
                </div>
            )
        }
    }

    render() {
        return(
        <div className="action-bar flex justify-between">
            <div >
                <button id='open-btn' className="btn action-bar-btn">Open</button>
                <button className="btn action-bar-btn" onClick={this.setShowing}>Shortcuts</button>
            </div>
            {this.addOptions()}
            <div>
               <button id='build-btn' className="btn action-bar-btn">Build</button>
               <button id='run-btn' className="btn action-bar-btn">Run</button>
               <button id='stop-btn' className="btn action-bar-btn">Stop</button>
            </div>
            <Modal isShowing={this.state.showing} toggle={this.setShowing}/>
        </div>
        )
    }
}