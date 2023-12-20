import styled from 'styled-components'

export const FavoritesMainContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 15px;
  width: 90%;
  margin: 0 auto;
  max-width: 1480px;
  box-sizing: inherit;
`

export const FavoritesCardHolder = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-top: 1rem;
  padding-bottom: 1rem;
  box-sizing: inherit;
`

export const FavoritesCardContainer = styled.div`
  width: 100%;
  border-radius: 20px;
  position: relative;
  margin: 0.5rem 0 1rem 0;
  background-color: #fff;
  transition: box-shadow .25s, -webkit-box-shadow .25s;
  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.12), 0 1px 5px 0 rgba(0, 0, 0, 0.2);
  box-sizing: inherit;
`

export const FavoritesTitle = styled.h1`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-weight: 900;
  background: -webkit-linear-gradient(45deg, #d53369 0%, #daae51 100%);
  background-clip: border-box;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  margin: 2.8rem 0 2.8rem 0;
  font-size: 4.2rem;
  line-height: 110%;
  box-sizing: inherit;
`
