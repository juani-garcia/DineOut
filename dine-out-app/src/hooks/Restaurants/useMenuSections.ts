import { useMethod } from '../auth/useMethod'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useMenuSections () {
  const { isLoading, requestMethod } = useMethod()
  const { requestMethod: rmc } = useMethod()
  const { requestMethod: rmm } = useMethod()
  const { requestMethod: rmu } = useMethod()
  const { requestMethod: rmr } = useMethod()

  async function menuSections (uri: string): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.GET,
      url: uri
    })
  }

  async function readMenuSection (uri: string): Promise<AxiosResponse> {
    return await rmr({
      method: HttpMethod.GET,
      url: uri
    })
  }

  async function createMenuSection (uri: string, name: string): Promise<AxiosResponse> {
    return await rmc({
      method: HttpMethod.POST,
      url: uri,
      data: {
        name
      }
    })
  }

  async function moveSection (uri: string, up: boolean = true): Promise<AxiosResponse> {
    return await rmm({
      method: HttpMethod.PATCH,
      url: uri,
      params: {
        up: up ? 'true' : 'false'
      }
    })
  }

  async function updateMenuSection (uri: string, name: string): Promise<AxiosResponse> {
    return await rmu({
      method: HttpMethod.PUT,
      url: uri,
      data: {
        name
      }
    })
  }

  return { isLoading, menuSections, readMenuSection, createMenuSection, moveSection, updateMenuSection }
}
