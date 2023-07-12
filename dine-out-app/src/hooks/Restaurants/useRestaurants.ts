import { useMethod } from '../auth/useMethod'
import { paths } from '@/common/const'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useRestaurants () {
  const { isLoading, requestMethod } = useMethod()

  async function restaurants (): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.GET,
      // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
      url: paths.API_URL + paths.RESTAURANTS
    })
  }

  return { isLoading, restaurants }
}
