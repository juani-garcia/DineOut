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
import { useRestaurants } from '@/hooks/Restaurants/useRestaurants'
import { useSnackbar } from 'notistack'
import type Restaurant from '@/types/models/Restaurant'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '@/hooks/auth/useAuth'
import { localPaths, roles } from '@/common/const'

function ParallaxRecommend (): JSX.Element {
  const { t } = useTranslation()
  const { restaurants } = useRestaurants()
  const parallaxRef = useRef(null)
  const { enqueueSnackbar } = useSnackbar()
  const navigate = useNavigate()
  const { user } = useAuth()

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
      window.removeEventListener('scroll', handleScroll)
      window.removeEventListener('load', handleScroll)
      window.removeEventListener('resize', handleScroll)
    }
  }, [])

  const recommendedHandler = (): void => {
    const searchParams = new URLSearchParams()
    if ((user?.roles.includes(roles.DINER)) === true) {
      searchParams.append('recommendedFor', user.userId.toString())
    }
    restaurants(searchParams)
      .then((response) => {
        if (response.status >= 400) {
          navigate('/error?status=' + response.status.toString())
        }
        const restaurantList = response.data as Restaurant[]
        if (restaurantList.length === 0) {
          navigate(localPaths.RESTAURANTS) // Show there are no restaurants
        }
        const randomIndex = Math.floor(Math.random() * restaurantList.length)
        const randomRestaurant = restaurantList[randomIndex]
        navigate(localPaths.RESTAURANTS + '/' + randomRestaurant.id.toString() + '/view')
      }).catch((e) => {
        enqueueSnackbar(t('Errors.oops'), {
          variant: 'error'
        })
      })
  }

  return (<ParallaxContainer id="parallax-container" className="parallax-container" ref={parallaxRef}>
        <ParallaxCardRecommend onClick={recommendedHandler}>
            <GroovyRecommend>{t('Parallax.dontKnow')}</GroovyRecommend>
            <ChooseP>{t('Parallax.choose')}</ChooseP>
        </ParallaxCardRecommend>
        <ParallaxImageContainer id="parallax">
            <ParallaxImage src={`${process.env.PUBLIC_URL}/static/background2.jpg`} id="parallax-img"/>
        </ParallaxImageContainer>
    </ParallaxContainer>)
}

export default ParallaxRecommend
