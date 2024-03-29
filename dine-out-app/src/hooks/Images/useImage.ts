import { useMethod } from '../auth/useMethod'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export default function useImage () {
  const { isLoading, requestMethod } = useMethod()
  const { isLoading: ild, requestMethod: rmd } = useMethod()

  async function updateImage (uri: string, image: any): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.PUT,
      url: uri,
      data: {
        image
      },
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  }

  async function deleteImage (uri: string): Promise<AxiosResponse> {
    return await rmd({
      method: HttpMethod.DELETE,
      url: uri
    })
  }

  return { isLoading, ild, updateImage, deleteImage }
}
