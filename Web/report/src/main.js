import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyBfXU2VchsVkUMylWU8Th8YTZ_cDEo8a4s",
  authDomain: "report-aldo.firebaseapp.com",
  projectId: "report-aldo",
  storageBucket: "report-aldo.appspot.com",
  messagingSenderId: "911789820108",
  appId: "1:911789820108:web:8e5c127f0775754136da69",
  measurementId: "G-WT0S54DNVM"
};

// Initialize Firebase
initializeApp(firebaseConfig);
// const analytics = getAnalytics(app);

const app = createApp(App)

app.use(router)

app.mount('#app')
