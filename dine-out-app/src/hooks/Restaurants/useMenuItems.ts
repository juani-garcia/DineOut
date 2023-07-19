import { useMethod } from '../auth/useMethod'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useMenuItems () {
  const { isLoading, requestMethod } = useMethod()
  const { isLoading: ild, requestMethod: rmd } = useMethod()
  const { requestMethod: rmc } = useMethod()

  async function menuItems (uri: string): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.GET,
      url: uri
    })
  }

  async function createMenuItem(uri: string, name: string, detail: string, price: number, section: number): Promise<AxiosResponse> {
    return await rmc({
      method: HttpMethod.POST,
      url: uri,
      data: {
        name,
        detail,
        price,
        section
      }
    })
  }

  async function deleteMenuItem (uri: string): Promise<AxiosResponse> {
    return await rmd({
      method: HttpMethod.DELETE,
      url: uri
    })
  }

  return { isLoading, ild, menuItems, createMenuItem, deleteMenuItem }
}
