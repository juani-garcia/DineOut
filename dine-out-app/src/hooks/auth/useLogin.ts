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

// class UseAuth {
//   async login (username: string, password: string): Promise<AxiosResponse> {
//     const basic = btoa(username + ':' + password)
//
//     console.log(basic)
//     console.log(paths.API_URL + paths.USERS)
//
//     return await axios.get(paths.API_URL + paths.USERS, {
//       headers: {
//         Authorization: `Basic ${basic}`
//       }
//     }
//     ).then(response => {
//       console.log('Respuesta')
//       console.log(response)
//       console.log(response.request.response as User)
//       console.log(response.request.response.json as User)
//       console.log(response.data)
//       console.log('usuario?')
//       console.log(response.data[0] as User)
//       console.log(response.headers)
//       console.log(response.status)
//
//       return response
//     }
//     ).catch(error => {
//       console.log(error)
//       console.log(error.response)
//       console.log('flaco te mandaste un re moco')
//
//       return error.response
//     }
//     )
//   }
// }
//
// // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
// export const useBetweenAuth = () => useBetween(useAuth)
