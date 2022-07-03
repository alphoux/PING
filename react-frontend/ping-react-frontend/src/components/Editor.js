import React from 'react';



export default function Editor() {
    const [code, setCode] = React.useState('');

    //let infofile = await fetch('localhost:8080/project/getContent')    
    // onSelect={
    // const selection = window.getSelection();
    //const selectedText = selection.toString();
    //}
    //
    //

    function handlechange(e) {
        setCode(e.target.value);
    }

    return (
            <textarea className='bg-gray-800 h-full w-full text-xl text-white text-left' onChange={handlechange}>
            </textarea>
    )
}