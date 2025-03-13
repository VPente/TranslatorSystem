<template>
    <div class="container">
      <h2>{{ isEdit ? "Edit Translator" : "New Translator" }}</h2>
  
      <form @submit.prevent="saveTranslator" class="translator-form">
        <div class="mb-3">
          <label for="name" class="form-label">Name</label>
          <input v-model="translator.name" type="text" class="form-control" id="name" required />
        </div>
  
        <div class="mb-3">
          <label for="email" class="form-label">Email</label>
          <input v-model="translator.email" type="email" class="form-control" id="email" required />
        </div>
  
        <div class="mb-3">
          <label for="sourceLanguage" class="form-label">Source Language</label>
          <input v-model="translator.sourceLanguage" type="text" class="form-control" id="sourceLanguage" required />
        </div>
  
        <div class="mb-3">
          <label for="targetLanguage" class="form-label">Target Language</label>
          <input v-model="translator.targetLanguage" type="text" class="form-control" id="targetLanguage" required />
        </div>
  
        <div class="button-container">
          <button type="submit" class="btn btn-primary">Save</button>
          <button @click="cancel" class="btn btn-secondary">Cancel</button>
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
  const translator = ref({
    name: "",
    email: "",
    sourceLanguage: "",
    targetLanguage: "",
  });
  
  const fetchTranslator = async (id) => {
    try {
      const response = await ApiService.getTranslatorById(id);
      translator.value = response.data;
    } catch (error) {
      console.error("Error fetching translator:", error);
    }
  };
  
  const saveTranslator = async () => {
    try {
      if (isEdit.value) {
        await ApiService.updateTranslator(translator.value.id, translator.value);
      } else {
        await ApiService.createTranslator(translator.value);
      }
      router.push({ name: "TranslatorList" });
    } catch (error) {
      console.error("Error saving translator:", error);
    }
  };
  
  const cancel = () => {
    router.push({ name: "TranslatorList" });
  };
  
  onMounted(() => {
    if (route.params.id) {
      isEdit.value = true;
      fetchTranslator(route.params.id);
    }
  });
  </script>
  
  <style scoped>
  .container {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
  }
  
  .translator-form {
    display: flex;
    flex-direction: column;
  }
  
  .button-container {
    display: flex;
    justify-content: space-between;
    margin-top: 15px;
  }
  </style>