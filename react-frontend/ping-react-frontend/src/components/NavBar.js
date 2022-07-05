import React from "react"
import {spell} from "./Utils";
import axios from "axios";
import { channels } from '../shared/constants';

const { ipcRenderer } = window.require('electron');

export default class NavBar extends React.Component {
    constructor(props) {
        super(props);
        this.openFile = this.openFile.bind(this)
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
    
    openFile() {
        ipcRenderer.send(channels.OPEN_FILE);
    }

    async build() {
        await axios.get('http://localhost:8080/project/build')
        .then((response) => {
            if (response.status == 400) {
                alert("Project is not a maven project, unable to build")
                return;
            }
        })
    }

    render() {
        return(
        <div className="action-bar flex justify-between">
            <div >
                <button id='open-btn' className="btn action-bar-btn" onClick={this.openFile}>Open</button>
                <button className="btn action-bar-btn" onClick={this.props.shortcut}>Shortcuts</button>
            </div>
            {this.addOptions()}
            <div>
               <button id='build-btn' className="btn action-bar-btn">Build</button>
               <button id='run-btn' className="btn action-bar-btn">Run</button>
               <button id='stop-btn' className="btn action-bar-btn">Stop</button>
            </div>
            
        </div>
        )
    }
}