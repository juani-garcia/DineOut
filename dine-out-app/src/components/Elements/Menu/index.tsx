import React, { useEffect, useState } from 'react'
import { WhiteBoxContainer } from '@/components/Elements/utils/styles'
import LoadingCircle from '@/components/Elements/LoadingCircle'
import { MenuTitle } from './styles'
import { useTranslation } from 'react-i18next'
import { useMenuSections } from '@/hooks/Restaurants/useMenuSections'
import type MenuSection from '@/types/models/MenuSection'
import MenuSectionComponent from '@/components/Elements/MenuSection'
import { WhiteButton } from '@/components/Elements/RestaurantBigCard/styles'
import { Link } from 'react-router-dom'

interface MenuProps {
  menuSectionsURI: string
  editable?: boolean
  menuSectionsOrder: string[]
  onDelete: (section: MenuSection) => void
  onUp: (section: MenuSection) => void
  onDown: (section: MenuSection) => void
}

export default function MenuComponent ({ menuSectionsURI, editable = false, menuSectionsOrder, onDelete, onUp, onDown }: MenuProps): JSX.Element {
  const { t } = useTranslation()
  const { isLoading, menuSections } = useMenuSections()
  const [menuSectionList, setMenuSectionList] = useState<MenuSection[]>([])

  useEffect(() => {
    menuSections(menuSectionsURI).then((response) => {
      if (response.status !== 200) {
        setMenuSectionList([])
        return
      }
      setMenuSectionList(response.data as MenuSection[])
    }).catch((e) => {
      console.error(e.response)
    })
  }, [menuSectionsURI])

  const handleSectionDeletion = (menuSection: MenuSection): void => {
    const newMenuSectionList = menuSectionList.filter(section => section.id !== menuSection.id)
    setMenuSectionList(newMenuSectionList)
    onDelete(menuSection)
  }

  const handleSectionUp = (menuSection: MenuSection): void => {
    onUp(menuSection)
  }

  const handleSectionDown = (menuSection: MenuSection): void => {
    onDown(menuSection)
  }

  return (
        <WhiteBoxContainer style={{ width: '100%', padding: 24 }}>
            <MenuTitle>{menuSectionList.length > 0 ? t('Menu.title') : t('Menu.empty')}</MenuTitle>
            {editable && (
                <div style={{
                  display: 'flex',
                  flexDirection: 'row',
                  justifyContent: 'space-between',
                  margin: '2rem 0rem'
                }}>
                    <WhiteButton as={Link} to={'/restaurant/section'} state={{ menuSectionsURI }}>
                        {t('MenuSection.creation.prompt')}
                    </WhiteButton>
                    <WhiteButton as={Link} to={'/restaurant/item'}>
                        {t('MenuItem.creation.prompt')}
                    </WhiteButton>
                </div>
            )}
            {
                isLoading
                  ? (
                        <LoadingCircle/>
                    )
                  : (
                      menuSectionList.length > 0
                        ? (
                                <>
                                    {menuSectionList.sort((s1, s2) => menuSectionsOrder.indexOf(s1.self) - menuSectionsOrder.indexOf(s2.self)).map(
                                      (section, index) => (
                                            <MenuSectionComponent
                                                menuSection={section}
                                                key={section.self}
                                                editable={editable}
                                                first={index === 0}
                                                last={index === menuSectionList.length - 1}
                                                onDelete={handleSectionDeletion}
                                                onUp={handleSectionUp}
                                                onDown={handleSectionDown}
                                            />
                                      )
                                    )}
                                </>
                          )
                        : (
                                <></>
                          )

                    )
            }
        </WhiteBoxContainer>
  )
}
