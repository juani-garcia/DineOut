import React, { useEffect, useState } from 'react'
import LoadingCircle from '@/components/Elements/LoadingCircle'
import Divider from '@mui/material/Divider'
import { MenuSectionTitle } from './styles'
import { useMenuItems } from '@/hooks/Restaurants/useMenuItems'
import type MenuSection from '@/types/models/MenuSection'
import type MenuItem from '@/types/models/MenuItem'
import MenuItemComponent from '@/components/Elements/MenuItem'
import { useTranslation } from 'react-i18next'

interface MenuSectionProps {
  menuSection: MenuSection
}

export default function MenuSectionComponent ({ menuSection }: MenuSectionProps): JSX.Element {
  const { t } = useTranslation()
  const { isLoading, menuItems } = useMenuItems()
  const [menuItemList, setMenuItemList] = useState<MenuItem[]>([])

  useEffect(() => {
    menuItems(menuSection.menuItemList).then((response) => {
      if (response.status !== 200) {
        setMenuItemList([])
        return
      }
      setMenuItemList(response.data as MenuItem[])
    }).catch((e) => {
      console.error(e.response)
    })
  }, [menuSection])

  return (
        <>
            <MenuSectionTitle>{menuSection.name}</MenuSectionTitle>
            <Divider/>
            {
                isLoading
                  ? (
                        <LoadingCircle/>
                    )
                  : (
                      menuItemList.length > 0
                        ? (
                            menuItemList.sort((m1, m2) => m1.ordering - m2.ordering).map(item => (
                                    <>
                                        <MenuItemComponent menuItem={item} key={item.self}/>
                                        <Divider/>
                                    </>
                            ))
                          )
                        : (
                                <p>{t('MenuSection.empty')}</p>
                          )
                    )
            }
        </>
  )
}
