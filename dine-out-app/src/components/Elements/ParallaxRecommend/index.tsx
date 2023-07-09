import React, { useEffect, useRef } from 'react'
import { ParallaxContainer, ParallaxImage, ParallaxImageContainer } from '@/components/Elements/ParallaxRecommend/styles'

function ParallaxRecommend (): JSX.Element {
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
      const scrollDistance = (parallaxDist * percentScrolled) - 160

      if (bottom > scrollTop && top < scrollTop + windowHeight) {
        parallaxImg.style.transform = `translate3d(-50%, ${scrollDistance}px, 0)`
      } else {
        parallaxImg.style.transform = ''
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
        <ParallaxImageContainer id="parallax">
            <ParallaxImage src="http://pawserver.it.itba.edu.ar/paw-2022a-10/resources/media/background2.jpg"
                           id="parallax-img"/>
        </ParallaxImageContainer>
    </ParallaxContainer>)
}

export default ParallaxRecommend
