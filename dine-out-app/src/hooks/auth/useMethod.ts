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

  async function requestMethod (request: MethodRequestType, retry: boolean = false): Promise<AxiosResponse> {
    setIsLoading(true)

    const refreshToken = getRefreshToken()
    const token = getToken()

    if (request.basic != null) {
      request.headers = {
        ...request.headers,
        Authorization: `Basic ${request.basic}`
      }
    } else if (token != null && !retry) {
      request.headers = {
        ...request.headers,
        Authorization: `${token}`
      }
    }

    return await axios({
      url: request.url,
      method: request.method,
      headers: request.headers,
      data: request.data,
      params: request.params
    }).then(response => {
      if (response.headers[DineOutHeaders.JWT_HEADER] != null) {
        setToken(response.headers[DineOutHeaders.JWT_HEADER])
      }
      if (response.headers[DineOutHeaders.REFRESH_TOKEN_HEADER] != null) {
        setRefreshToken(response.headers[DineOutHeaders.REFRESH_TOKEN_HEADER])
      }

      setIsLoading(false)
      return response
    }
    ).catch(async e => {
      if (e.response?.status === 404 || e.response?.status === 400) {
        setIsLoading(false)
        return e.response
      }
      if (e.response?.status > 400 && e.response?.status < 500 && request.basic == null) {
        if (token == null && refreshToken == null) {
          logout()
          setIsLoading(false)
          navigate('/login', {
            state: { from: window.location.pathname.replace('/paw-2022a-10', '') + window.location.search }
          })
          return e.response
        }

        if (retry) {
          return e.response
        }

        return await requestMethod(request, true)
      }
      setIsLoading(false) // Failed credentials for login or other HTTP status code.
      return e.response
    }).finally(() => {
      setIsLoading(false)
    })
  }

  return { isLoading, requestMethod }
}
