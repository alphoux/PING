export default function spell() {
    if ('speechSynthesis' in window) {
        // Speech Synthesis supported
        const highlighted = window.getSelection().toString();
        let msg = new SpeechSynthesisUtterance();
        msg.text = highlighted;
        window.speechSynthesis.speak(msg);
       } else{
         // Speech Synthesis Not Supported
         alert("Sorry, your browser doesn't support text to speech!");
       }
}