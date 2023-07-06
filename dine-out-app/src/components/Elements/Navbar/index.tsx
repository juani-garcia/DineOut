import React from 'react'
import {
  ListItem,
  LoginButton,
  Logo,
  Navigation,
  NavigationContainer,
  NavRight,
  NavTitle,
  RegisterButton
} from './styles'
import { useTranslation } from 'react-i18next'
import { Link } from 'react-router-dom'

function Navbar (): JSX.Element {
  const { t } = useTranslation()

  return (
        <Navigation>
            <NavigationContainer>
                <NavTitle id="logo-container" href="#" as={Link} to="/">Dine Out
                    <Logo src="https://images.emojiterra.com/google/android-pie/512px/1f35c.png"/>
                </NavTitle>
                <NavRight>
                    {/*    TODO: if logged in show "Hello {user}!" */}
                    <ListItem>
                        <RegisterButton>{t('register')}</RegisterButton>
                    </ListItem>
                    <ListItem>
                        <LoginButton as={Link} to="/login">
                            {t('login')}
                        </LoginButton>
                    </ListItem>
                </NavRight>
            </NavigationContainer>
        </Navigation>
  )
}

export default Navbar
