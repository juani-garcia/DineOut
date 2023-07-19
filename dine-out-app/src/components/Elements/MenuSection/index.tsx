import React, { useEffect, useState } from 'react'
import LoadingCircle from '@/components/Elements/LoadingCircle'
import Divider from '@mui/material/Divider'
import { MenuSectionTitle } from './styles'
import { useMenuItems } from '@/hooks/Restaurants/useMenuItems'
import type MenuSection from '@/types/models/MenuSection'
import type MenuItem from '@/types/models/MenuItem'
import MenuItemComponent from '@/components/Elements/MenuItem'
import { useTranslation } from 'react-i18next'
import { IconButton } from '@mui/material'
import DeleteIcon from '@mui/icons-material/Delete'

interface MenuSectionProps {
  menuSection: MenuSection
  editable?: boolean
  onDelete?: (section: MenuSection) => void
}

export default function MenuSectionComponent ({ menuSection, editable = false, onDelete }: MenuSectionProps): JSX.Element {
  const { t } = useTranslation()
  const { isLoading, menuItems, deleteMenuItem } = useMenuItems()
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

  const handleDeletion: React.MouseEventHandler<HTMLButtonElement> = e => {
    deleteMenuItem(menuSection.self).then(response => {
      if (response.status !== 204) {
        // TODO: ?????
      }
      if (onDelete) {
        onDelete(menuSection)
      }
    }).catch(e => {
      console.error(e)
    })
  }

  return (
        <>
            <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
                <MenuSectionTitle>{menuSection.name}</MenuSectionTitle>
                {editable && (
                    <div>
                        <IconButton onClick={handleDeletion} color="secondary" aria-label="delete">
                            <DeleteIcon/>
                        </IconButton>
                    </div>
                )}
            </div>
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
