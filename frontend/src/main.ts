import { createApp } from "vue";
import App from "./App.vue";
import { router } from "@/router";
import { createPinia } from "pinia"; 
import "/src/assets/scss/main.scss";
import "/node_modules/bootstrap/dist/js/bootstrap.bundle.js"

const pinia = createPinia();

createApp(App)
.use(router)
.use(pinia)
.mount("#app");
