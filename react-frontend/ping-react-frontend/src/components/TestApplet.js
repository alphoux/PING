import React from 'react';

export default class TestApplet extends React.Component {
    render () {
        return (
        <applet code="MyIde.class" name="fader" width="400" height="100">
        <param name="init" ></param>
        <button onClick={buttonCalled}></button>
        </applet> )
    }
}