import styled from 'styled-components'
import { BoxContainer } from '../../utils/styles'

export const SearchBoxContainer = styled(BoxContainer)`
  background-color: ${(props) => props.theme.defaultWhite};
  margin-top: 30px;
  margin-bottom: 30px;
  width: 90%;
`
