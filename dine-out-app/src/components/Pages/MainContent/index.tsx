import React from 'react'
import { Catchphrase, WhoAreWeContainer, WhoAreWeText, WhoAreWeTitle } from './styles'
import { useTranslation } from 'react-i18next'
import SearchBox from '@/components/Elements/SearchBox'
import { MyContainer, Title } from '@/components/Elements/utils/styles'
import ParallaxRecommend from '@/components/Elements/ParallaxRecommend'

function MainContent (): JSX.Element {
  const { t } = useTranslation()
  return (
        <MyContainer>
            <Title>{t('MainContent.header.title')}</Title>
            <Catchphrase>{t('MainContent.header.catchphrase')}</Catchphrase>

            <SearchBox></SearchBox>

            <WhoAreWeContainer>
                <WhoAreWeTitle>{t('MainContent.info.title')}</WhoAreWeTitle>
                <WhoAreWeText>{t('MainContent.info.description')}</WhoAreWeText>
            </WhoAreWeContainer>

            <ParallaxRecommend/>
        </MyContainer>
  )
}

export default MainContent
