import styled from 'styled-components'
import { WhiteBoxContainer } from '@/components/Elements/utils/styles'

export const ReservationWhiteBoxContainer = styled(WhiteBoxContainer)`
  max-width: 47%;
  display: flex;
  margin-left: auto;
  margin-right: auto;
  box-sizing: border-box;
  width: 100%;
  min-width: 500px;


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
export const ActionButtonsContainer = styled.div`
  display: flex;
  margin-left: auto;
  gap: 8px; // Adjust the spacing between buttons
`

export const DeleteButton = styled.button`
  background-color: ${(props) => props.theme.defaultRed};
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;

  transition: all 0.2s ease-in-out;

  &:hover {
    transform: scale(1.08);
  }
`

export const ConfirmButton = styled.button`
  background-color: ${(props) => props.theme.defaultSuccess};
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
`

export const CardImageContainer = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-sizing: inherit;
`

export const CardImage = styled.img`
  border-radius: 24px;
  max-width: 100%;
  width: auto;
  display: block;
  position: relative;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  transform: scale(0.9);
  height: inherit;
  border-style: none;
  box-sizing: inherit;
`

export const CardContent = styled.div`
  padding: 24px;
  border-radius: 0 0 2px 2px;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  color: ${(props) => props.theme.defaultDark};
!important;
  box-sizing: inherit;
`

export const NameAndZone = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  box-sizing: inherit;
  color: ${(props) => props.theme.defaultDark} !important;

  > .restaurant-name {
    margin-right: auto;
  }

  > .delete-button + .restaurant-name {
    margin-left: 0;
  }


`

export const Name = styled.h6`
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 1.15rem;
  line-height: 110%;
  margin: 0.7666666667rem 0 0.46rem 0;
  box-sizing: inherit;
`

export const ZoneContainer = styled.h6`
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 1.15rem;
  line-height: 110%;
  margin: 0.7666666667rem 0 0.46rem 0;
  box-sizing: inherit;
  margin-left: auto !important;
`

export const Detail = styled.p`
  margin: 0;
  width: 100%;
  font-weight: 400;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 2rem;
`

export const Category = styled.h6`
  z-index: 9999;
  padding: 4px;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2), 0 3px 10px 0 rgba(0, 0, 0, 0.19);
  border-radius: 20px;
  background-color: #fff;
  margin: 0.5rem 5px 1rem 5px !important;
  transition: box-shadow .25s, -webkit-box-shadow .25s;
  font-size: 1.15rem;
  line-height: 110%;
  font-weight: 400;
  box-sizing: inherit;
  white-space: nowrap;
`

export const Address = styled.p`
  margin: 0;
  width: 70%;
  font-weight: 300;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 2rem;
`
