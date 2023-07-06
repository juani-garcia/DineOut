import React from 'react'
import { Logo, Navigation, NavigationContainer, NavTitle, PlaceHolder } from './styles'
import { useLogin } from '../../../hooks/auth/useLogin'

function Navbar (): JSX.Element {
  const { isLoading, login } = useLogin()
  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  const handleLogin = async () => {
    console.log('me parece que me voy a escribir muchas veces')
    await login('test@test.com', 'testtest').then(() => {
      console.log('termine')
    })
  }

  return (
        <Navigation>
            <NavigationContainer>
                <NavTitle id="logo-container" href="#">Dine Out
                    <Logo src="https://images.emojiterra.com/google/android-pie/512px/1f35c.png"/>
                </NavTitle>
                <PlaceHolder>
                    {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                    <button onClick={handleLogin}>FIumba</button>
                </PlaceHolder>
                <p>{isLoading ? 'fiumba' : 'fiumbant'}</p>
            </NavigationContainer>
        </Navigation>
  )
}

export default Navbar
