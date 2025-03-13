<template>
  <div>
    <div class="titleHead">
      <h2>Documents List</h2>
      <button @click="goToNewDocument" class="btn btn-primary mb-3">
        Novo Documento
      </button>
    </div>
    <table class="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Subject</th>
          <th>Content</th>
          <th>Locale</th>
          <th>Author</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="document in documents" :key="document.id">
          <td>{{ document.id }}</td>
          <td>{{ document.subject }}</td>
          <td>{{ document.content }}</td>
          <td>{{ document.locale }}</td>
          <td>{{ document.author }}</td>
          <td>
            <button
              @click="editDocument(document.id)"
              class="btn btn-sm btn-warning"
            >
              Editar
            </button>
            <button
              @click="deleteDocument(document.id)"
              class="btn btn-sm btn-danger"
            >
              Excluir
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
const documents = ref([]);

// Função para buscar documentos na API
const fetchDocuments = async () => {
  try {
    const response = await ApiService.getAllDocuments();
    documents.value = response.data;
  } catch (error) {
    console.error("Erro ao buscar documentos:", error);
  }
};

// Navega para a página de criação de documento
const goToNewDocument = () => {
  router.push({ name: "NewDocument" });
};

// Navega para a página de edição de documento
const editDocument = (id) => {
  router.push({ name: "EditDocument", params: { id } });
};

// Exclui um documento e atualiza a lista
const deleteDocument = async (id) => {
  try {
    await ApiService.deleteDocument(id);
    fetchDocuments(); // Atualiza lista após exclusão
  } catch (error) {
    console.error("Erro ao excluir documento:", error);
  }
};

// Carrega os documentos ao montar o componente
onMounted(fetchDocuments);
</script>

<style scoped>
.titleHead {
  display: flex;
  justify-content: space-between;
}
</style>
