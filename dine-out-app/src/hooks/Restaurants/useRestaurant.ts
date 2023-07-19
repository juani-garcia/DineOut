import { useMethod } from '../auth/useMethod'
import { paths } from '@/common/const'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export default function useRestaurant () {
  const { isLoading, requestMethod } = useMethod()

  async function restaurant (id: number): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.GET,
      url: `${paths.API_URL}${paths.RESTAURANTS}/${id}`
    })
  }

  return { isLoading, restaurant }
}
