import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import "./assets/index.css";
import { transferService } from "@/config/ServiceProvider";

createApp(App)
	.provide("transferService", transferService)
	.use(router)
	.mount("#app");
