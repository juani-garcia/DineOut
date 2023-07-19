import React from 'react'
import type MenuItem from '@/types/models/MenuItem'

interface MenuItemProps {
  menuItem: MenuItem
}

export default function MenuItemComponent ({ menuItem }: MenuItemProps): JSX.Element {
  return (
        <div style={{ display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
            {(menuItem.image !== '') && <img style={{ flex: 1 }} src={menuItem.image} alt={menuItem.name}/>}
            <div style={{ flex: 10, display: 'flex', flexDirection: 'column' }}>
                <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
                    <h3>{menuItem.name}</h3>
                    <p>$ {menuItem.price.toFixed(2)}</p>
                </div>
                <p>{menuItem.detail}</p>
            </div>
        </div>
  )
}
