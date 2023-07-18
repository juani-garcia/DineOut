import styled from 'styled-components'

export const RestaurantMapBoxContainer = styled.a`
  max-width: 100%;
  display: flex;
  box-sizing: border-box;
  width: 100%;
  min-width: 500px;
  cursor: pointer;
  font-family: 'Montserrat', 'Roboto', 'Oxygen', 'sans-serif' !important;

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
  max-width: 100% !important;
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
  padding: 4px;
  border-radius: 0 0 2px 2px;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  color: ${(props) => props.theme.defaultDark};
!important;
  box-sizing: inherit;
`
