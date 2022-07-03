import logo from './logo.svg';
import './App.css';
import Toolbar from './components/Toolbar.js';
import FileExplorer from './components/FileExplorer';
import NavBar from './components/NavBar'
import Editor from './components/Editor';

function App() {
  return (
    <div className="App">
      <body>
        <NavBar dyslexia={true} />
        <div className='flex flex-row body-row'>
          <div className="basis-1/5 flex-none border-solid border-2 border-black-">
            <FileExplorer></FileExplorer>
          </div>
          <div className="basis-4/5 grow flex-1 border-solid border-2 border-black-">
            <Editor/>
          </div>
        </div>
      </body>
    </div>
  );
}

export default App;
