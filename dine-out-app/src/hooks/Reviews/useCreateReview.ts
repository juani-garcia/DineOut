import { useMethod } from '../auth/useMethod'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useCreateReview () {
  const { isLoading, requestMethod } = useMethod()

  async function createReview (reservation: string, restaurantId: number, rating: number, review: string): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.POST,
      url: reservation,
      data: {
        restaurantId,
        rating,
        review
      }
    })
  }

  return { isLoading, createReview }
}
