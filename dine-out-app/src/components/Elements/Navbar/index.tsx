import React from 'react'
import {
  ListItem,
  LoginButton,
  Logo,
  LogoutButton,
  Navigation,
  NavigationContainer,
  NavRight,
  NavTitle,
  RegisterButton
} from './styles'
import { useTranslation } from 'react-i18next'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '@/hooks/auth/useAuth'

function Navbar (): JSX.Element {
  const { t } = useTranslation()
  const { user, logout } = useAuth()
  const navigate = useNavigate()

  return (
        <Navigation>
            <NavigationContainer>
                <NavTitle id="logo-container" href="#" as={Link} to="/">Dine Out
                    <Logo src="https://images.emojiterra.com/google/android-pie/512px/1f35c.png"/>
                </NavTitle>

                {user == null
                  ? <NavRight>
                        <ListItem>
                            <RegisterButton onClick={() => {
                              navigate('/register', {
                                state: { from: window.location.pathname }
                              })
                            }}>
                                {t('register')}
                            </RegisterButton>
                        </ListItem>
                        <ListItem>
                            <LoginButton onClick={() => {
                              navigate('/login', {
                                state: { from: window.location.pathname }
                              })
                            }}>
                                {t('login')}
                            </LoginButton>
                        </ListItem>
                    </NavRight>
                  : <NavRight>
                        <ListItem>Boeeenas {user.sub}</ListItem>
                        <ListItem><LogoutButton onClick={() => {
                          logout()
                        }}>Chau chau</LogoutButton></ListItem>
                    </NavRight>}

            </NavigationContainer>
        </Navigation>
  )
}

export default Navbar
