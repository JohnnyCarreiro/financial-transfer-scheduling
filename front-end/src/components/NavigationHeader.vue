<template>
  <header class="bg-white shadow-md sticky top-0 z-10">
    <div class="container mx-auto px-4 py-4 flex items-center justify-between">
      <!-- Logo -->
      <div class="flex items-center gap-4">
        <div class="text-2xl font-bold text-gray-800">
          <router-link to="/">Transferências</router-link>
        </div>
      </div>

      <!-- Navigation Links -->
      <nav class="flex items-center gap-6">
        <router-link
          to="/transferencias"
          class="text-gray-600 hover:text-gray-900 font-medium transition"
        >
          Transferências
        </router-link>
        <router-link
          to="/transferencias/nova"
          class="text-white bg-blue-500 hover:bg-blue-600 font-medium px-4 py-2 rounded-lg shadow-md transition"
        >
          Nova Transferência
        </router-link>
      </nav>
    </div>
  </header>
</template>

<script>
export default {
	name: "NavigationHeader",
};
</script>

<style scoped>
</style>
