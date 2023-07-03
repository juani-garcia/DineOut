import axios, { type AxiosResponse } from 'axios'
import { paths } from '../common/const'

class AuthService {
  async login (username: string, password: string): Promise<AxiosResponse> {
    const basic = btoa(username + ':' + password)

    console.log(basic)
    console.log(paths.API_URL + paths.LOGIN)

    return await axios.post(paths.API_URL + paths.LOGIN, {}, {
      headers: {
        Authorization: `Basic ${basic}`
      }
    }
    ).then(response => {
      console.log(response.data)
      console.log(response.headers)
      console.log(response.status)

      return response
    }
    ).catch(error => {
      console.log(error)
      console.log(error.response)
      console.log('flaco te mandaste un re moco')

      return error.response
    }
    )
  }
}

export default new AuthService()
