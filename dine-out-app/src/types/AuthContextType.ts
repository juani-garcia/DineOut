import type User from '../types/models/User'
import { type Dispatch, type SetStateAction } from 'react'

export default interface AuthContextType {
  user: User | null
  setUser: Dispatch<SetStateAction<User | null>>
  setUserRestaurantId: (resId: number) => void
  setRoleRestaurant: () => void
  setRoleDiner: () => void
  logout: () => void
  getToken: () => string | null
  setToken: (token: string | null) => void
  getRefreshToken: () => string | null
  setRefreshToken: (token: string | null) => void
}
