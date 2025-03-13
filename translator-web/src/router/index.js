import { createRouter, createWebHistory } from 'vue-router';
import DocumentList from '../views/DocumentList.vue';
import TranslatorList from '../views/TranslatorList.vue';
import DocumentForm from '../views/DocumentForm.vue';
import TranslatorForm from '../views/TranslatorForm.vue';

const routes = [
  { path: '/', name: 'Home', component: () => import('../views/Home.vue') },
  { path: '/documents', name: 'DocumentList', component: DocumentList },
  { path: '/translators', name: 'TranslatorList', component: TranslatorList },
  { path: '/documents/new', name: 'NewDocument', component: DocumentForm },
  { path: '/documents/edit/:id', name: 'EditDocument', component: DocumentForm },
  { path: '/translators/new', name: 'NewTranslator', component: TranslatorForm },
  { path: '/translators/edit/:id', name: 'EditTranslator', component: TranslatorForm },
  
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;