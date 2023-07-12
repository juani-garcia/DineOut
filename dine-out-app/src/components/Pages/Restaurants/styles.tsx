import styled from 'styled-components'
import { BoxContainer } from '@/components/Elements/utils/styles'

export const NoRestaurantsContainer = styled(BoxContainer)`
  background-color: #FFFFFF;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 25px;
  padding-top: 25px;
  padding-bottom: 25px;
  width: 90%;
  text-align: center;
  color: ${(props) => props.theme.defaultWhite};
`

export const NoRestaurantsText = styled.h1`
  color: ${(props) => props.theme.defaultLight};
  text-align: center;
  margin: 2.8rem 0 2.8rem 0;
  font-size: 4.2rem;
  line-height: 110%;
  font-weight: 400;
  
`
