import { useMethod } from '../auth/useMethod'
import { paths } from '@/common/const'
import { HttpMethod } from '@/types/enums/HTTPMethod'
import type User from '@/types/models/User'
import type { AxiosResponse } from 'axios'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useUpdateFavorite () {
  const { isLoading, requestMethod } = useMethod()

  async function updateFavorite (user: User, restaurantId: number, upVote: boolean): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.PUT,
      url: paths.USERS + `/${user.userId}/favorites/${restaurantId}`,
      data: {
        upVote
      }
    })
  }

  return { isLoading, updateFavorite }
}
