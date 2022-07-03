import logo from './logo.svg';
import './App.css';
import Toolbar from './components/Toolbar.js';
import FileExplorer from './components/FileExplorer';
import NavBar from './components/NavBar'
import Editor from './components/Editor';

function App() {
  return (
    <div className="App">
      <body className='flex flex-col h-full'>
        <NavBar dyslexia={true} />
        <div className='flex h-full'>
          <div className="w-1/5 h-full border-solid border-2 border-black-">
            <FileExplorer></FileExplorer>
          </div>
          <div className="w-4/5 h-full border-solid border-2 border-black-">
            <Editor/>
          </div>
        </div>
      </body>
    </div>
  );
}

export default App;
