import styled from 'styled-components'

export const MainContainer = styled.div`
  background: linear-gradient(90deg, ${(props) => props.theme.backgroundTop} 0%, ${(props) => props.theme.backgroundBottom} 100%);
  min-height: 100vh;
  width: 100vw;
  display: flex;
  flex-direction: column;
`

export const BodyContainer = styled.div`
  width: 100vw;
  flex: 1;
  padding-top: 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;
`
