import logo from './logo.svg';
import './App.css';
import Toolbar from './components/Toolbar.js';
import FileExplorer from './components/FileExplorer';

function App() {
  return (
    <div className="App">
      <body>
      <Toolbar></Toolbar>
      <div className="w-2/5 h-full border-solid border-2 border-black-">
      <FileExplorer></FileExplorer>
      </div>
      </body>
    </div>
  );
}

export default App;
