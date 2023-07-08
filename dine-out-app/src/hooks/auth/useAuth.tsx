import React, { createContext, type ReactNode, useContext, useState } from 'react'
import type User from '@/types/models/User'
import type AuthContextType from '@/types/AuthContextType'
import jwtDecode from 'jwt-decode'

// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
export const AuthContext = createContext<AuthContextType>(null!)

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function AuthProvider ({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(() => {
    const userInfo = getToken()?.split(' ')[1] // TODO: Check if works.
    if (userInfo == null) return null
    return jwtDecode(userInfo)
  })

  const getToken = (): string | null => {
    return localStorage.getItem('token')
  }

  const getRefreshToken = (): string | null => {
    return localStorage.getItem('refreshToken')
  }

  const setToken = (token: string | null): void => {
    if (token != null) {
      localStorage.setItem('token', token)
      setUser(jwtDecode(token.split(' ')[1]))
    } else {
      localStorage.removeItem('token')
    }
  }

  const setRefreshToken = (token: string | null): void => {
    if (token != null) {
      localStorage.setItem('refreshToken', token)
    } else {
      localStorage.removeItem('refreshToken')
    }
  }

  const logout = (): void => {
    setRefreshToken(null)
    setToken(null)
    setUser(null)
  }

  return <AuthContext.Provider value={{
    user,
    setUser,
    getToken,
    setToken,
    getRefreshToken,
    setRefreshToken,
    logout
  }}>
        {children}
    </AuthContext.Provider>
}

export function useAuth (): AuthContextType {
  return useContext(AuthContext)
}
