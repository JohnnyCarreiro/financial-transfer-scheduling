<template>
  <div class="min-h-screen flex flex-col bg-gray-100">
    <NavigationHeader />

    <main class="container mx-auto px-4 py-8">
      <h1 class="text-2xl font-bold text-gray-800 mb-4">Bem-vindo às Transferências</h1>
      <slot />
    </main>
  </div>
</template>

<script>
import NavigationHeader from "@/components/NavigationHeader.vue";

export default {
	name: "TransferLayout",
	components: {
		NavigationHeader,
	},
};
</script>
