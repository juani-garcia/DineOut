import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { Header, MenuItemForm, MenuItemWhiteBoxContainer } from './styles'
import { Button, FormControl, MenuItem, Select, TextField } from '@mui/material'
import { Controller, useForm } from 'react-hook-form'
import MenuSection from '@/types/models/MenuSection'
import { useNavigate, useNavigation, useNavigationType, useParams } from 'react-router-dom'
import { useAuth } from '@/hooks/auth/useAuth'
import { useMenuSections } from '@/hooks/Restaurants/useMenuSections'
import { paths, roles } from '@/common/const'
import { useMenuItems } from '@/hooks/Restaurants/useMenuItems'

// interface MenuItemFormInput {
//   name: string
//   detail: string
//   price: number
//   section: string
//   image?: string
// }

export default function MenuItemCreation (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control } = useForm()
  const params = useParams()
  const { user } = useAuth()
  const isNewItem = !Boolean(params.id)
  const navigate = useNavigate()
  const { menuSections } = useMenuSections()
  const [sections, setSections] = useState<MenuSection[]>([])
  const { createMenuItem, getMenuItem } = useMenuItems()
  const location = useLocation()
  const menuItem = location.state.menuItem

  useEffect(() => {
    if (user === null ){
        navigate('/login', {
            state: { from: window.location.pathname}
        })
    } else if (!user.roles.includes(roles.RESTAURANT)) {
        navigate('/')
    } else if (user.restaurantId === undefined || user.restaurantId === null) {
        navigate('/restaurant/register')
    }
    menuSections(`${paths.API_URL}${paths.RESTAURANTS}/${user?.restaurantId}/menu-sections`).then(response => {
        if (response.status !== 200) {
            return
        }
        setSections(response.data as MenuSection[])
    })
    if (params.id !== null && params.id !== undefined) {
        getMenuItem()
    }
  }, [user])

  const onSubmit = (data: any): void => {
    createMenuItem(`${paths.API_URL}${paths.RESTAURANTS}/${restaurantId}/menu-sections/${data.section}/menu-items`,
    data.name,
    data.detail,
    data.price,
    data.section).then(response => {
        if (response.status !== 201) {
            return
        }
        navigate(`/restaurant/${user?.restaurantId}/view`)
    }).catch(e => {
        console.error(e.response)
    })
  }

  return (
        <MyContainer>
            <Title>{t('MenuItem.form.title')}</Title>
            <MenuItemWhiteBoxContainer>
                {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                <MenuItemForm onSubmit={handleSubmit(onSubmit)}>
                    <Header>{t('MenuItem.form.header')}</Header>
                    {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                    <FormControl component="form"
                                 sx={{
                                   '& .MuiButton-root': {
                                     width: '40%'
                                   }
                                 }}>
                        <TextField
                            label={t('MenuItem.form.name')}
                            fullWidth
                            margin="normal"
                            {...control.register('name')}
                            variant="standard"
                        />
                        <TextField
                            label={t('MenuItem.form.detail')}
                            fullWidth
                            margin="normal"
                            {...control.register('detail')}
                            variant="standard"
                        />
                        <TextField
                            label={t('MenuItem.form.price')}
                            fullWidth
                            margin="normal"
                            type="number"
                            {...control.register('price')}
                            variant="standard"
                        />
                        <FormControl fullWidth>
                            <Controller
                                name="section"
                                control={control}
                                rules={{ required: true }}
                                defaultValue=""
                                render={({ field }) => (
                                    <Select {...field} id="section-select" fullWidth label={t('MenuItem.form.section')}>
                                        {sections.map((option) => (
                                            <MenuItem key={option.self} value={option.id}>
                                                {option.name}
                                            </MenuItem>
                                        ))}
                                    </Select>
                                )}
                            />
                        </FormControl>
                        <Button type="submit" variant="contained" color="primary">
                            {t('MenuItem.form.submit')}
                        </Button>
                    </FormControl>
                </MenuItemForm>
            </MenuItemWhiteBoxContainer>
        </MyContainer>
  )
}
