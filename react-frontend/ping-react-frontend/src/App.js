import logo from './logo.svg';
import { useCallback, useEffect, useState } from 'react';
import './App.css';
import FileExplorer from './components/FileExplorer';
import NavBar from './components/NavBar'
import Editor from './components/Editor';
import spell from './components/Utils';
import axios from "axios";
import CustomModal from './components/CustomModal';
import { channels } from './shared/constants';
const { ipcRenderer } = window.require('electron');

function App() {

  // handle what happens on key press
  const handleKeyPress = useCallback((event) => {
    // Shortcut Modifier
    if (event.altKey === true) {
      // Shortcut list
      switch (event.key) {
        // open button
        case 'o':
          ipcRenderer.send(channels.OPEN_FILE);
          break;
        // Shortcuts information
        case 'i':
          alert("Back link not implemented !")
          break;
        // Spelling
        case 's':
          spell()
          break;
        // Build
        case 'b':
          alert("Back link not implemented !")
          break;
        // Run
        case 'r':
          alert("Back link not implemented !")
          break;
        // Stop
        case 'e':
          alert("Back link not implemented !")
          break;
      }
    }
  }, []);

  useEffect(() => {
    // attach the event listener
    document.addEventListener('keydown', handleKeyPress);

    // remove the event listener
    return () => {
      document.removeEventListener('keydown', handleKeyPress);
    };
  }, [handleKeyPress]);

  const [project, setProject] = useState(null)
  const [open, setOpen] = useState(false)
  const [dyslexia, setDyslexia] = useState(false)

  ipcRenderer.on(channels.OPEN_FILE, async (event, arg) => {
      await axios.get('http://localhost:8080/project/load?path='+arg.filePaths[0])
        .then((response) => setProject(response.data));
  });

  function toggleOpen() {
    setOpen(!open)
    console.log(open)
  }

  function updateDyslexia() {
    const checkbox = document.getElementById('dyslexia-check');
    console.log(checkbox.checked)
    setDyslexia(checkbox.checked)
  }

   return (
    <div className="App">
      <body>
        <CustomModal isOpen={open} toggle={toggleOpen}/>
        <NavBar dyslexia={dyslexia} open={setProject} shortcut={toggleOpen}/>
          <div className='flex flex-row body-row'>
            <div className="basis-1/5 flex-none border-solid border-2 border-black-">
              <FileExplorer></FileExplorer>
              <div className='border'></div>
              <div>
                <h1>Dyslexia options</h1>
                <input id='dyslexia-check' type='checkbox' onChange={updateDyslexia}></input> Enable dyslexia options
              </div>
            </div>
            <div className="basis-4/5 grow flex-1 border-solid border-1 border-black-">
              <Editor dyslexia={true}/>
            </div>
          </div>
      </body>
    </div>
  );
}

export default App;
