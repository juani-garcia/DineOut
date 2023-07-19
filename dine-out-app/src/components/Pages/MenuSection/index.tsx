import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React from 'react'
import { useTranslation } from 'react-i18next'
import { Header, MenuSectionForm, MenuSectionWhiteBoxContainer } from './styles'
import { Button, FormControl, TextField } from '@mui/material'
import { useForm } from 'react-hook-form'
import { useMenuSections } from '@/hooks/Restaurants/useMenuSections'
import { useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from '@/hooks/auth/useAuth'

// interface MenuSectionFormInput {
//   name: string
// }

export default function MenuSectionCreation (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control } = useForm()
  const location = useLocation()
  const menuSectionsURI = location.state
  const navigate = useNavigate()
  const { user } = useAuth()

  const onSubmit = (data: any) => {
    if (! menuSectionsURI)
      return
    createMenuSection(menuSectionsURI, data.name).then(response => {
      if (response.status !== 201) {
        return
      }
      navigate(`/restaurant/${user?.restaurantId}/view`)
    }).catch(e => {
      console.error(e.response)
    })
  }
  const { isLoading, menuSections, createMenuSection } = useMenuSections()

  return (
        <MyContainer>
            <Title>{t('MenuSection.form.title')}</Title>
            <MenuSectionWhiteBoxContainer>
                <MenuSectionForm onSubmit={handleSubmit(onSubmit)}>
                    <Header>{t('MenuSection.form.header')}</Header>
                    {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                    <FormControl component="form"
                                 sx={{
                                   '& .MuiButton-root': {
                                     width: '40%'
                                   }
                                 }}>
                        <TextField
                            label={t('MenuSection.form.name')}
                            fullWidth
                            margin="normal"
                            {...control.register('name')}
                            variant="standard"
                        />
                        <Button type="submit" variant="contained" color="primary">
                            {t('MenuSection.form.submit')}
                        </Button>
                    </FormControl>
                </MenuSectionForm>
            </MenuSectionWhiteBoxContainer>
        </MyContainer>
  )
}
