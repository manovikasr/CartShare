import firebase from "firebase";

firebase.initializeApp({
    apiKey: "AIzaSyBF5F-cft7dw_jN8C3OJ6Fgswa9694vgVo",
    authDomain: "cmpe275-cartshare.firebaseapp.com"
});

export const firebaseConfig = {
    signInFlow: "popup",
    signInOptions: [
        firebase.auth.GoogleAuthProvider.PROVIDER_ID,
        firebase.auth.FacebookAuthProvider.PROVIDER_ID,
        firebase.auth.EmailAuthProvider.PROVIDER_ID
    ],
    callbacks: {
        signInSuccess: () => false
    }
};

export const signout = () => {
    firebase.auth().signout();
}

export default {firebaseConfig, signout};
