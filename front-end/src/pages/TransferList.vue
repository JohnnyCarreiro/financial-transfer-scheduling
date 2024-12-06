<script lang="ts">
import { inject, ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import type { ITransferService } from "@/services/ITransferService";
import type { Transfer } from "@/@types";
import TransferTable from "@/components/TransferTable.vue";

export default {
	name: "TransferList",
	components: { TransferTable },
	setup() {
		const transfers = ref<Transfer[]>([]);
		const errorMessage = ref<string>("");
		const router = useRouter();

		const transferService =
			inject<ITransferService<Transfer>>("transferService");

		const navigateToNewTransfer = () => {
			router.push("/transferencias/nova");
		};

		onMounted(async () => {
			if (transferService) {
				try {
					const data = await transferService.listTransfers();
					console.log(">>>> Data: ", data);
					transfers.value = data;
				} catch (error) {
					errorMessage.value = "Erro ao buscar transferências.";
				}
			}
		});

		return { transfers, errorMessage, navigateToNewTransfer };
	},
};
</script>

<template>
  <div class="m-auto w-full md:max-w-6xl p-4">
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-2xl font-bold">Lista de Transferências</h1>
      <button
        class="flex items-center gap-2 bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600"
        @click="navigateToNewTransfer"
      >
        Nova Transferência
      </button>
    </div>
    <div>
      <TransferTable :transfers="transfers" />
      <p v-if="errorMessage" class="text-red-500 mt-4">{{ errorMessage }}</p>
    </div>
  </div>
</template>

