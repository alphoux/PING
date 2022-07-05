import * as React from 'react';
import FileTree from './FileTree';

export default class FileExplorer extends React.Component {
    render() {
        return (
            <div>
              <FileTree fs={this.props.fs} onChange={this.props.onChange}/>
            </div>
        )
    }
}