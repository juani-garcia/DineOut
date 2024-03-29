import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { Header, MenuSectionForm, MenuSectionWhiteBoxContainer } from './styles'
import { Button, FormControl, TextField } from '@mui/material'
import { useForm } from 'react-hook-form'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '@/hooks/auth/useAuth'
import type MenuSection from '@/types/models/MenuSection'
import { useMenuSections } from '@/hooks/Restaurants/useMenuSections'
import { paths } from '@/common/const'

// interface MenuSectionFormInput {
//   name: string
// }

export default function MenuSectionCreation (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control } = useForm()
  const location = useLocation()
  const [menuSection, setMenuSection] = useState<MenuSection>(location.state?.menuSection != null ? location.state.menuSection : undefined)
  const menuSectionsURI = location.state?.menuSectionsURI
  const navigate = useNavigate()
  const { user } = useAuth()
  const params = useParams()
  const isNewSection = params.id == null
  const { createMenuSection, updateMenuSection, readMenuSection } = useMenuSections()

  useEffect(() => {
    if (!isNewSection) {
      if ((user?.restaurantId) == null) {
        navigate('/error?status=403')
      }
      // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
      readMenuSection(paths.API_URL + `/restaurants/${user?.restaurantId}/menu-sections/${params.id}`).then(response => {
        if (response.status !== 200) {
          navigate('/error?status=404')
        }
        setMenuSection(response.data as MenuSection)
      }).catch((e) => {
        console.log(e.data())
      })
    }
  }, [params])

  const onSubmit = (data: any): void => {
    if (isNewSection) {
      createMenuSection(menuSectionsURI, data.name).then(response => {
        if (response.status !== 201) {
          return
        }
        // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
        navigate(`/restaurant/${user?.restaurantId}/view`)
      }).catch(e => {
        console.error(e.response)
      })
    } else {
      if (menuSection == null) {
        return
      }
      updateMenuSection(menuSection.self, data.name, menuSection.menuItemsOrder).then(responses => {
        if (responses.status !== 200) {
          return
        }
        // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
        navigate(`/restaurant/${user?.restaurantId}/view`)
      }).catch(e => {
        console.error(e.response)
      })
    }
  }

  return (
        <MyContainer>
            <Title>{t('MenuSection.form.title')}</Title>
            <MenuSectionWhiteBoxContainer>
                {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                <MenuSectionForm onSubmit={handleSubmit(onSubmit)}>
                    <Header>{isNewSection ? t('MenuSection.form.header') : t('MenuSection.form.header-edit')}</Header>
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
                            defaultValue={isNewSection || (menuSection == null) ? undefined : menuSection.name}
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
