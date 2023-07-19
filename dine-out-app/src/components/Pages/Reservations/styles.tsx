import styled from 'styled-components'

export const ReservationMainContainer = styled.div`
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

export const ReservationCardHolder = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-top: 1rem;
  padding-bottom: 1rem;
  box-sizing: inherit;
`

export const ReservationCardContainer = styled.div`
  width: 100%;
  border-radius: 20px;
  position: relative;
  margin: 0.5rem 0 1rem 0;
  background-color: #fff;
  transition: box-shadow .25s, -webkit-box-shadow .25s;
  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.12), 0 1px 5px 0 rgba(0, 0, 0, 0.2);
  box-sizing: inherit;
`

export const ReservationTitle = styled.h1`
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

export const ShowPreviousContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-sizing: inherit;
`

export const ReservationsContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  box-sizing: inherit;
`

export const PastToggle = styled.a`
  color: #242B2A !important;
  box-sizing: inherit;
  text-decoration: underline;
  -webkit-text-fill-color: #000;
  font-weight: 700;
  font-size: 1.15rem;
  line-height: 110%;
  margin: 0.7666666667rem 0 0.46rem 0;
  cursor: pointer;

  transition: all 0.2s ease-in-out;


  &:hover {
    transform: scale(1.1);
  }
`
