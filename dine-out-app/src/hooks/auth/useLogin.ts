import { useMethod } from './useMethod'
import { paths } from '../../common/const'
import { type AxiosResponse } from 'axios'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useLogin () {
  const { isLoading, requestMethod } = useMethod()

  async function login (username: string, password: string): Promise<AxiosResponse> {
    return await requestMethod({
      basic: btoa(username + ':' + password),
      method: 'GET', // TODO: Check with backend, get or post
      url: paths.API_URL + paths.USERS
    })
  }

  return { isLoading, login }
}
