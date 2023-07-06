import styled from 'styled-components'

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
  padding-left: 0.5rem;
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

export const LoginButton = styled.button`
  margin-top: -2px;
  margin-left: 15px;
  margin-right: 15px;
  text-transform: none;
  border-radius: 10px;
  font-weight: 700;
  position: relative;
  cursor: pointer;
  display: inline-block;
  overflow: hidden;
  user-select: none;
  vertical-align: middle;
  z-index: 1;
  height: 32.4px;
  line-height: 32.4px;
  font-size: 13px;
  text-decoration: none;
  text-align: center;
  letter-spacing: .5px;
  outline: 0;
  border: none;
  padding: 0 16px;
  background-color: ${(props) => props.theme.defaultWhite};
  color: ${(props) => props.theme.black} !important;
  box-sizing: inherit;
  list-style-type: none;
  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.12), 0 1px 5px 0 rgba(0, 0, 0, 0.2);
  font-family: inherit;


  transition: all 0.2s ease-in-out;

  &:hover {
    transform: scale(1.1);
  }
`

export const ListItem = styled.li`
  list-style-type: none;
  transition: background-color .3s;
  float: left;
  padding: 0;
  box-sizing: inherit;
  line-height: 64px;
`
