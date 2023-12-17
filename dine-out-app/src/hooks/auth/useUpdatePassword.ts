import { useMethod } from './useMethod'
import { paths } from '@/common/const'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useUpdatePassword () {
  const { isLoading, requestMethod } = useMethod()

  async function updatePassword (password: string, token: string): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.PUT,
      url: paths.USERS + '/password-recovery-token/' + token,
      data: {
        password
      }
    })
  }

  return { isLoading, updatePassword }
}
