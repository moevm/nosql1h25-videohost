/// <reference types="vite/client" />
import type { FunctionalComponent } from 'vue';
import type { Notifications } from '@kyvg/vue3-notification';
declare module 'vue' {
  export interface GlobalComponents {
    Notifications: FunctionalComponent<Notifications>;
  }
}

interface ImportMetaEnv {
  readonly VITE_API: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
