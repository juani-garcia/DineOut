import React, { useEffect } from 'react'
import { ParallaxContainer, ParallaxImage, ParallaxImageContainer } from '@/components/Elements/ParallaxRecommend/styles'

function ParallaxRecommend (): JSX.Element {
  useEffect(() => {
    const handleScroll = (): void => {
      const parallaxImg = document.getElementById('parallax-img')
      if (parallaxImg == null) return

      const scrollPosition = window.pageYOffset

      parallaxImg.style.transform = `translate3d(-50%, ${(scrollPosition * 0.3) + 33}px, 0)`
    }

    window.addEventListener('scroll', handleScroll)
    return () => {
      window.removeEventListener('scroll', handleScroll)
    }
  }, [])

  return (<ParallaxContainer id="parallax-container" className="parallax-container">
        <ParallaxImageContainer className="parallax">
            <ParallaxImage src="http://pawserver.it.itba.edu.ar/paw-2022a-10/resources/media/background2.jpg"
                           id="parallax-img"/>
        </ParallaxImageContainer>
    </ParallaxContainer>)
}

export default ParallaxRecommend
