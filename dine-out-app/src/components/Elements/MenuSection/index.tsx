import React, { useEffect, useState } from 'react'
import LoadingCircle from '@/components/Elements/LoadingCircle'
import Divider from '@mui/material/Divider'
import { MenuSectionTitle } from './styles'
import { useMenuItems } from '@/hooks/Restaurants/useMenuItems'
import type MenuSection from '@/types/models/MenuSection'
import type MenuItem from '@/types/models/MenuItem'
import MenuItemComponent from '@/components/Elements/MenuItem'
import { useTranslation } from 'react-i18next'
import { IconButton, Stack } from '@mui/material'
import DeleteIcon from '@mui/icons-material/Delete'
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward'
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward'
import { useMenuSections } from '@/hooks/Restaurants/useMenuSections'
import { Link } from 'react-router-dom'
import EditIcon from '@mui/icons-material/Edit'

interface MenuSectionProps {
  menuSection: MenuSection
  editable?: boolean
  first?: boolean
  last?: boolean
  onDelete?: (section: MenuSection) => void
  onUp?: (section: MenuSection) => void
  onDown?: (section: MenuSection) => void
}

export default function MenuSectionComponent ({
  menuSection,
  editable = false,
  first = false,
  last = false,
  onDelete,
  onUp,
  onDown
}: MenuSectionProps): JSX.Element {
  const { t } = useTranslation()
  const { isLoading, menuItems, deleteMenuItem } = useMenuItems()
  const [menuItemList, setMenuItemList] = useState<MenuItem[]>([])
  const [menuItemsOrder, setMenuItemsOrder] = useState<string[]>([])
  const { updateMenuSection } = useMenuSections()

  useEffect(() => {
    setMenuItemsOrder(menuSection.menuItemsOrder)
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
      if (onDelete != null) {
        onDelete(menuSection)
      }
    }).catch(e => {
      console.error(e)
    })
  }

  const handleUp: React.MouseEventHandler<HTMLButtonElement> = e => {
    // TODO: Handle
  }

  const handleDown: React.MouseEventHandler<HTMLButtonElement> = e => {
    // TODO: Handle
  }

  const handleItemDeletion = (menuItem: MenuItem): void => {
    const newMenuItemList = menuItemList.filter(item => menuItem.id !== item.id)
    setMenuItemList(newMenuItemList)
    const newMenuItemsOrder = menuItemsOrder.filter(s => s !== menuItem.self)
    setMenuItemsOrder(newMenuItemsOrder)
  }

  const handleItemUp = (menuItem: MenuItem): void => {
    const newMenuItemsOrder = [...menuItemsOrder]
    const currentIndex: number = menuItemsOrder.indexOf(menuItem.self)
    console.log('Moving position ' + String(currentIndex) + ' up')
    newMenuItemsOrder[currentIndex - 1] = menuItem.self
    newMenuItemsOrder[currentIndex] = menuItemsOrder[currentIndex - 1]
    console.log(newMenuItemsOrder)
    updateMenuSection(menuSection.self, menuSection.name, newMenuItemsOrder).then(responses => {
      if (responses.status !== 200) {
        return
      }
      setMenuItemsOrder(newMenuItemsOrder)
    }).catch(e => {
      console.error(e.response)
    })
  }

  const handleItemDown = (menuItem: MenuItem): void => {
    const newMenuItemsOrder = [...menuItemsOrder]
    const currentIndex: number = menuItemsOrder.indexOf(menuItem.self)
    console.log('Moving position ' + String(currentIndex) + ' down')
    newMenuItemsOrder[currentIndex + 1] = menuItem.self
    newMenuItemsOrder[currentIndex] = menuItemsOrder[currentIndex + 1]
    console.log(newMenuItemsOrder)
    updateMenuSection(menuSection.self, menuSection.name, newMenuItemsOrder).then(responses => {
      if (responses.status !== 200) {
        return
      }
      setMenuItemsOrder(newMenuItemsOrder)
    }).catch(e => {
      console.error(e.response)
    })
  }

  if (menuSection.id == null) {
    return (<></>)
  }
  return (
        <>
            <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
                <MenuSectionTitle>{menuSection.name}</MenuSectionTitle>
                {editable && (
                    <Stack direction='row'>
                        <IconButton onClick={handleUp} color="secondary" disabled={first}>
                            <ArrowUpwardIcon/>
                        </IconButton>
                        <IconButton onClick={handleDown} color="secondary" disabled={last}>
                            <ArrowDownwardIcon/>
                        </IconButton>
                        <Link to={'/restaurant/section/' + menuSection.id.toString() + '/edit'}
                              state={{ menuSection }}>
                            <IconButton color="secondary">
                                <EditIcon/>
                            </IconButton>
                        </Link>
                        <IconButton onClick={handleDeletion} color="secondary">
                            <DeleteIcon/>
                        </IconButton>
                    </Stack>

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
                            menuItemList.sort((m1, m2) => menuItemsOrder.indexOf(m1.self) - menuItemsOrder.indexOf(m2.self)).map((item, index) => (
                                    <>
                                        <MenuItemComponent
                                            menuItem={item}
                                            key={item.self}
                                            editable={editable}
                                            first={index === 0}
                                            last={index === menuItemList.length - 1}
                                            onDelete={handleItemDeletion}
                                            onUp={handleItemUp}
                                            onDown={handleItemDown}
                                        />
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
