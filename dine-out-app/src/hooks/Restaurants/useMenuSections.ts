import { useMethod } from '../auth/useMethod'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useMenuSections () {
  const { isLoading, requestMethod } = useMethod()

  async function menuSections (uri: string): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.GET,
      url: uri
    })
  }

  return { isLoading, menuSections }
}
