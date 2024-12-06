<script setup lang="ts">
import { useRouter } from "vue-router";
import { inject } from "vue";
import type { ITransferService } from "@/services/ITransferService";
import type { Transfer } from "@/@types";
import { Toaster } from "@/components/ui/toast";

import { Button } from "@/components/ui/button";
import {
	FormControl,
	FormDescription,
	FormField,
	FormItem,
	FormLabel,
	FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { toast } from "@/components/ui/toast";

import { toTypedSchema } from "@vee-validate/zod";
import { useForm } from "vee-validate";
import { h } from "vue";
import * as z from "zod";

const transferService = inject<ITransferService<Transfer>>("transferService");

const formSchema = toTypedSchema(
	z.object({
		sourceAccount: z
			.string({ message: "Campo obrigatório." })
			.min(10, { message: "A Conta do remetente deve ter 10 caracteres." })
			.max(10),
		destinationAccount: z
			.string({ message: "Campo obrigatório." })
			.min(10, "A Conta do destinatário deve ter 10 caracteres.")
			.max(10),
		amount: z.coerce
			.number({ message: "O valor deve ser um número." })
			.min(1, "O valor deve ser maior que 0."),
		scheduledDate: z
			.string({ message: "Campo obrigatório." })
			.refine(
				(value) => !isNaN(Date.parse(value)),
				"A data deve ser válida no formato YYYY-MM-DD.",
			)
			.refine((value) => {
				const inputDate = new Date(value + "T00:00:00");
				const today = new Date();
				today.setHours(0, 0, 0, 0);

				return inputDate >= today;
			}, "A data de agendamento não pode ser no passado."),
	}),
);

const { handleSubmit, resetForm } = useForm({
	validationSchema: formSchema,
});

const router = useRouter();

const navigateToNewTransfer = () => {
	router.push("/transferencias");
};

const onSubmit = handleSubmit(async (values) => {
	try {
		if (!transferService) {
			throw new Error("Serviço de transferência não disponível.");
		}

		const response = await transferService.createTransfer({
			sourceAccount: values.sourceAccount,
			destinationAccount: values.destinationAccount,
			amount: parseFloat(values.amount.toString()),
			scheduledDate: new Date(values.scheduledDate).toISOString(),
		});

		if (response != null) {
			toast({
				title: "Transferência realizada com sucesso!",
				description: h("div", { class: "mt-2 w-[340px]" }, [
					h(
						"button",
						{
							class:
								"block w-full bg-green-500 text-white py-2 px-4 rounded mb-2 hover:bg-green-600",
							onClick: navigateToNewTransfer,
						},
						"Voltar para Transferências",
					),
					h(
						"button",
						{
							class:
								"block w-full bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600",
							onClick: () => resetForm(),
						},
						"Nova Transferência",
					),
				]),
			});
		} else {
			// Caso a resposta seja `null`
			toast({
				title: "Algo deu errado!",
				description: "A transferência não foi concluída. Tente novamente.",
				class: "bg-red-500 text-white",
			});
		}
	} catch (error) {
		// Toast de erro genérico
		toast({
			title: "Erro ao realizar a transferência",
			description:
				error instanceof Error ? error.message : "Erro desconhecido.",
			class: "bg-red-500 text-white",
		});
	}
});
</script>

<template>
  <Toaster />
  <div class="p-6 max-w-lg mx-auto bg-white shadow-md rounded-lg">
    <h1 class="text-2xl font-bold text-center mb-6">Nova Transferência</h1>
    <form class="space-y-6" @submit="onSubmit">
      <FormField v-slot="{ componentField }" name="sourceAccount">
        <FormItem>
          <FormLabel>Remetente</FormLabel>
          <FormControl>
            <Input type="text" placeholder="Conta do remetente" v-bind="componentField" />
          </FormControl>
          <FormDescription>
            Informe a conta do remetente.
          </FormDescription>
          <FormMessage />
        </FormItem>
      </FormField>

      <FormField v-slot="{ componentField }" name="destinationAccount">
        <FormItem>
          <FormLabel>Destinatário</FormLabel>
          <FormControl>
            <Input type="text" placeholder="Conta do destinatário" v-bind="componentField" />
          </FormControl>
          <FormDescription>
            Informe a conta do destinatário.
          </FormDescription>
          <FormMessage />
        </FormItem>
      </FormField>

      <FormField v-slot="{ componentField }" name="amount">
        <FormItem>
          <FormLabel>Valor</FormLabel>
          <FormControl>
            <Input type="text" placeholder="Valor da transferência" v-bind="componentField" />
          </FormControl>
          <FormDescription>
            Informe o valor da transferência em reais.
          </FormDescription>
          <FormMessage />
        </FormItem>
      </FormField>

      <FormField v-slot="{ componentField }" name="scheduledDate">
        <FormItem>
          <FormLabel>Data de Agendamento</FormLabel>
          <FormControl>
            <Input type="date" v-bind="componentField" />
          </FormControl>
          <FormDescription>
            Escolha a data para agendar a transferência. A data deve ser a partir de hoje.
          </FormDescription>
          <FormMessage />
        </FormItem>
      </FormField>

      <Button type="submit" class="w-full bg-green-500 text-white">
        Confirmar Transferência
      </Button>
    </form>
  </div>
</template>

<style scoped>
h1 {
  color: #2d3748;
}
</style>

