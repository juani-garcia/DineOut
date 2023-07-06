import React from 'react'
import { MyContainer, Title, WhiteBoxContainer } from '../../Elements/utils/styles'
import { useTranslation } from 'react-i18next'

function Login (): JSX.Element {
  const { t } = useTranslation()

  return (
        <MyContainer>
            <Title>{t('login')}</Title>
            <WhiteBoxContainer>BOCA</WhiteBoxContainer>
        </MyContainer>
  )
}

export default Login
