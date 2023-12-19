import { useMethod } from '../auth/useMethod'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'
import type Reservation from '@/types/models/Reservation'
import { paths } from '@/common/const'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useDeleteReservation () {
  const { isLoading, requestMethod } = useMethod()

  async function deleteReservation (reservation: Reservation): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.DELETE,
      url: paths.API_URL + paths.RESERVATION + `/${reservation.id}`
    })
  }

  return { isLoading, deleteReservation }
}
