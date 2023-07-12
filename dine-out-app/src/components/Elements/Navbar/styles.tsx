import styled from 'styled-components'
import { Button } from '@/components/Elements/utils/styles'

export const Navigation = styled.nav`
  width: 100vw;
  display: flex;
  justify-content: space-between;
  height: 64px;
  line-height: 64px;
`

export const NavigationContainer = styled.div`
  margin: 0 auto;
  width: 90%;
  max-width: 1480px;
  display: flex;
  justify-content: space-between;
`

export const NavTitle = styled.a`
  color: #FFFFFF;
  transition: all 0.2s ease-in-out;
  padding: 0 15px;
  font-weight: 900;
  text-decoration: none;
  font-size: 2.1rem;

  &:hover {
    transform: scale(1.1);
  }
`

export const Logo = styled.img`
  padding-left: 0.5719rem;
  height: 2.1rem
`

export const NavRight = styled.ul`
  float: right;
  padding-left: 0;
  list-style-type: none;
  margin: 0;
  box-sizing: inherit;
  line-height: 64px;
  color: ${(props) => props.theme.defaultWhite};

`

export const RegisterButton = styled.button`
  font-weight: 300;
  color: ${(props) => props.theme.defaultWhite};
  padding: 0 15px;
  text-decoration: underline;
  font-size: 1rem;
  display: block;
  cursor: pointer;
  background-color: transparent;
  box-sizing: inherit;
  list-style-type: none;
  line-height: 64px;
  outline: 0;
  border: none;
  font-family: inherit;

  transition: all 0.2s ease-in-out;

  &:hover {
    transform: scale(1.1);
  }
`

export const LoginButton = styled(Button)`
  background-color: ${(props) => props.theme.defaultWhite};
  color: ${(props) => props.theme.black} !important;
`

export const LogoutButton = styled(Button)`
  background-color: ${(props) => props.theme.defaultRed};
  color: ${(props) => props.theme.defaultWhite} !important;
`

export const ListItem = styled.li`
  list-style-type: none;
  transition: background-color .3s;
  float: left;
  padding: 0;
  box-sizing: inherit;
  line-height: 64px;
`
