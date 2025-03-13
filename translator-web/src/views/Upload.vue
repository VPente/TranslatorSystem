<template>
    <div>
      <h2>Upload de Arquivos</h2>
      <input type="file" @change="handleFile" />
    </div>
  </template>
  
  <script setup>
  import { useStore } from '../store';
  
  const store = useStore();
  
  const handleFile = (event) => {
    const file = event.target.files[0];
    store.addData(file.name); // Exemplo de ação
  };
  </script>