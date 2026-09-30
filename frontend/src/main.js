import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';

// 애플리케이션 생성
const app = createApp(App);

// Pinia 상태 관리 플러그인 등록
app.use(createPinia());

// 앱 마운트
app.mount('#app');