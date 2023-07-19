import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useEffect } from 'react'
import { useTranslation } from 'react-i18next'
import { Header, MenuSectionForm, MenuSectionWhiteBoxContainer } from './styles'
import { Button, FormControl } from '@mui/material'
import { useForm } from 'react-hook-form'
import { useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '@/hooks/auth/useAuth'

// interface MenuSectionFormInput {
//   name: string
// }

export default function MenuSectionCreation (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit } = useForm()
  // const location = useLocation()
  // const [menuSection, setMenuSection] = useState<MenuSection>(location.state?.menuSection ? location.state.menuSection : undefined)
  // const menuSectionsURI = location.state?.menuSectionsURI
  const navigate = useNavigate()
  const { user } = useAuth()
  const params = useParams()
  const isNewSection = params.id == null
  // const { createMenuSection, updateMenuSection, readMenuSection } = useMenuSections()

  useEffect(() => {
    if (!isNewSection) {
      if ((user?.restaurantId) == null) {
        navigate('/error?status=403')
      }
      // readMenuSection(paths.API_URL + `/restaurants/${user?.restaurantId}/menu-sections/${params.id}`).then(response => {
      //   if (response.status !== 200) {
      //     navigate('/error?status=404')
      //   }
      //   setMenuSection(response.data as MenuSection)
      // })
    }
  }, [params])

  const onSubmit = (data: any): void => {
    if (isNewSection) {
      // createMenuSection(menuSectionsURI, data.name).then(response => {
      //   if (response.status !== 201) {
      //     return
      //   }
      //   navigate(`/restaurant/${user?.restaurantId}/view`)
      // }).catch(e => {
      //   console.error(e.response)
      // })
    } else {
      // if (!menuSection) {
      //   return
      // }
      // updateMenuSection(menuSection.self, data.name).then(responses => {
      //   if (responses.status !== 200) {
      //     return
      //   }
      //   navigate(`/restaurant/${user?.restaurantId}/view`)
      // }).catch(e => {
      //   console.error(e.response)
      // })
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
                        {/* <TextField */}
                        {/*    label={t('MenuSection.form.name')} */}
                        {/*    fullWidth */}
                        {/*    margin="normal" */}
                        {/*    {...control.register('name')} */}
                        {/*    variant="standard" */}
                        {/*    defaultValue={isNewSection || !menuSection ? undefined : menuSection.name} */}
                        {/* /> */}
                        <Button type="submit" variant="contained" color="primary">
                            {t('MenuSection.form.submit')}
                        </Button>
                    </FormControl>
                </MenuSectionForm>
            </MenuSectionWhiteBoxContainer>
        </MyContainer>
  )
}
