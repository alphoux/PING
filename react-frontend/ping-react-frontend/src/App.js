import logo from './logo.svg';
import { useCallback, useEffect, useState } from 'react';
import './App.css';
import FileExplorer from './components/FileExplorer';
import NavBar from './components/NavBar'
import Editor from './components/Editor';
import {spell, save} from './components/Utils';
import axios from "axios";
import CustomModal from './components/CustomModal';
import { channels } from './shared/constants';
import AceEditor from "react-ace";

import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/theme-github";
import "ace-builds/src-noconflict/ext-language_tools";
import FormDialog from './components/FormDialog';

const { ipcRenderer } = window.require('electron');




function App() {

  const [project, setProject] = useState(null)
  const [open, setOpen] = useState(false)
  const [dyslexia, setDyslexia] = useState(false)
  const [textValue, setTextValue] = useState("")
  const [currentPath, setCurrentPath] = useState("")

  // handle what happens on key press
  const handleKeyPress = useCallback(async (event) => {
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
          setOpen(!open)
          break;
        // Spelling
        case 's':
          spell()
          break;
        // Build
        case 'b':
          await axios.get('http://localhost:8080/maven/build')
          .then((response) => {
              if (response.status == 400) {
                  alert("Project is not a maven project, unable to build")
                  return;
              }
          })
          .catch((e) => console.log(e))
          break;
        // Run
        case 'r':
          await axios.get('http://localhost:8080/maven/run')
          .then((response) => {
              if (response.status == 400) {
                  alert("Project is not a maven project, unable to build")
                  return;
              }
          })
          .catch((e) => console.log(e))
          break;
        // Stop
        case 'e':
          await axios.get('http://localhost:8080/maven/stop')
          .then((response) => {
              if (response.status == 400) {
                  alert("Project is not a maven project, unable to build")
                  return;
              }
          })
          .catch((e) => console.log(e))
          break;
      }
    }
    if (event.crtlKey === true) {
      if (event.key === 's')
      {
        save();
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

  function save() {
    console.log("saveeee");
    axios.post('http://localhost:8080/project/updateContent', {
      content: textValue,
    })
  }

  function createFile(enteredName) {
    axios.get("http://localhost:8080/project/createFile?name=" + enteredName).then(() => {
      axios.get("http://localhost:8080/project/getStructure").then((res) => {
        setProject(res.data)
      })
    });
  }

  function deleteFile() {
    axios.get("http://localhost:8080/project/deleteCurrent").then(() => {
        axios.get("http://localhost:8080/project/getStructure").then((res) => {
          setProject(res.data)
        })
    })
  }

let textArea = "";

   return (
    <div className="App">
      <body>
        <CustomModal isOpen={open} toggle={toggleOpen}/>
        <NavBar dyslexia={dyslexia} open={setProject} shortcut={toggleOpen} saveFunction={save}/>
          <div className='flex flex-row body-row'>
            <div className="basis-1/5 flex-none border-solid border-2 border-black-">
              <FileExplorer fs={project} onChange={setTextValue}></FileExplorer>
              <div className="grid grid-cols-2 place-content-around">
              <FormDialog onCreated={createFile}></FormDialog>
                <button className="w-50" onClick={deleteFile}>
                Delete File
                </button>
              </div>
              <div className='border'></div>
              <div>
                <h1>Dyslexia options</h1>
                <input id='dyslexia-check' type='checkbox' onChange={updateDyslexia}></input> Enable dyslexia options
              </div>
            </div>
            <div className="basis-4/5 grow flex-1 border-solid border-1 border-black-">
              {dyslexia ? 
                <Editor dyslexia={true} onChange={(value) => {
                  setTextValue(value);
                }}
                value={textValue}
                />
                :
                <AceEditor
                mode="java"
                theme="github"
                name="Ace Editor"
                onChange={(value) => {
                  setTextValue(value);
                }}
                value={textValue}
                editorProps={{ $blockScrolling: true }}
                style={{
                  height: "100%",
                  width: "100%",
                }}
              />
              }
            </div>
          </div>
      </body>
    </div>
  );
}

export default App;
