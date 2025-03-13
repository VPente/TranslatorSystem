<template>
    <div class="container">
      <h1>Welcome to the Management System</h1>
      
      <div class="card-container">
        <div class="card" @click="navigateToDocuments">
          <h4>Documents</h4>
          <p>Manage your documents here</p>
        </div>
        <div class="card" @click="navigateToTranslators">
          <h4>Translators</h4>
          <p>Manage your translators here</p>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    methods: {
      navigateToDocuments() {
        // lógica para navegar até a tela de documentos
        this.$router.push('/documents');
      },
      navigateToTranslators() {
        // lógica para navegar até a tela de tradutores
        this.$router.push('/translators');
      }
    }
  }
  </script>
  
  <style scoped>
  .container {
    text-align: center;
    padding: 20px;
  }
  
  .card-container {
    display: flex;
    justify-content: center;
    gap: 20px;
  }
  
  .card {
    margin-top: 35px;
    width: 40vw;
    padding: 20px;
    background-color: #f0f0f0;
    border-radius: 10px;
    cursor: pointer;
    transition: transform 0.2s ease-in-out;
    text-align: center;
    font-size: 10px;
  }
  
  .card:hover {
    transform: scale(1.05);
  }
  
  .card h2 {
    margin: 10px 0;
  }
  
  .card p {
    color: #666;
  }
  </style>