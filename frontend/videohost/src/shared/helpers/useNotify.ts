import { useNotification } from '@kyvg/vue3-notification'

export const useNotify = () => {
  const { notify } = useNotification()

  const error = (message: string, text: string) => {
    return notify({
      type: 'error',
      title: message,
      text: text,
      duration: 70000
    })
  }

  const success = (message: string, text: string) => {
    return notify({
      type: 'success',
      title: message,
      text: text,
      duration: 70000
    })
  }

  return {
    error,
    success,
  }
}
