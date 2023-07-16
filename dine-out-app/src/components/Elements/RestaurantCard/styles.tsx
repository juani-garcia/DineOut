import styled from 'styled-components'
import { WhiteBoxContainer } from '@/components/Elements/utils/styles'

export const RestaurantWhiteBoxContainer = styled(WhiteBoxContainer)`
  max-width: 47%;
  display: flex;
  margin-left: auto;
  margin-right: auto;
  box-sizing: border-box;
  width: 100%;
  min-width: 500px;


  transition: all 0.2s ease-in-out;

  &:hover {
    cursor: pointer;
    transform: scale(1.1);
  }

  > .card-image {
    width: 35% !important;
    max-width: 35% !important;
  }

  > .card-content {
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  > .card-image + .card-content {
    width: 65%;
    overflow: hidden;
    text-overflow: ellipsis;
  }

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

  > .restaurant-zone + .restaurant-name {
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

export const Address = styled.p`
  margin: 0;
  width: 70%;
  font-weight: 300;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 2rem;
`

export const CategoriesAndRating = styled.div`
  width: 100%;
  margin-top: auto !important;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  box-sizing: inherit;
`

export const CategoriesContainer = styled.div`
  width: 75% !important;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  box-sizing: inherit;
`

export const CategoriesTitle = styled.p`
  margin: 0;
  font-weight: 300;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 2rem;
  box-sizing: inherit;
`

export const CategoriesHolder = styled.div`
  z-index: 9999;
  width: 100%;
  white-space: nowrap;
  display: flex;
  flex-direction: row;
  scrollbar-width: none;
  overflow-x: auto;
  box-sizing: inherit;
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

export const RatingContainer = styled.div`
  width: 20% !important;
  margin-top: auto !important;
  margin-left: auto !important;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  box-sizing: inherit;
`

export const Rating = styled.h3`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 2.92rem;
  line-height: 110%;
  margin: 0 0 0 0;
  box-sizing: inherit;
`

export const RatingCount = styled.h6`
  font-size: 1.15rem;
  line-height: 110%;
  margin: 0 0 0 0;
  color: ${(props) => props.theme.defaultLight} !important;
  text-align: center;
  font-weight: 400;
  box-sizing: inherit;
`
