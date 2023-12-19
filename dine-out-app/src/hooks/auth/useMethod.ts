import { useAuth } from './useAuth'
import { useState } from 'react'
import axios, { type AxiosResponse, HttpStatusCode } from 'axios'
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
  const isTokenExpired = (token: string): boolean => {
    const [, payloadBase64] = token.split('.')
    const payload = JSON.parse(atob(payloadBase64))
    return payload.exp * 1000 < Date.now()
  }

  const navigate = useNavigate()

  async function requestMethod (request: MethodRequestType, retry: boolean = false): Promise<AxiosResponse> {
    setIsLoading(true)

    let token = getToken()
    if (token != null && isTokenExpired(token)) {
      token = getRefreshToken()
    }
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
    } else if (request.passwordRecoveryToken != null) {
      request.headers = {
        ...request.headers,
        // Bearer is added because token is passed through params
        Authorization: `Bearer ${request.passwordRecoveryToken.toString()}`
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
      if (e.response?.status === HttpStatusCode.NotFound || e.response?.status === HttpStatusCode.BadRequest) {
        setIsLoading(false)
        return e.response
      }

      if (e.response?.status === HttpStatusCode.Unauthorized && request.passwordRecoveryToken !== null) {
        return e.response
      }

      if (e.response?.status > 400 && e.response?.status < 500 && request.basic == null) {
        if (token == null) {
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
