import styled from 'styled-components'
import { WhiteBoxContainer } from '@/components/Elements/utils/styles'

export const LoginForm = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
  width: 50%;
  margin-left: 25%;
  margin-top: 30px;
  margin-bottom: 30px;

`

export const LoginWhiteBoxContainer = styled(WhiteBoxContainer)`
  width: 60%
`

export const RedirectionFooter = styled.div`
  width: 50%;
  margin-left: 25%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
  height: 60px;
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
  margin-top: auto;
  margin-bottom: auto;

  cursor: pointer;

  transition: all 0.2s ease-in-out;


  &:hover {
    transform: scale(1.1);
  }
`
