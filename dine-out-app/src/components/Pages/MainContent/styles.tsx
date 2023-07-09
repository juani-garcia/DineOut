import styled from 'styled-components'
import { BoxContainer } from '../../Elements/utils/styles'

export const Catchphrase = styled.h5`
  font-weight: 700;
  color: #FFFFFF !important;
  text-align: center;
  font-size: 1.64rem;
  line-height: 110%;
  margin: 0 0 0.656rem 0;
`

export const WhoAreWeContainer = styled(BoxContainer)`
  background-color: ${(props) => props.theme.defaultDark};
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 25px;
  padding-bottom: 25px;
  width: 90%;
  color: ${(props) => props.theme.defaultWhite};
`

export const WhoAreWeTitle = styled.h4`
  font-weight: ${(props) => props.theme.boldWeight};
  font-size: 2.28rem;
  line-height: 110%;
  margin: 1.52rem 0 0.912rem 0;
`

export const WhoAreWeText = styled.h6`
  font-weight: ${(props) => props.theme.lightWeight};
  font-size: 1.15rem;
  line-height: 110%;
  margin: 0.7666666667rem 0 0.46rem 0;
`
