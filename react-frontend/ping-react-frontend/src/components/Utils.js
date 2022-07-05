import { useEffect } from 'react';
import axios from "axios";

function spell() {
  const highlighted = window.getSelection().toString();
  const msg = new SpeechSynthesisUtterance();
  msg.text = highlighted;
  window.speechSynthesis.speak(msg)
}

export { spell};