import { useMethod } from '../auth/useMethod'
import { paths } from '@/common/const'
import { HttpMethod } from '@/types/enums/HTTPMethod'
import type User from '@/types/models/User'
import type { AxiosResponse } from 'axios'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useFavoriteStatus () {
  const { isLoading, requestMethod } = useMethod()

  async function checkFavorite (user: User, restaurantId: number): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.GET,
      url: paths.USERS + `/${user.userId}/favorites/${restaurantId}`
    })
  }

  return { isLoading, checkFavorite }
}
