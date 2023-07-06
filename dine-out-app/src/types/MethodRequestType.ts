export default interface MethodRequestType {
  basic?: string
  url: string
  method: string
  headers?: Record<string, string>
  data?: object
  needsAuth?: boolean
  params?: Record<string, string>
}
