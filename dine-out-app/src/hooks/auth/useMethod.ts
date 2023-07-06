import { useAuth } from './useAuth'
import { useState } from 'react'
import axios, { type AxiosResponse, HttpStatusCode } from 'axios'
import type MethodRequestType from '../../types/MethodRequestType'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export const useMethod = () => {
  const [isLoading, setIsLoading] = useState(false)

  const {
    getToken,
    setToken,
    getRefreshToken,
    setRefreshToken
  } = useAuth()

  async function requestMethod (request: MethodRequestType): Promise<AxiosResponse> {
    setIsLoading(true)

    const refreshToken = getRefreshToken()
    const token = getToken()

    if (request.needsAuth === true) {
      if (refreshToken == null && token == null && request.basic == null) {
        // TODO: Handle redirect to login.
      }
    } else {
      request.headers = {
        Authorization: (request.basic != null)
          ? `Basic ${request.basic}`
          : (token != null) ? `${token}` : (refreshToken != null) ? `${refreshToken}` : ''
      }
    }

    const response = await axios({
      url: request.url,
      method: request.method,
      headers: request.headers,
      data: request.data,
      params: request.params
    }).then(
      // TODO: Update headers.
    ).catch(async e => {
      if (e.response?.status === HttpStatusCode.Unauthorized) {
        if (request.basic != null) return e.response
        if (token != null) setToken(null)
        else if (refreshToken != null) setRefreshToken(null)
        if (request.headers != null) delete request.headers.Authorization

        return await requestMethod(request) // TODO: Check if return is valid.
      }
    })

    setIsLoading(false)
    return response
  }

  return { isLoading, requestMethod }
}
