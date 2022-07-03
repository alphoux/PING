import { useEffect } from 'react';

export default function spell() {
  const highlighted = window.getSelection().toString();
  const msg = new SpeechSynthesisUtterance();
  msg.text = highlighted;
  window.speechSynthesis.speak(msg)
}