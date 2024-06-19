<template>
  <h1>Log In To Your Account</h1>
  <p><input type="text" placeholder="Email" v-model="email"></p>
  <p><input type="password" placeholder="Password" v-model="password"></p>
  <p v-if="errormsg">{{ errormsg }}</p>
  <p><button @click="signIn">Sign in</button></p>
  <p><button @click="signInWithGoogle">Sign In With Google</button></p>
</template>

<script setup>
import { ref } from "vue";
import { getAuth, signInWithEmailAndPassword } from "firebase/auth";
import { useRouter } from "vue-router";

const email = ref("");
const password = ref("");
const errormsg = ref("");
const router = useRouter();

const signIn = () => {
  const auth = getAuth();
  signInWithEmailAndPassword(auth, email.value, password.value)
    .then((data) => {
      console.log("successfully signed in");
      // Redirect or handle successful sign-in
      // router.push('/some-route'); // Uncomment and set the route as needed
    })
    .catch((error) => {
      console.log(error.code);
      alert(error.message);
      switch (error.code) {
        case "auth/invalid-email":
          errormsg.value = "invalid email"
          break;
        case "auth/user-not-found":
          errormsg.value = "No account with that email was found"
          break;
        case "auth/wrong-password":
          errormsg.value = "Incorrect password"
          break;
        default:
          errormsg.value = "Email or password was incorrect"
          break;
      }
    });
};

const signInWithGoogle = () => {
  // Implement Google sign-in logic here
};
</script>
