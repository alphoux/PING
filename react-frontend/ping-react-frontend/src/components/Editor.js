import React from 'react';
import $ from 'jquery';


function applyHighlights(text) {
    return text.split("\n").map((line, index) => {
        if (index % 2 === 0) {
            return <><mark style={{color:"transparent",backgroundColor:"lavender",}}>{line}</mark><br></br></>; 
        } else {
            return <><mark style={{color: "transparent", "background-color": "yellow",}}> {line} </mark><br></br></>
        } 
    });
}

function tohtml(text)
{
    var e = document.createElement('div');
    e.innerHTML = text;
    return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
}

function handleScrolldyslexia(){
    var scrollTop = $('textarea').scrollTop();
    $('#back').scrollTop(scrollTop);
}

function handleScrolldyslexia2(){
    var scrollTop = $('#back').scrollTop();
    $('textarea').scrollTop(scrollTop);
}
export default class Editor extends React.Component {
    constructor(props) {
        super(props);
        this.state = {code:""};
        this.handlechange = this.handlechange.bind(this);
        this.handlechangedyslexia = this.handlechangedyslexia.bind(this);
    }
   
    handlechange(e) {
        this.setState({code:document.getElementById("area").value});
    }


    handlechangedyslexia(e) {
        let text = document.getElementById("area").value;
        this.setState({code:document.getElementById("area").value});
        let highlightedText = applyHighlights(text);
        document.getElementById("highlight").innerHTML = highlightedText; 
    }



    render() {
        return (
            <>
                <div className='h-full w-full text-xl text-white text-left bg-white'>
                    <textarea className='h-full w-full text-xl text-white text-left absolute z-10' id="area" style={{ outline: "none", resize: "none", color: "#444", backgroundColor: "transparent" }} onChange={(value) => {
                        this.props.onChange(value.target.value);
                        //this.handlechangedyslexia(value);
                    }} onScroll={handleScrolldyslexia} value={this.props.value}>
                    </textarea>
                    <div className='h-full w-full text-xl text-white text-left' id="back" style={{ overflow: "auto", backgroundColor: "#ffff" }} onScroll={handleScrolldyslexia2}>
                        <div className='h-full w-full text-xl text-white text-left' id="highlight" style={{ whiteSpace: "pre-wrap", wordWrap: "break-word", color: "transparent" }}>
                            {applyHighlights(this.props.value)}
                        </div>
                    </div>
                </div>
            </>
        )
    }
}