import { useMethod } from '@/hooks/auth/useMethod'
import { paths } from '@/common/const'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useCreateRestaurant () {
  const { isLoading, requestMethod } = useMethod()

  async function createRestaurant (name: string, address: string, email: string, detail: string, zone: string, lat: number, lng: number, categories: string[], shifts: string[]): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.POST,
      url: paths.API_URL + paths.RESTAURANTS,
      data: {
        name,
        address,
        email,
        detail,
        zone,
        lat,
        lng,
        categories,
        shifts
      }
    })
  }

  return { isLoading, createRestaurant }
}
