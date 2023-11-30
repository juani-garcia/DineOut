import { useAuth } from './useAuth'
import { useState } from 'react'
import axios, { type AxiosResponse } from 'axios'
import type MethodRequestType from '@/types/MethodRequestType'
import { useNavigate } from 'react-router-dom'
import { DineOutHeaders } from '@/common/const'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export const useMethod = () => {
  const [isLoading, setIsLoading] = useState(false)

  const {
    getToken,
    setToken,
    getRefreshToken,
    setRefreshToken,
    logout
  } = useAuth()

  const navigate = useNavigate()

  async function requestMethod (request: MethodRequestType): Promise<AxiosResponse> {
    setIsLoading(true)

    const refreshToken = getRefreshToken()
    const token = getToken()

    if (request.basic != null) {
      request.headers = {
        [DineOutHeaders.AUTH_HEADER]: `Basic ${request.basic}`,
        ...request.headers
      }
    } else if (token != null) {
      request.headers = {
        ...request.headers,
        [DineOutHeaders.AUTH_HEADER]: `${token}`
      }
    } else if (refreshToken != null) {
      request.headers = {
        ...request.headers,
        [DineOutHeaders.AUTH_HEADER]: `${refreshToken}`
      }
    }

    const response = await axios({
      url: request.url,
      method: request.method,
      headers: request.headers,
      data: request.data,
      params: request.params
    }).then(response => {
      if (response.headers[DineOutHeaders.AUTH_HEADER] != null) setToken(response.headers.authorization)
      if (response.headers[DineOutHeaders.REFRESH_TOKEN_HEADER] != null) setRefreshToken(response.headers[DineOutHeaders.REFRESH_TOKEN_HEADER])

      setIsLoading(false)
      return response
    }
    ).catch(async e => {
      if (e.response?.status === 404 || e.response?.status === 400) {
        setIsLoading(false)
        return e.response
      }
      // TODO: Change logic, if not authorized it should try with refresh token but keep the token. If I am authenticated but try to acces something I can not, it should just say so not make me login again, roles could be added to avoid this.
      if (e.response?.status > 400 && e.response?.status < 500 && request.basic == null) {
        if (token == null && refreshToken == null) {
          logout()
          setIsLoading(false)
          navigate('/login')
          return e.response
        }

        if (token != null) setToken(null)
        else if (refreshToken != null) setRefreshToken(null)

        // eslint-disable-next-line @typescript-eslint/no-dynamic-delete
        if (request.headers != null) delete request.headers[DineOutHeaders.AUTH_HEADER]

        return await requestMethod(request)
      }
      setIsLoading(false) // Failed credentials for login or other HTTP status code.
      return e.response
    }).finally(() => {
      setIsLoading(false)
    })

    return response
  }

  return { isLoading, requestMethod }
}
