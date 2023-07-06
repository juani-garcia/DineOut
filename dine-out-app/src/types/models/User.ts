export default interface User {
  id: number
  username: string
  firstName: string
  lastName: string
  locale: string
  token?: string
  self?: string
}
