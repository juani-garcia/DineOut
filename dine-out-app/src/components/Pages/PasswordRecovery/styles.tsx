import styled from 'styled-components'
import { WhiteBoxContainer } from '@/components/Elements/utils/styles'

export const RecoveryForm = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
  width: 50%;
  margin-left: 25%;
  padding: 1.5rem 0;
`

export const RecoveryWhiteBoxContainer = styled(WhiteBoxContainer)`
  width: 60%
`

export const Header = styled.h5`
  font-weight: 400;
  color: #000000 !important;
  text-align: center;
  font-size: 2.28rem;
  line-height: 110%;
  margin: 1.52rem 0 0.912rem 0;
`

export const RedirectionFooter = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  height: 60px;
  text-align: center;
`

export const LinkTo = styled.h4`
  text-decoration: underline;
  font-weight: 100;
  color: #242B2A !important;
  background-color: transparent;
  box-sizing: inherit;
  font-size: 1.15rem;
  text-align: center;
  line-height: 110%;
  margin: auto; /* Center vertically and horizontally */

  cursor: pointer;

  transition: all 0.2s ease-in-out;

  &:hover {
    transform: scale(1.1);
  }
`
