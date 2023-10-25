import { createApp } from "vue"
import App from "./App.vue"
import { router } from "@/router"
import { createPinia } from "pinia"
import piniaPersistedstate from "pinia-plugin-persist"
import Toast from 'vue-toastification'
import "/src/assets/scss/main.scss"
import "/node_modules/bootstrap/dist/js/bootstrap.bundle.js"
import "vue-toastification/dist/index.css";

const pinia = createPinia();
pinia.use(piniaPersistedstate)

const app = createApp(App)
app.config.errorHandler = function(err, vm, info) {
  console.log("에러", err)
}

app
  .use(router)
  .use(pinia)
  .use(Toast)
  .mount("#app");