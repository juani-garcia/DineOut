import React from 'react'
import type MenuItem from '@/types/models/MenuItem'
import { IconButton, Stack } from '@mui/material'
import DeleteIcon from '@mui/icons-material/Delete'
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward'
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward'
import EditIcon from '@mui/icons-material/Edit'
import { Link } from 'react-router-dom'
import { useMenuItems } from '@/hooks/Restaurants/useMenuItems'

interface MenuItemProps {
  menuItem: MenuItem,
  editable?: boolean,
  last?: boolean,
  onDelete?: (menuItem: MenuItem) => void,
  onUp?: (menuItem: MenuItem) => void,
  onDown?: (menuItem: MenuItem) => void
}

export default function MenuItemComponent ({ menuItem, editable = false, last = false, onDelete, onUp, onDown }: MenuItemProps): JSX.Element {
  const { deleteMenuItem, moveItem } = useMenuItems()

  const handleDeletion: React.MouseEventHandler<HTMLButtonElement> = e => {
    deleteMenuItem(menuItem.self).then(response => {
      if (response.status !== 204) {
        // TODO: ?????
      }
      if (onDelete) {
        onDelete(menuItem)
      }
    }).catch(e => {
      console.error(e)
    })
  }

  const handleUp: React.MouseEventHandler<HTMLButtonElement> = e => {
    moveItem(menuItem.self, true).then(response => {
      if (response.status !== 200)
        return
        if (onUp) {
          onUp(menuItem)
        }          
    }).catch(e => {
      console.error(e)
    })
  }

  const handleDown: React.MouseEventHandler<HTMLButtonElement> = e => {
    moveItem(menuItem.self, false).then(response => {
      if (response.status !== 200)
        return
        if (onDown) {
          onDown(menuItem)
        }          
    }).catch(e => {
      console.error(e)
    })
  }

  return (
        <div style={{ display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
            {(menuItem.image) && <img style={{ flex: 1 }} src={menuItem.image} alt={menuItem.name}/>}
            <div style={{ flex: 10, display: 'flex', flexDirection: 'column' }}>
                <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
                    <h3>{menuItem.name}</h3>
                    <p>$ {menuItem.price.toFixed(2)}</p>
                </div>
                <p>{menuItem.detail}</p>
            </div>
            {editable && (
                    <Stack>
                      <IconButton onClick={handleUp} color="secondary" disabled={menuItem.ordering === 0}>
                          <ArrowUpwardIcon/>
                      </IconButton>
                      <IconButton onClick={handleDown} color="secondary" disabled={last}>
                          <ArrowDownwardIcon/>
                      </IconButton>
                      <Link to={`/restaurant/item/${menuItem.id}/edit`} state={{menuSection: menuItem}}>
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
  )
}
