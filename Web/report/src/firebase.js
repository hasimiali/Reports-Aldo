// src/firebase.js
import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";

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
const firebaseApp = initializeApp(firebaseConfig);

// Initialize Firestore
const db = getFirestore(firebaseApp);

export { db };
