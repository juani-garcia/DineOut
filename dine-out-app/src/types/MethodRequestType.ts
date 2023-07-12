export default interface MethodRequestType {
  basic?: string
  url: string
  method: string
  headers?: Record<string, string>
  data?: object
  params?: Record<string, string>
}
