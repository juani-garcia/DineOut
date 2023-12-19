import React, { useRef, useState } from 'react'
import type MenuItem from '@/types/models/MenuItem'
import { IconButton, Input, Stack } from '@mui/material'
import DeleteIcon from '@mui/icons-material/Delete'
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward'
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward'
import EditIcon from '@mui/icons-material/Edit'
import { Link } from 'react-router-dom'
import { useMenuItems } from '@/hooks/Restaurants/useMenuItems'
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate'
import useImage from '@/hooks/Images/useImage'

interface MenuItemProps {
  menuItem: MenuItem
  editable?: boolean
  first?: boolean
  last?: boolean
  onDelete?: (menuItem: MenuItem) => void
  onUp?: (menuItem: MenuItem) => void
  onDown?: (menuItem: MenuItem) => void
}

export default function MenuItemComponent ({ menuItem, editable = false, first = false, last = false, onDelete, onUp, onDown }: MenuItemProps): JSX.Element {
  const { deleteMenuItem } = useMenuItems()
  const fileInputRef = useRef<HTMLInputElement | null>(null)
  const [imagePreview, setImagePreview] = useState<string | undefined>((menuItem.image === '') ? undefined : menuItem.image)
  const { updateImage, deleteImage } = useImage()

  const handleDeletion: React.MouseEventHandler<HTMLButtonElement> = e => {
    deleteMenuItem(menuItem.self).then(response => {
      if (response.status !== 204) {
        // TODO: ??????
      }
      if (onDelete != null) {
        onDelete(menuItem)
      }
    }).catch(e => {
      console.error(e)
    })
  }

  const handleUp: React.MouseEventHandler<HTMLButtonElement> = e => {
    if (onUp !== null && onUp !== undefined) {
      onUp(menuItem)
    }
  }

  const handleDown: React.MouseEventHandler<HTMLButtonElement> = e => {
    if (onDown !== null && onDown !== undefined) {
      onDown(menuItem)
    }
  }

  const handleUpdateImage: React.ChangeEventHandler<HTMLInputElement> = event => {
    if (event.target.files == null) {
      return
    }
    const newImage = event.target.files[0]
    updateImage(`${menuItem.self}/image`, newImage).then(response => {
      if (response.status !== 200) {
        return
      }
      setImagePreview(URL.createObjectURL(newImage))
    }).catch(e => {
      console.error(e.response) // TODO: Toast
    })
  }

  const handleDeleteImage: React.MouseEventHandler<HTMLButtonElement> = event => {
    deleteImage(`${menuItem.self}/image`).then(response => {
      if (response.status !== 200) {
        return
      }
      setImagePreview(undefined)
    }).catch(e => {
      console.error(e.response)
    })
  }

  return (
        <div style={{ display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
            <Stack>
              {(imagePreview !== undefined) && <img style={{ flex: 1 }} src={imagePreview} alt={menuItem.name}/>}
              {editable && (
                <Stack direction='row'
                  justifyContent='flex-start'
                  alignItems='center'
                >
                  <Input
                    style={{ display: 'hidden', visibility: 'collapse', width: '39px' }}
                    type='file'
                    name='image'
                    inputRef={fileInputRef}
                    onChange={handleUpdateImage}
                    endAdornment={(
                      <>
                          <IconButton
                            style={{ display: 'block', visibility: 'visible' }}
                            onClick={() => fileInputRef.current?.click()}
                            color="secondary"
                            aria-label="delete"
                          >
                            <AddPhotoAlternateIcon/>
                          </IconButton>
                      </>
                    )}
                  />
                  <IconButton onClick={handleDeleteImage} color="secondary" aria-label="delete">
                    <DeleteIcon/>
                  </IconButton>
                </Stack>
              )}
            </Stack>
            <div style={{ flex: 10, display: 'flex', flexDirection: 'column' }}>
                <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
                    <h3>{menuItem.name}</h3>
                    <p>$ {menuItem.price.toFixed(2)}</p>
                </div>
                <p>{menuItem.detail}</p>
            </div>
            {editable && (
                    <Stack>
                      <IconButton onClick={handleUp} color="secondary" disabled={first}>
                          <ArrowUpwardIcon/>
                      </IconButton>
                      <IconButton onClick={handleDown} color="secondary" disabled={last}>
                          <ArrowDownwardIcon/>
                      </IconButton>
                      <Link to={'/restaurant/item'} state={{ menuItem }}>
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
