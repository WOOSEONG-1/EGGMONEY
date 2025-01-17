import { fileURLToPath, URL } from "node:url"
import { defineConfig, loadEnv } from "vite"
import vue from "@vitejs/plugin-vue"
import vueDevTools from "vite-plugin-vue-devtools"
import { VitePWA } from "vite-plugin-pwa"

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd())
  console.log(env.VITE_BASE_URL)
  console.log("Loaded Environment Variables:", env)
  return {
    plugins: [
      vue(),
      vueDevTools(),
      VitePWA({
        registerType: "autoUpdate",
        workbox: {
          maximumFileSizeToCacheInBytes: 5 * 1024 ** 2,
          runtimeCaching: [
            {
              urlPattern: /^https?:\/\/.*\/api\/kakao\/login/,
              handler: "NetworkFirst",
            },
          ],
        },
        devOptions: {
          enabled: true,
        },
        srcDir: "public",
        filename: "sw.js",
        manifest: {
          name: "에그머니",
          short_name: "에그머니",
          description: "금융활동을 미리 경험해보세요",
          theme_color: "#FF5A00",
          background_color: "#F3F4F6",
          icons: [
            {
              src: "img/icons/android-chrome-192x192.png",
              sizes: "192x192",
              type: "image/png",
            },
            {
              src: "/img/icons/android-chrome-512x512.png",
              sizes: "512x512",
              type: "image/png",
            },
          ],
        },
      }),
    ],
    resolve: {
      alias: {
        "@": fileURLToPath(new URL("./src", import.meta.url)),
      },
    },
    server: {
      proxy: {
        [env.VITE_BASE_URL]: {
          target: env.VITE_SERVER_URL, // 환경 변수에서 서버 URL 가져오기
          changeOrigin: true,
          secure: false,
          rewrite: (path) => path.replace(new RegExp(`^${env.VITE_BASE_URL}`), ""),
        },
        // '/dpi': {
        //   target: 'https://oracle1.mypjt.xyz/api',
        //   changeOrigin: true,
        //   secure: true,
        //   rewrite: (path) => path.replace(/^\/dpi/, ""),
        // }
      },      
    },
  }
})
