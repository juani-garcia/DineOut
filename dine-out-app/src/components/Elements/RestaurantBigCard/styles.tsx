import styled from 'styled-components'
import { Button, WhiteBoxContainer } from '@/components/Elements/utils/styles'

export const RestaurantBlackBoxContainer = styled(WhiteBoxContainer)`
  background-color: ${(props) => props.theme.defaultDark};
`

export const RestaurantTitle = styled.h1`
  color:  ${(props) => props.theme.defaultWhite};
  width: '100%';

`

export const WhiteButton = styled(Button)`
  background-color: ${(props) => props.theme.defaultWhite};
  color: ${(props) => props.theme.black} !important;
`