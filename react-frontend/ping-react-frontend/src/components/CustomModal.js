import Modal from 'react-modal';
import { useCallback, useEffect} from 'react';
import './../App.css';

const customStyles = {
    content: {
      top: '50%',
      left: '50%',
      right: 'auto',
      bottom: 'auto',
      marginRight: '-50%',
      transform: 'translate(-50%, -50%)',
      color: 'black'
    },
  };

Modal.setAppElement('#root');

const CustomModal = (props) => {

    return ( 
      <Modal isOpen={props.isOpen} style={customStyles}>
        <h1 className="text-center">Shortcuts</h1>
        <br></br>
        <table>
          <tr> <td>Open file:</td> <td>Alt+o</td> </tr>
          <tr> <td>Display shortcuts:</td> <td>Alt+i</td> </tr>
          <tr> <td>Dyslexia spelling:</td> <td>Alt+s</td> </tr>
          <tr> <td>Build project:</td> <td>Alt+b</td> </tr>
          <tr> <td>Run project:</td> <td>Alt+t</td> </tr>
          <tr> <td>Stop project:</td> <td>Alt+e</td> </tr>
        </table>
        <div className='text-center'>
          <button className='btn modal-btn' onClick={props.toggle}>Close</button>
        </div>
      </Modal>
  )
}

export default CustomModal