// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import {getStorage} from 'firebase/storage'
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyCNN1uQso6DmfpEgp0b5eSK25NHTO-vREU",
    authDomain: "diplomski-c2903.firebaseapp.com",
    projectId: "diplomski-c2903",
    storageBucket: "diplomski-c2903.appspot.com",
    messagingSenderId: "954815918675",
    appId: "1:954815918675:web:6c569e780734fd99504ca6"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const storage = getStorage(app);