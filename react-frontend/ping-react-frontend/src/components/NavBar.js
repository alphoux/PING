import React from "react"
import Modal from 'react-modal';
import spell from "./Utils";
import isElectron from 'is-electron';
var Client = require('electron-rpc/client')
let client = new Client()


Modal.setAppElement(document.getElementById('root'));

export default class NavBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showing: false
        }
        this.setShowing = this.setShowing.bind(this)
        this.openFile = this.openFile.bind(this)
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

    openFile() {
          client.request('some-action-without-callback')
          
          client.on('some-server-message', function(err, body){
              console.log('foo:', body)
          })
    }

    render() {
        return(
        <div className="action-bar flex justify-between">
            <div >
                <button id='open-btn' className="btn action-bar-btn" onClick={this.openFile}>Open</button>
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