import React from 'react'
import { Logo, Navigation, NavigationContainer, NavTitle, PlaceHolder } from './styles'
import AuthService from '../../../services/AuthService'

function Navbar (): JSX.Element {
  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  const handleLogin = async () => {
    await AuthService.login('test@test.com', 'testtest').then(() => {
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
            </NavigationContainer>
        </Navigation>
  )
}

export default Navbar
