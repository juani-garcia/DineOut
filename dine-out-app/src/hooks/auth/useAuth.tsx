import React, { createContext, type ReactNode, useContext, useState } from 'react'
import type User from '../../types/models/User'
import type AuthContextType from '../../types/AuthContextType'

// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
export const AuthContext = createContext<AuthContextType>(null!)

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function AuthProvider ({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null)// TODO: What would be the default value?

  const getToken = (): string | null => {
    return localStorage.getItem('token') // TODO: Check if valid to store token in localStorage
  }

  const getRefreshToken = (): string | null => {
    return localStorage.getItem('refreshToken')
  }

  const setToken = (token: string | null): void => {
    // TODO
  }

  const setRefreshToken = (token: string | null): void => {
    // TODO
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
