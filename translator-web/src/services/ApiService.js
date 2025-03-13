import axios from 'axios';

// URL base da API, ajuste conforme o endereço do seu backend
const API_URL = 'http://localhost:8080/api';

export default {
  // GET /api/translators - Obter todos os tradutores
  getAllTranslators: () => axios.get(`${API_URL}/translators`),

  // GET /api/translators/{id} - Obter um tradutor por ID
  getTranslatorById: (id) => axios.get(`${API_URL}/translators/${id}`),

  // POST /api/translators - Criar um novo tradutor
  createTranslator: (translator) => axios.post(`${API_URL}/translators`, translator),

  // PUT /api/translators/{id} - Atualizar um tradutor existente
  updateTranslator: (id, translator) => axios.put(`${API_URL}/translators/${id}`, translator),

  // DELETE /api/translators/{id} - Deletar um tradutor
  deleteTranslator: (id) => axios.delete(`${API_URL}/translators/${id}`),

    // Documents

   // GET /api/documents - get all documents
   getAllDocuments: () => axios.get(`${API_URL}/documents`),

   // GET /api/documents/{id} - Get document per ID
   getDocumentById: (id) => axios.get(`${API_URL}/documents/${id}`),
 
   // POST /api/documents - Create a document 
   createDocument: (translator) => axios.post(`${API_URL}/documents`, translator),
 
   // PUT /api/documents/{id} - Update an existing translator
   updateDocument: (id, translator) => axios.put(`${API_URL}/documents/${id}`, translator),
 
   // DELETE /api/documents/{id} - Delete a document
   deleteDocument: (id) => axios.delete(`${API_URL}/documents/${id}`),
};