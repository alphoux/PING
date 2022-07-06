import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

export default function FormDialog(props) {
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    props.onCreated(document.getElementById("FileNameTextField").value);
  };

  return (
    <div>
      <button variant="outlined" onClick={handleClickOpen}>
        Create File
      </button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Create File</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Enter file name
          </DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="FileNameTextField"
            label="filename"
            type="string"
            fullWidth
            variant="standard"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleClose}>Create</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}