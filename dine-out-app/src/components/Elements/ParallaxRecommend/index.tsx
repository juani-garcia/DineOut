import React, { useEffect, useRef } from 'react'
import {
  GroovyRecommend,
  ParallaxCardRecommend,
  ParallaxContainer,
  ParallaxImage,
  ParallaxImageContainer
} from '@/components/Elements/ParallaxRecommend/styles'
import { ChooseP } from '@/components/Elements/utils/styles'
import { useTranslation } from 'react-i18next'

function ParallaxRecommend (): JSX.Element {
  const { t } = useTranslation()
  const parallaxRef = useRef(null)

  useEffect(() => {
    const handleScroll = (): void => {
      const parallaxImg = document.getElementById('parallax-img')
      if (parallaxImg == null) return

      const container = document.getElementById('parallax-container')
      if (container == null) return
      const containerHeight = container.offsetHeight

      const imgHeight = parallaxImg.offsetHeight
      const parallaxDist = imgHeight - containerHeight

      const parallax = document.getElementById('parallax')
      if (parallax == null) return

      const bottom = parallax.offsetTop + containerHeight
      const top = parallax.offsetTop
      const scrollTop = document.documentElement.scrollTop
      const windowHeight = window.innerHeight
      const windowBottom = scrollTop + windowHeight
      const percentScrolled = (windowBottom - top) / (containerHeight + windowHeight)
      const scrollDistance = (parallaxDist * percentScrolled)

      if (bottom > scrollTop && top < scrollTop + windowHeight) {
        parallaxImg.style.transform = `translate3d(-50%, ${scrollDistance}px, 0)`
      }
    }

    window.addEventListener('scroll', handleScroll)
    window.addEventListener('load', handleScroll)
    window.addEventListener('resize', handleScroll)
    return () => {
      window.addEventListener('scroll', handleScroll)
      window.addEventListener('load', handleScroll)
      window.addEventListener('resize', handleScroll)
    }
  }, [])

  return (<ParallaxContainer id="parallax-container" className="parallax-container" ref={parallaxRef}>
        <ParallaxCardRecommend>
            <GroovyRecommend>{t('Parallax.dontKnow')}</GroovyRecommend>
            <ChooseP>{t('Parallax.choose')}</ChooseP>
        </ParallaxCardRecommend>
        <ParallaxImageContainer id="parallax">
            <ParallaxImage src="http://pawserver.it.itba.edu.ar/paw-2022a-10/resources/media/background2.jpg"
                           id="parallax-img"/>
        </ParallaxImageContainer>
    </ParallaxContainer>)
}

export default ParallaxRecommend
