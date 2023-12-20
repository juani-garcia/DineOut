import { useMethod } from '../auth/useMethod'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useDeleteReview () {
  const { isLoading, requestMethod } = useMethod()

  async function deleteReview (reservation: string): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.DELETE,
      url: reservation
    })
  }

  return { isLoading, deleteReview }
}
