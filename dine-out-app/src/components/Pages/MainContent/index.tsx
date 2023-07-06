import React from 'react'
import { Link } from 'react-router-dom'
import { Catchphrase, WhoAreWeContainer, WhoAreWeText, WhoAreWeTitle } from './styles'
import { useTranslation } from 'react-i18next'
import SearchBox from '../../Elements/SearchBox'
import { MyContainer, Title } from '../../Elements/utils/styles'

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

            <Link to="/restaurants">
                <h1>Restaurants</h1>
            </Link>
        </MyContainer>
  )
}

export default MainContent
