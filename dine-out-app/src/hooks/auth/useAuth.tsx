import React, { createContext, type ReactNode, useContext, useState } from 'react'
import type User from '@/types/models/User'
import type AuthContextType from '@/types/AuthContextType'
import jwtDecode from 'jwt-decode'
import { roles } from '@/common/const'

// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
export const AuthContext = createContext<AuthContextType>(null!)

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function AuthProvider ({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(() => {
    const userInfo = localStorage.getItem('DineOut_token')?.split(' ')[1]
    if (userInfo == null) return null
    return jwtDecode(userInfo)
  })

  const getToken = (): string | null => {
    return localStorage.getItem('DineOut_token')
  }

  const getRefreshToken = (): string | null => {
    return localStorage.getItem('DineOut_refreshToken')
  }

  const setUserRestaurantId = (resId: number): void => {
    setUser((prevUser) => {
      if (prevUser != null) {
        return { ...prevUser, restaurantId: resId }
      }
      return null
    })
  }

  const setRoleRestaurant = (): void => {
    setUser((prevUser) => {
      if (prevUser != null) {
        return { ...prevUser, roles: roles.RESTAURANT }
      }
      return null
    })
  }

  const setRoleDiner = (): void => {
    setUser((prevUser) => {
      if (prevUser != null) {
        return { ...prevUser, roles: roles.DINER }
      }
      return null
    })
  }

  const setToken = (token: string | null): void => {
    if (token != null) {
      localStorage.setItem('DineOut_token', token)
      setUser(jwtDecode(token.split(' ')[1]))
    } else {
      localStorage.removeItem('DineOut_token')
    }
  }

  const setRefreshToken = (token: string | null): void => {
    if (token != null) {
      localStorage.setItem('DineOut_refreshToken', token)
    } else {
      localStorage.removeItem('DineOut_refreshToken')
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
    setUserRestaurantId,
    setRoleRestaurant,
    setRoleDiner,
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
