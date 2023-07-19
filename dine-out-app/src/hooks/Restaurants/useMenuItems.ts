import { useMethod } from '../auth/useMethod'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useMenuItems () {
  const { isLoading, requestMethod } = useMethod()
  const { isLoading: ild, requestMethod: rmd } = useMethod()
  const { requestMethod: rmc } = useMethod()
  const { requestMethod: rmm } = useMethod()
  const { requestMethod: rmg } = useMethod()
  const { requestMethod: rmu } = useMethod()

  async function menuItems (uri: string): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.GET,
      url: uri
    })
  }

  async function getMenuItem(uri: string): Promise<AxiosResponse> {
    return await rmg({
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

  async function updateMenuItem(uri: string, name: string, detail: string, price: number, section: number): Promise<AxiosResponse> {
    return await rmu({
      method: HttpMethod.PUT,
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

  async function moveItem(uri: string, up: boolean = true): Promise<AxiosResponse> {
    return await rmm({
      method: HttpMethod.PATCH,
      url: uri,
      params: {
        "up": up ? "true" : "false"
      }
    })
  }

  return { isLoading, ild, menuItems, getMenuItem, createMenuItem, updateMenuItem, deleteMenuItem, moveItem }
}
