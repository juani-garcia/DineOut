import styled from 'styled-components'
import { WhiteBoxContainer } from '@/components/Elements/utils/styles'

export const NeedsLogInContainer = styled(WhiteBoxContainer)`
  height: 500px;
  width: 80%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: row;
  gap: 300px;
  font-size: 13rem;

  transition: all 0.2s ease-in-out;

  &:hover {
    transform: scale(1.1);
  }
`
