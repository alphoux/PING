import { useEffect } from 'react';
import axios from "axios";

function spell() {
  const highlighted = window.getSelection().toString();
  const msg = new SpeechSynthesisUtterance();
  msg.text = highlighted;
  window.speechSynthesis.speak(msg)
}

function save() {
  const text = document.getElementById("area").value;
  axios.post('/project/updateContent', {
    content: text,
  })
}

export { spell, save };