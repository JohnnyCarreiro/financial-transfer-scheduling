<template>
  <TransferLayout>
    <div>
      <h1 class="text-center text-2xl mb-4">Sistema de Transferências</h1>
      <router-view></router-view>
    </div>
  </TransferLayout>
</template>

<script lang="ts">
import TransferLayout from "@/pages/Layout.vue";

export default {
	name: "App",
	components: { TransferLayout },
};
</script>

<style scoped>
/* Estilos globais ou de layout */
</style>
