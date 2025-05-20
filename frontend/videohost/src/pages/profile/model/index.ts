export default interface IUser {
  userId: string
  username: string
  email: string
  registrationDate: string
  subscriberCount: number
  blocked: boolean
  roles: ['ADMIN' | 'USER']
}
