import styled from 'styled-components'
import { GroovyText, WhiteBoxContainer } from '@/components/Elements/utils/styles'

export const ParallaxContainer = styled.div`
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  border-radius: 24px;
  min-height: 580px;
  margin-bottom: 60px;
  margin-top: 60px;
  flex-direction: column;
  flex: 1;
  line-height: 0;
  height: auto;
  width: 100%;
  color: rgba(255, 255, 255, .9);
  display: flex;
  position: relative;
  overflow: hidden;
  box-sizing: inherit;
`

export const ParallaxImageContainer = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  background-color: #FFFFFF !important;
  box-sizing: inherit;
  line-height: 0;
  color: rgba(255, 255, 255, .9);
`

export const ParallaxImage = styled.img`
  transform: translate3d(-50%, 33px, 0);
  position: absolute;
  left: 50%;
  bottom: 0;
  width: 100%;
  min-height: 100%;
  opacity: 1;
  height: inherit;
  border-style: none;
  box-sizing: inherit;
  line-height: 0;
  color: rgba(255, 255, 255, .9);
`

export const ParallaxCardRecommend = styled(WhiteBoxContainer)`
  margin-top: auto;
  min-width: 600px;
  z-index: 1;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 20px;
  position: relative;
  cursor: pointer;
  display: inline-block;
  overflow: hidden;
  user-select: none;
  padding: 15px;

  transition: all 0.2s ease-in-out;

  &:hover {
    transform: scale(1.1);
  }
`

export const GroovyRecommend = styled(GroovyText)`
  font-weight: 700;
  width: 100%;
  text-align: center;
  font-size: 1.64rem;
  line-height: 110%;
  margin: 1.0933333333rem 0 0.656rem 0;
  box-sizing: inherit;
  cursor: pointer;
`
