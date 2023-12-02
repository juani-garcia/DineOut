import { useMethod } from './useMethod'
import { paths } from '@/common/const'
import { type AxiosResponse } from 'axios'
import { HttpMethod } from '@/types/enums/HTTPMethod'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useRegister () {
  const { isLoading, requestMethod } = useMethod()

  async function register (firstName: string, lastName: string, username: string, password: string, confirmPassword: string, isRestaurant: boolean): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.POST,
      url: paths.USERS,
      data: {
        firstName,
        lastName,
        username,
        password,
        confirmPassword,
        isRestaurant
      }
    })
  }

  return { isLoading, register }
}
