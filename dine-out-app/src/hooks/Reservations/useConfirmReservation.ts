import { useMethod } from '../auth/useMethod'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'
import type Reservation from '@/types/models/Reservation'
import { paths } from '@/common/const'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useConfirmReservation () {
  const { isLoading, requestMethod } = useMethod()

  async function confirmReservation (reservation: Reservation): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.PUT,
      url: paths.API_URL + paths.RESERVATION + `/${reservation.id}`,
      data: {
        isConfirmed: true
      }
    })
  }

  return { isLoading, confirmReservation }
}
