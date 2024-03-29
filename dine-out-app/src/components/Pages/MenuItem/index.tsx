import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { Header, MenuItemForm, MenuItemWhiteBoxContainer } from './styles'
import { Button, FormControl, MenuItem, Select, TextField } from '@mui/material'
import { Controller, useForm } from 'react-hook-form'
import type MenuSection from '@/types/models/MenuSection'
import { useLocation, useNavigate } from 'react-router-dom'
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
  const { handleSubmit, control, setValue } = useForm()
  const { user } = useAuth()
  const navigate = useNavigate()
  const { menuSections } = useMenuSections()
  const [sections, setSections] = useState<MenuSection[]>([])
  const { createMenuItem, updateMenuItem } = useMenuItems()
  const location = useLocation()
  const menuItem = location.state?.menuItem

  useEffect(() => {
    if (user === null) {
      navigate('/login', {
        state: { from: window.location.pathname.replace('/paw-2022a-10', '') + window.location.search }
      })
    } else if (!user.roles.includes(roles.RESTAURANT)) {
      navigate('/')
    } else if (user.restaurantId === undefined || user.restaurantId === null) {
      navigate('/restaurant/register')
    }
    // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
    menuSections(`${paths.API_URL}${paths.RESTAURANTS}/${user?.restaurantId}/menu-sections`).then(response => {
      if (response.status !== 200) {
        return
      }
      setSections(response.data as MenuSection[])
    }).catch((e) => {
      console.log(e.data())
    })
  }, [user])

  useEffect(() => {
    if (menuItem !== null && menuItem !== undefined) {
      setValue('name', menuItem.name)
      setValue('detail', menuItem.detail)
      setValue('price', menuItem.price)
      setValue('section', menuItem.section)
    }
  }, [menuItem])

  const onSubmit = (data: any): void => {
    if (menuItem !== null && menuItem !== undefined) {
      console.log(data)
      updateMenuItem(menuItem.self,
        data.name,
        data.detail,
        data.price,
        data.section).then(response => {
        if (response.status !== 200) {
          return
        }
        // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
        navigate(`/restaurant/${user?.restaurantId}/view`)
      }).catch(e => {
        console.error(e.response)
      })
    } else {
      // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
      createMenuItem(`${paths.API_URL}${paths.RESTAURANTS}/${user?.restaurantId}/menu-sections/${data.section}/menu-items`,
        data.name,
        data.detail,
        data.price,
        data.section).then(response => {
        if (response.status !== 201) {
          return
        }
        if (user == null) return
        navigate('/restaurant/' + user?.restaurantId.toString() + '/view')
      }).catch(e => {
        console.error(e.response)
      })
    }
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
