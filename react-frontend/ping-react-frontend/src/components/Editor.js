import React from 'react';



export default class Editor extends React.Component {
    constructor(props) {
        super(props);
        this.code = "";
        this.handlechange = this.handlechange.bind(this);
    }

    handlechange(e) {
        this.code = e.target.value;
    }

    handledyslexia(e) {
        if (this.props.dyslexia)
        {
            return ""
        }
    }

    render()
    {
        return (
            <textarea style={{outline: "none",resize: "none",}} className='bg-gray-800 h-full w-full text-xl text-white text-left' onChange={this.handlechange}>
            </textarea>
        )
    }
}