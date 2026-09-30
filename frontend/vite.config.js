import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()], // <-- .vue 파일을 해석해 주는 플러그인 등록
    server: {
        port: 5173,
        open: true,
    },
});