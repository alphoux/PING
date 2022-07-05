import logo from './logo.svg';
import { useCallback, useEffect, useState } from 'react';
import './App.css';
import FileExplorer from './components/FileExplorer';
import NavBar from './components/NavBar'
import Editor from './components/Editor';
import spell from './components/Utils';

function App() {

  // handle what happens on key press
  const handleKeyPress = useCallback((event) => {
    // Shortcut Modifier
    if (event.altKey === true) {
      // Shortcut list
      switch (event.key) {
        // open button
        case 'o':
          alert("Back link not implemented !");
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

   return (
    <div className="App">
      <body>
      <NavBar dyslexia={true} open={setProject}/>
        <div className='flex flex-row body-row'>
          <div className="basis-1/5 flex-none border-solid border-2 border-black-">
            <FileExplorer></FileExplorer>
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
