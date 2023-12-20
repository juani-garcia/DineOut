import styled from 'styled-components'
import { Button } from '@/components/Elements/utils/styles'

export const RestaurantBlackBoxContainer = styled.div`
  width: 100%;
  min-width: 500px;
  border-radius: 20px;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  background-color: ${(props) => props.theme.defaultDark} !important;
  position: relative;
  margin: 0.5rem 0 1rem 0;
  transition: box-shadow .25s, -webkit-box-shadow .25s;
  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.12), 0 1px 5px 0 rgba(0, 0, 0, 0.2);
  color: ${(props) => props.theme.defaultWhite} !important;
  box-sizing: inherit;
`

export const RestaurantTitle = styled.h1`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  font-weight: 700;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  margin: 2.8rem 0 2.8rem 0;
  font-size: 4.2rem;
  line-height: 110%;
  box-sizing: inherit;
`

export const WhiteButton = styled(Button)`
  background-color: ${(props) => props.theme.defaultWhite};
  color: ${(props) => props.theme.black} !important;
`

export const RestaurantHeader = styled.div`
  overflow: hidden;
  text-overflow: ellipsis;
  box-sizing: inherit;
  padding: 24px;
  border-radius: 0 0 2px 2px;
`

export const RatingContainer = styled.h3`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  font-weight: 700;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  font-size: 2.92rem;
  line-height: 110%;
  margin: 1.9466666667rem 0 1.168rem 0;
  box-sizing: inherit;
  transform: scale(1.5);
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

export const DetailContainer = styled.div`
  padding: 24px;
  border-radius: 0 0 2px 2px;
  overflow: hidden;
  text-overflow: ellipsis;
  box-sizing: inherit;
`

export const Detail = styled.div`
  padding: 0 15px;
  box-sizing: inherit;
`

export const RestaurantDetail = styled.h5`
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  font-size: 1.64rem;
  line-height: 110%;
  margin: 1.0933333333rem 0 0.656rem 0;
  font-weight: 500;
  box-sizing: inherit;
`

export const DetailIconContainer = styled.h2`
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  font-size: 3.56rem;
  line-height: 160%;
  margin: 2.3733333333rem 0 1.424rem 0;
  font-weight: 400;
  box-sizing: inherit;
`

export const ZoneContainer = styled.div`
  padding: 24px;
  border-radius: 0 0 2px 2px;
  overflow: hidden;
  text-overflow: ellipsis;
  box-sizing: inherit;
`

export const Zone = styled.h5`
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  font-size: 1.64rem;
  line-height: 110%;
  margin: 1.0933333333rem 0 0.656rem 0;
  font-weight: 500;
  box-sizing: inherit;
`

export const CategoriesContainer = styled.div`
  padding: 24px;
  border-radius: 0 0 2px 2px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  text-overflow: ellipsis;
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
  justify-content: center;
  align-items: center;
`

export const Category = styled.a`
  z-index: 9999;
  padding: 6px;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2), 0 3px 10px 0 rgba(0, 0, 0, 0.19);
  border-radius: 20px;
  background-color: #fff;
  margin: 0.5rem 5px 1rem 5px !important;
  font-size: 1.15rem;
  line-height: 110%;
  font-weight: 400;
  box-sizing: inherit;
  white-space: nowrap;
  color: #000000 !important;
  text-decoration: none;
  cursor: pointer;

  transition: all 0.2s ease-in-out;

  &:hover {
    transform: scale(1.08);
  }
`

export const ShiftsContainer = styled.div`
  padding: 24px;
  border-radius: 0 0 2px 2px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  text-overflow: ellipsis;
  box-sizing: inherit;
`

export const ShiftsTitle = styled.h5`
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  font-size: 1.64rem;
  line-height: 110%;
  margin: 1.0933333333rem 0 0.656rem 0;
  font-weight: 500;
  box-sizing: inherit;
`

export const Shift = styled.h6`
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  font-size: 1.15rem;
  line-height: 110%;
  margin: 0.7666666667rem 0 0.46rem 0;
  font-weight: 500;
  box-sizing: inherit;
`

export const ButtonsContainer = styled.div`
  padding: 24px;
  border-radius: 0 0 2px 2px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  text-overflow: ellipsis;
  box-sizing: inherit;
`
