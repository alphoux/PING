const Modal = (props) => {

    if (props.isShowing) {
        return (
            <div className='modal'>
                <div className='modal-content'>
                    <h1>Shortcuts</h1>
                    <p>Open file: Alt+o</p>
                    <p>Display shortcuts: Alt+i</p>
                    <p>Dyslexia spelling: Alt+s</p>
                    <p>Build project: Alt+b</p>
                    <p>Run project: Alt+r</p>
                    <p>Stop project: Alt+e</p>
                    <button onClick={props.toggle}/>
                </div>
            </div>
        )
    } else {
        return null;
    }
}

export default Modal