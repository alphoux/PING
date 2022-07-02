/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/components/*.js",
              "./src/*.js",
              "./src/assets*.js/"],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],
}
