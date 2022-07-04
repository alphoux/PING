// Modules to control application life and create native browser window
const {app, BrowserWindow, dialog} = require('electron')
const path = require('path')
var Server = require('electron-rpc/server')
var tmp = new Server()

let mainWindow

function createWindow () {
  // Create the browser window.
   mainWindow = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      preload: __dirname + '/preload.js',
      nodeIntegration: true,
      contextIsolation: false,
    }
  })

  // and load the index.html of the app.
    mainWindow.webContents.openDevTools()
    mainWindow.loadURL('http://localhost:3000')
  // Open the DevTools.
}

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.whenReady().then(() => {
  createWindow()

  app.on('activate', function () {
    // On macOS it's common to re-create a window in the app when the
    // dock icon is clicked and there are no other windows open.
    if (BrowserWindow.getAllWindows().length === 0) createWindow()
    tmp.configure(mainWindow.webContents) // pass a BrowserWindow.webContents[1] object

tmp.on('some-action-without-callback', function(req){
    console.log('foo')
})

// You can also send messages without triggering a request on the client
tmp.send('some-server-message', 'bar')
  })
})

// Quit when all windows are closed, except on macOS. There, it's common
// for applications and their menu bar to stay active until the user quits
// explicitly with Cmd + Q.
app.on('window-all-closed', function () {
  if (process.platform !== 'darwin') app.quit()
})

// In this file you can include the rest of your app's specific main process
// code. You can also put them in separate files and require them here.
exports.test = () => console.log(dialog.showOpenDialog(mainWindow, { properties: ['openFile', 'multiSelections'] }));