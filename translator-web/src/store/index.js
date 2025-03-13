import { createPinia } from 'pinia';
import { defineStore } from 'pinia';

export const useStore = defineStore('main', {
  state: () => ({
    data: [],
  }),
  actions: {
    addData(item) {
      this.data.push(item);
    },
  },
});

export const pinia = createPinia();