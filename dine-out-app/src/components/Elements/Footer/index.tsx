import React from 'react'
import { CompanyName, FooterComponent, FooterContainer, FooterText } from './styles'
import { useTranslation } from 'react-i18next'

function Footer (): JSX.Element {
  const { t } = useTranslation()
  return <FooterComponent>
        <FooterContainer>
            <FooterText>{t('Footer.project')}
                <CompanyName>{t('Footer.company')}</CompanyName>.
            </FooterText>
        </FooterContainer>
    </FooterComponent>
}

export default Footer
