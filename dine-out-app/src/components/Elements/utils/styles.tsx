import styled from 'styled-components'

export const Title = styled.h1`
  font-weight: 700;
  color: #FFFFFF !important;
  text-align: center;
  font-size: 4.2rem;
  margin: 2.8rem 0 2.8rem 0;
  line-height: 110%;
`

export const MyContainer = styled.div`
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  max-width: 1480px;
  width: 100%;
`

export const BoxContainer = styled.div`
  margin: 0 auto;
  max-width: 1480px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  border-radius: 24px;
`

export const WhiteBoxContainer = styled(BoxContainer)`
  background-color: ${(props) => props.theme.defaultWhite};
  margin-top: 30px;
  margin-bottom: 30px;
`

export const Button = styled.button`
  margin-top: -2px;
  margin-left: 15px;
  margin-right: 15px;
  text-transform: none;
  border-radius: 10px;
  font-weight: 700;
  position: relative;
  cursor: pointer;
  display: inline-block;
  overflow: hidden;
  user-select: none;
  vertical-align: middle;
  z-index: 1;
  height: 32.4px;
  line-height: 32.4px;
  font-size: 13px;
  text-decoration: none;
  text-align: center;
  letter-spacing: .5px;
  outline: 0;
  border: none;
  padding: 0 16px;
  box-sizing: inherit;
  list-style-type: none;
  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.12), 0 1px 5px 0 rgba(0, 0, 0, 0.2);
  font-family: inherit;


  transition: all 0.2s ease-in-out;

  &:hover {
    transform: scale(1.1);
  }
`
