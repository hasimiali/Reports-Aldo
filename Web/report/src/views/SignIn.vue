<template>
  <div class="login-container">
    <h1>Log In To Your Account</h1>
    <p><input type="text" placeholder="Email" v-model="email"></p>
    <p><input type="password" placeholder="Password" v-model="password"></p>
    <p v-if="errormsg" class="error">{{ errormsg }}</p>
    <p><button @click="signIn">Sign in</button></p>
  </div>
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
          errormsg.value = "Invalid email";
          break;
        case "auth/user-not-found":
          errormsg.value = "No account with that email was found";
          break;
        case "auth/wrong-password":
          errormsg.value = "Incorrect password";
          break;
        default:
          errormsg.value = "Email or password was incorrect";
          break;
      }
    });
};

const signInWithGoogle = () => {
  // Implement Google sign-in logic here
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 2rem;
  background-color: #f0f0f0;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  margin-bottom: 40px;
  color: #333;
}

p {
  margin: 1rem 0;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 0.75rem;
  margin-top: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 1rem;
}

button {
  width: 100%;
  padding: 0.75rem;
  border: none;
  border-radius: 4px;
  background-color: #333;
  color: #fff;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #555;
}

.error {
  color: red;
  text-align: center;
}

.google-signin {
  background-color: #db4437;
  margin-top: 1rem;
}

.google-signin:hover {
  background-color: #c33d2e;
}
</style>
