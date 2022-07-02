import React from 'react';


function FileList() {
    return (<div class="overflow-x-auto">
    <table class="table w-full">
      <thead>
        <tr>
          <th>Name</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>HelloWorld.java</td>
        </tr>
        <tr class="active">
          <td>Main.java</td>
        </tr>
        <tr>
          <td>WebApp.java</td>
        </tr>
      </tbody>
    </table>
  </div>)
}

export default class FileExplorer extends React.Component {
    render() {
        return (
            <div className=''>
                <FileList></FileList>
            </div>
        )
    }
}