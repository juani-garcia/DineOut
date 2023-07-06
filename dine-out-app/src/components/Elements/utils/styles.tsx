import styled from 'styled-components'

export const Title = styled.h1`
  font-weight: 700;
  color: #FFFFFF !important;
  text-align: center;
  font-size: 4.2rem;
  margin: 2.8rem 0 2.8rem 0;
  line-height: 110%;
`

export const MyContainer = styled.div`
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  max-width: 1480px;
  width: 100%;
`

export const BoxContainer = styled.div`
  margin: 0 auto;
  max-width: 1480px;
  width: 100%;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  border-radius: 24px;
`

export const WhiteBoxContainer = styled(BoxContainer)`
  background-color: ${(props) => props.theme.defaultWhite};
  margin-top: 30px;
  margin-bottom: 30px;
  width: 90%;
`
