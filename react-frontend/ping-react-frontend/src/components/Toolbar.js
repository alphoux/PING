import React from 'react';

export default class Toolbar extends React.Component {
    render() {
       return (<div class="btn-group">
                <button class="btn btn-active">Build</button>
                <button class="btn">Run</button>
                <button class="btn">Stop</button>
    </div>)
    }
}
