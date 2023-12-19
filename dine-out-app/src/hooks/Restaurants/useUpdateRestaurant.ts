import { useMethod } from '@/hooks/auth/useMethod'
import { paths } from '@/common/const'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useUpdateRestaurant () {
  const { isLoading, requestMethod } = useMethod()

  async function updateRestaurant (resId: number, name: string, address: string, email: string, detail: string, zone: string, lat: number, lng: number, categories: string[], shifts: string[], menuSectionsOrder: string[]): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.PUT,
      url: paths.API_URL + paths.RESTAURANTS + '/' + resId.toString(),
      data: {
        name,
        address,
        email,
        detail,
        zone,
        lat,
        lng,
        categories,
        shifts,
        menuSectionsOrder
      }
    })
  }

  return { isLoading, updateRestaurant }
}
