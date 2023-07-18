import React, { useEffect, useState } from 'react'
import { WhiteBoxContainer } from '@/components/Elements/utils/styles'
import LoadingCircle from '@/components/Elements/LoadingCircle'
import { MenuTitle } from './styles'
import { useTranslation } from 'react-i18next'
import { useMenuSections } from '@/hooks/Restaurants/useMenuSections'
import type MenuSection from '@/types/models/MenuSection'

interface MenuProps {
  menuSectionsURI: string
}

export default function MenuComponent ({ menuSectionsURI }: MenuProps): JSX.Element {
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

  return (
        <WhiteBoxContainer style={{ width: '100%', padding: 24 }}>
            <MenuTitle>{menuSectionList.length > 0 ? t('Menu.title') : t('Menu.empty')}</MenuTitle>
            {
                isLoading
                  ? (
                        <LoadingCircle/>
                    )
                  : (
                      menuSectionList.length > 0
                        ? (
                                <>
                                    {/* menuSectionList.sort((s1, s2) => s1.ordering - s2.ordering).map( */}
                                    {/* (section) => ( */}
                                    {/* <MenuSectionComponent menuSection={section} key={section.self}/> */}
                                    {/* ) */}
                                    {/* ) */}
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
