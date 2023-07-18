import styled from 'styled-components'
import { WhiteBoxContainer } from '@/components/Elements/utils/styles'

export const RestaurantBlackBoxContainer = styled(WhiteBoxContainer)`
  background-color: ${(props) => props.theme.defaultDark};
`

export const RestaurantTitle = styled.h1`
  color:  ${(props) => props.theme.defaultWhite};
  width: '100%';

`