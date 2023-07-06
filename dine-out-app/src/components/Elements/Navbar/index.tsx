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
import { useAuth } from '../../../hooks/auth/useAuth'

function Navbar (): JSX.Element {
  const { t } = useTranslation()
  const { user } = useAuth()

  return (
        <Navigation>
            <NavigationContainer>
                <NavTitle id="logo-container" href="#" as={Link} to="/">Dine Out
                    <Logo src="https://images.emojiterra.com/google/android-pie/512px/1f35c.png"/>
                </NavTitle>

                {user == null
                  ? <NavRight>
                        <ListItem>
                            <RegisterButton>{t('register')}</RegisterButton>
                        </ListItem>
                        <ListItem>
                            <LoginButton as={Link} to="/login">
                                {t('login')}
                            </LoginButton>
                        </ListItem>
                    </NavRight>
                  : <NavRight>
                        <ListItem>Boeeenas ${user.sub}</ListItem>
                    </NavRight>}

            </NavigationContainer>
        </Navigation>
  )
}

export default Navbar
