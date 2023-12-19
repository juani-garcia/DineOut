import React from 'react'
import Dialog from '@mui/material/Dialog'
import DialogTitle from '@mui/material/DialogTitle'
import DialogContent from '@mui/material/DialogContent'
import DialogContentText from '@mui/material/DialogContentText'
import DialogActions from '@mui/material/DialogActions'
import Button from '@mui/material/Button'
import { useTranslation } from 'react-i18next'

interface ConfirmationModalProps {
  title: string
  open: boolean
  onClose: () => void
  onConfirm: () => void
}

const ConfirmationModal = ({ title, open, onClose, onConfirm }: ConfirmationModalProps): React.JSX.Element => {
  const { t } = useTranslation()

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>{ title }</DialogTitle>
      <DialogContent>
        <DialogContentText>{ t('Confirmation.actionIrreversible')}</DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button
          onClick={onClose}
          style={{
            color: 'black'
          }}
        >
          { t('Confirmation.goBack') }
        </Button>
        <Button
          onClick={onConfirm}
          style={{
            color: 'black'
          }}>
          { t('Confirmation.continue') }
        </Button>
      </DialogActions>
    </Dialog>
  )
}

export default ConfirmationModal
