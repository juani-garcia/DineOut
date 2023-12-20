import { useMethod } from '../auth/useMethod'
import { paths } from '@/common/const'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useReviews () {
  const { isLoading, requestMethod } = useMethod()

  async function reviews (queryParams: URLSearchParams): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.GET,
      params: Object.fromEntries(queryParams.entries()),
      // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
      url: paths.API_URL + paths.REVIEWS
    })
  }

  return { isLoading, reviews }
}
