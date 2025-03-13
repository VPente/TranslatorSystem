<template>
  <div>
    <div class="titleHead">
      <h2>Translator List</h2>
      <button @click="goToNewTranslator" class="btn btn-primary mb-3">
        Novo Tradutor
      </button>
    </div>
    <table class="table">
      <thead>
        <tr>
          <th class="d-none">ID</th>
          <th>Name</th>
          <th>Email</th>
          <th>Source Language</th>
          <th>Target Language</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="translator in translators" :key="translator.id">
          <td class="d-none">{{ translator.id }}</td>
          <td>{{ translator.name }}</td>
          <td>{{ translator.email }}</td>
          <td>{{ translator.sourceLanguage }}</td>
          <td>{{ translator.targetLanguage }}</td>

          <td>
            <button
              @click="editTranslator(translator.id)"
              class="btn btn-sm btn-warning"
            >
              Edit
            </button>
            <button
              @click="deleteTranslator(translator.id)"
              class="btn btn-sm btn-danger"
            >
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import ApiService from "../services/ApiService";

const router = useRouter();
const translators = ref([]);

const fetchTranslators = async () => {
  try {
    const response = await ApiService.getAllTranslators();
    translators.value = response.data;
  } catch (error) {
    console.error("Error at search translator:", error);
  }
};

const goToNewTranslator = () => {
  router.push({ name: "NewTranslator" });
};

const editTranslator = (id) => {
  router.push({ name: "EditTranslator", params: { id } });
};

const deleteTranslator = async (id) => {
  try {
    await ApiService.deleteTranslator(id);
    fetchTranslators(); // Atualiza a lista após exclusão
  } catch (error) {
    console.error("Error at detele translator:", error);
  }
};

onMounted(fetchTranslators);
</script>

<style scoped>
.titleHead {
  display: flex;
  justify-content: space-between;
}
</style>
