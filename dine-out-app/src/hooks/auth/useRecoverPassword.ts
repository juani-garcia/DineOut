import { useMethod } from './useMethod'
import { paths } from '@/common/const'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useRecoverPassword () {
  const { isLoading, requestMethod } = useMethod()

  async function recoverPassword (username: string): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.POST,
      url: paths.USERS + '/password-recovery-token',
      data: {
        username
      }
    })
  }

  return { isLoading, passwordRecovery: recoverPassword }
}
