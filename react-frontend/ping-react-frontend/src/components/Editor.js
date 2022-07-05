import React from 'react';
import $ from 'jquery';


function applyHighlights(text) {
    return text.split("\n").map((line, index) => {
        if (index % 2 === 0) {
            return line
        } else {
            return '<mark style={{color: "transparent", background-color: "yellow",}}>'+line+'</mark>'
        } 
    }).join("\n");
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
        //console.log(this.props.value);
        return (
            <>
                {
                this.props.dyslexia ? (
                    <div  className='h-full w-full text-xl text-white text-left bg-white'>
                        <textarea className='h-full w-full text-xl text-white text-left absolute z-10' id="area" style={{ outline: "none", resize: "none", color:"#444",backgroundColor:"transparent" }}  onChange={(value) => {
                            
                            this.props.onChange(value.target.value);
                            this.handlechangedyslexia(null);
                            }} onScroll={handleScrolldyslexia} value={this.props.value}>
                        </textarea>
                        <div className='h-full w-full text-xl text-white text-left'id="back" style={{overflow:"auto",backgroundColor:"#ffff"}} onScroll={handleScrolldyslexia2}>
                            <div className='h-full w-full text-xl text-white text-left'id="highlight" style={{whiteSpace: "pre-wrap", wordWrap: "break-word",color:"transparent"}}>
                            </div>
                        </div>
                    </div>
                )
                    :
                    (
                        <textarea id="area" style={{ outline: "none", resize: "none", }} className='bg-gray-800 h-full w-full text-xl text-white text-left' onChange={this.handlechange}>
                        </textarea>
                    )
            }
            </>
        )
    }
}