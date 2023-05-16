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

export const PlaceHolder = styled.p`
  float: right;
`
