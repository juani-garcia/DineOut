import { useMethod } from './useMethod'
import { paths } from '@/common/const'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useUpdatePassword () {
  const { isLoading, requestMethod } = useMethod()

  async function updatePassword (password: string, token: string, userId: string): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.PATCH,
      url: paths.USERS + `/${userId}`,
      data: {
        password
      },
      passwordRecoveryToken: token
    })
  }

  return { isLoading, updatePassword }
}
