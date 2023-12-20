import styled from 'styled-components'
import { WhiteBoxContainer } from '@/components/Elements/utils/styles'

export const ReviewBlackBoxContainer = styled(WhiteBoxContainer)`
  background-color: ${(props) => props.theme.defaultDark};
`

export const ReviewTitle = styled.h1`
  color:  ${(props) => props.theme.defaultWhite};
  width: '100%';
`

export const ReviewText = styled.div`
  color: ${(props) => props.theme.defaultWhite};
  margin-right: auto;
  font-weight: bold;
  max-width: 70%;
  overflow: hidden;
  text-overflow: ellipsis;
`

export const ReviewContainer = styled.div`
  display: flex; !important;
  flex-direction: row; !important;
  padding-top: 20px; !important;
  padding-bottom: 20px; !important;
`

export const MyReviewCardContainer = styled(WhiteBoxContainer)`
  max-width: 47%;
  display: flex;
  box-sizing: border-box;
  width: 100%;
  min-width: 500px;
  margin-right: 20px;
  margin-left: 20px;


  transition: all 0.2s ease-in-out;

  > .card-image {
    width: 35% !important;
    max-width: 35% !important;
  }

  > .card-content {
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  > .delete-button {
    margin-left: auto;
  }

  > .card-image + .card-content {
    width: 65%;
    overflow: hidden;
    text-overflow: ellipsis;
  }

`
