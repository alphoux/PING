import React from "react"
import spell from "./Utils";
import axios from "axios";
import { channels } from '../shared/constants';

const { ipcRenderer } = window.require('electron');

export default class NavBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            build: false
        }
        this.openFile = this.openFile.bind(this)
        this.buildProject = this.buildProject.bind(this)
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

    async buildProject() {
        this.setState({build: true})
        await axios.get('http://localhost:8080/project/build')
        .then((response) => {
            if (response.status == 400) {
                alert("Project is not a maven project, unable to build")
                return;
            }
        })
        .catch((e) => console.log(e))
        this.setState({build: false})
    }

    async runProject() {
        await axios.get('http://localhost:8080/project/run')
        .then((response) => {
            if (response.status == 400) {
                alert("Project is not a maven project, unable to run")
                return;
            }
        })
        .catch((e) => console.log(e))
    }

    async stopProject() {
        await axios.get('http://localhost:8080/project/stop')
        .then((response) => {
            if (response.status == 400) {
                alert("Project is not a maven project, unable to run")
                return;
            }
        })
        .catch((e) => console.log(e))
    }

    render() {
        return(
        <div className="action-bar flex justify-between">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"></link>
            <div >
                <button id='open-btn' className="btn action-bar-btn" onClick={this.openFile}>Open</button>
                <button className="btn action-bar-btn" onClick={this.props.shortcut}>Shortcuts</button>
            </div>
            {this.addOptions()}
            <div>
               <button id='build-btn' className="btn action-bar-btn" onClick={this.buildProject}>{this.state.build ? <i id='build-load' class="fa fa-spinner fa-spin mr-right"></i>:null}Build</button>
               <button id='run-btn' className="btn action-bar-btn" onClick={this.runProject}>Run</button>
               <button id='stop-btn' className="btn action-bar-btn" onClick={this.stopProject}>Stop</button>
            </div>
        </div>
        )
    }
}