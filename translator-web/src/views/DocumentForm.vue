<template>
  <div class="container">
    <h2>{{ isEdit ? "Editar Documento" : "Novo Documento" }}</h2>

    <form @submit.prevent="saveDocument" class="document-form">
      <div class="mb-3">
        <label for="subject" class="form-label">Assunto</label>
        <input v-model="doc.subject" type="text" class="form-control" id="subject" required />
      </div>

      <div class="mb-3">
        <label for="content" class="form-label">Conteúdo</label>
        <textarea v-model="doc.content" class="form-control" id="content" rows="4" required></textarea>
      </div>

      <div class="mb-3">
        <label for="locale" class="form-label">Localidade</label>
        <input v-model="doc.locale" type="text" class="form-control" id="locale" placeholder="Opcional" />
      </div>

      <div class="mb-3">
        <label for="author" class="form-label">Autor (E-mail)</label>
        <input v-model="doc.author" type="email" class="form-control" id="author" required />
      </div>

      <div class="button-container">
        <button type="submit" class="btn btn-primary">Salvar</button>
        <button @click="cancel" class="btn btn-secondary">Cancelar</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import ApiService from "../services/ApiService";

const router = useRouter();
const route = useRoute();
const isEdit = ref(false);
const doc = ref({
  subject: "",
  content: "",
  locale: "",
  author: "",
});

const fetchDocument = async (id) => {
  try {
    const response = await ApiService.getDocumentById(id);
    doc.value = response.data;
  } catch (error) {
    console.error("Erro ao carregar documento:", error);
  }
};

const saveDocument = async () => {
  try {
    if (isEdit.value) {
      await ApiService.updateDocument(doc.value.id, doc.value);
    } else {
      await ApiService.createDocument(doc.value);
    }
    router.push({ name: "DocumentList" });
  } catch (error) {
    console.error("Erro ao salvar documento:", error);
  }
};

const cancel = () => {
  router.push({ name: "DocumentList" });
};

onMounted(() => {
  if (route.params.id) {
    isEdit.value = true;
    fetchDocument(route.params.id);
  }
});
</script>

<style scoped>
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.document-form {
  display: flex;
  flex-direction: column;
}

.button-container {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
}
</style>