import React from 'react'
import { Logo, Navigation, NavigationContainer, NavTitle, PlaceHolder } from './styles'

function Navbar (): JSX.Element {
  return (
        <Navigation>
            <NavigationContainer>
                <NavTitle id="logo-container" href="#">Dine Out
                    <Logo src="https://images.emojiterra.com/google/android-pie/512px/1f35c.png"/>
                </NavTitle>
                <PlaceHolder/>
            </NavigationContainer>
        </Navigation>
  )
}

export default Navbar
