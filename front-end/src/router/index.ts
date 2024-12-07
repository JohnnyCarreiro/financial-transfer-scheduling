import { createRouter, createWebHistory } from "vue-router";
import TransferForm from "../pages/TransferForm.vue";
import TransferList from "../pages/TransferList.vue";

const routes = [
	{ path: "/", redirect: "/transferencias" }, // Redireciona para /transfers ao acessar /
	{ path: "/transferencias", component: TransferList },
	{ path: "/transferencias/nova", component: TransferForm },
];

const router = createRouter({
	history: createWebHistory(),
	routes,
});

export default router;
