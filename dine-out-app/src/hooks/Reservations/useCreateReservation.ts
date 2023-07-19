import { useMethod } from '../auth/useMethod'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useCreateReservation () {
  const { isLoading, requestMethod } = useMethod()

  async function createReservation (reservation: string, restaurantId: number, comments: string, amount: number, date: string, time: string): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.POST,
      url: reservation,
      data: {
        restaurantId,
        comments,
        amount,
        date,
        time
      }
    })
  }

  return { isLoading, createReservation }
}
