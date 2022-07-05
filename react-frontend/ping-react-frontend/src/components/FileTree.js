import * as React from 'react';
import TreeView from '@mui/lab/TreeView';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import TreeItem from '@mui/lab/TreeItem';
import axios from "axios";

function nameFromPath(fullPath) {
  console.log(fullPath)
  let regex = new RegExp(/^.*[\\/]/)
  let name = fullPath.replace(regex, '')
  return name
}

export default function FileTree(props) {
  console.log(props);
  const renderTree = (nodes) => (
    <TreeItem key={nodes.id} nodeId={nodes.id} label={nameFromPath(nodes.path)} onClick={nodes.file ? () => {
      axios.get("http://localhost:8080/project/makeActive?path=" + encodeURIComponent(nodes.path)).then((result) => {
        const area = document.getElementById('area');
        area.value = result.data.content;
      }).catch((err) => {
        console.log(err);
      });
    } : null}>
      {Array.isArray(nodes.children)
        ? nodes.children.map((node) => renderTree(node))
        : null}
    </TreeItem>
  );

  return (
    <TreeView
      aria-label="rich object"
      defaultCollapseIcon={<ExpandMoreIcon />}
      defaultExpanded={['root']}
      defaultExpandIcon={<ChevronRightIcon />}
      sx={{ height: 450, flexGrow: 1, maxWidth: 400, overflowY: 'auto' }}
    >
      {props.fs == null ? null : renderTree(props.fs.nodes)}
    </TreeView>
  );
}
