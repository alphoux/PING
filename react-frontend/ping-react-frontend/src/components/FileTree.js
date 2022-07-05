import * as React from 'react';
import TreeView from '@mui/lab/TreeView';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import TreeItem from '@mui/lab/TreeItem';

function nameFromPath(fullPath) {
  console.log(fullPath)
  let regex = new RegExp(/^.*[\\/]/)
  let name = fullPath.replace(regex, '')
  return name
}

const data = {
  id: 'root',
  path: 'project',
  children: [
    {
      id: '1',
      path: "project/src",
      children: []
    },
    {
      id: '2',
      path: "project/src/lib",
      children: [
        {
          id: '3',
          path: 'project/src/lib/kipu.yolo',
          children: []
        },
      ],
    },
  ],
};

export default function FileTree() {
  const renderTree = (nodes) => (
    <TreeItem key={nodes.id} nodeId={nodes.id} label={nameFromPath(nodes.path)}>
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
      sx={{ height: 110, flexGrow: 1, maxWidth: 400, overflowY: 'auto' }}
    >
      {renderTree(data)}
    </TreeView>
  );
}
