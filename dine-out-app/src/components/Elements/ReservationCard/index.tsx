import React, { useEffect, useState } from 'react'
import type Reservation from '@/types/models/Reservation'
import type Restaurant from '@/types/models/Restaurant'
import { enqueueSnackbar } from 'notistack'
import { useTranslation } from 'react-i18next'
import { HttpStatusCode } from 'axios'
import { CircularProgress } from '@mui/material'
import {
  ActionButtonsContainer,
  Address, CardContent, CardImage, CardImageContainer, ConfirmButton, DeleteButton,
  Detail,
  Name,
  NameAndZone, ReservationWhiteBoxContainer,
  ZoneContainer
} from '@/components/Elements/ReservationCard/styles'
import { Zone } from '@/types/enums/Zone'
import DeleteIcon from '@mui/icons-material/Delete'
import CheckIcon from '@mui/icons-material/Check'
import ConfirmationModal from '@/components/Elements/ConfirmationModal'
import { useDeleteReservation } from '@/hooks/Reservations/useDeleteReservation'
import useRestaurantFromUri from '@/hooks/Restaurants/useRestaurantFromUri'
import { useConfirmReservation } from '@/hooks/Reservations/useConfirmReservation'

interface ReservationCardProps {
  reservation: Reservation
  deleteCallback: (res: Reservation) => void
  confirmCallback: (res: Reservation) => void
  forUser: boolean
}

const getFormattedDateTime = (dateString: string): string => {
  const date = new Date(dateString)
  const formatter = new Intl.DateTimeFormat('es-AR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
  return formatter.format(date)
}

const ReservationCard = ({ forUser, confirmCallback, deleteCallback, reservation }: ReservationCardProps): React.JSX.Element => {
  console.log(reservation)

  const { t } = useTranslation()
  const [restaurant, setRestaurant] = useState<Restaurant | null>(null)
  const { isLoading: isLoadingRestaurant, requestRestaurant } = useRestaurantFromUri()
  const { isLoading: isLoadingDelete, deleteReservation } = useDeleteReservation()
  const { isLoading: isLoadingConfirm, confirmReservation } = useConfirmReservation()
  const [restaurantZoneName, setRestaurantZoneName] = useState<string>('')
  const [formattedDateTime, setFormattedDateTime] = useState<string>(getFormattedDateTime(reservation.dateTime))
  const [isDeleteModalOpen, setDeleteModalOpen] = useState<boolean>(false)
  const [isConfirmModalOpen, setConfirmModalOpen] = useState<boolean>(false)

  useEffect(() => {
    setFormattedDateTime(getFormattedDateTime(reservation.dateTime))
  }, [reservation])

  useEffect(() => {
    requestRestaurant(reservation.restaurant)
      .then(response => {
        if (response.status === HttpStatusCode.Ok) {
          setRestaurant(response.data as Restaurant)
          const zone = Zone.values.find(otherZ => otherZ.name === restaurant?.zone)
          if (restaurant !== null && zone !== undefined) {
            setRestaurantZoneName(zone.description)
          }
        } else {
          enqueueSnackbar(t('Errors.oops'), {
            variant: 'error'
          })
        }
      })
      .catch(() => {
        enqueueSnackbar(t('Errors.oops'), {
          variant: 'error'
        })
      })
      .finally(() => {
        setDeleteModalOpen(false)
      })
  }, [])

  const openDeleteModal = (): void => {
    setDeleteModalOpen(true)
  }

  const closeDeleteModal = (): void => {
    setDeleteModalOpen(false)
  }

  const onDeleteReservation = (): void => {
    deleteReservation(reservation)
      .then(response => {
        if (response.status === HttpStatusCode.Ok || response.status === HttpStatusCode.NoContent) {
          enqueueSnackbar(t('Reservation.deleteSuccessful'), {
            variant: 'default'
          })
          deleteCallback(reservation)
        }
      })
      .catch(() => {
        enqueueSnackbar(t('Errors.oops'), {
          variant: 'error'
        })
      }).finally(() => {
        closeDeleteModal()
      })
  }

  const openConfirmModal = (): void => {
    setConfirmModalOpen(true)
  }

  const closeConfirmModal = (): void => {
    setConfirmModalOpen(false)
  }

  const onConfirmReservation = (): void => {
    confirmReservation(reservation)
      .then(response => {
        if (response.status === HttpStatusCode.Ok) {
          enqueueSnackbar(t('Reservation.confirmSuccessful'), {
            variant: 'success'
          })
          confirmCallback(reservation)
        } else {
          enqueueSnackbar(t('Errors.tryAgain'), {
            variant: 'warning'
          })
        }
      })
      .catch(() => {
        enqueueSnackbar(t('Errors.oops'), {
          variant: 'error'
        })
      }).finally(() => {
        closeConfirmModal()
      })
  }

  return (
    <>
    { isLoadingRestaurant
      ? (
        <CircularProgress color="secondary" size="100px"/>
        )
      : (
        <ReservationWhiteBoxContainer>
          {restaurant?.image != null && restaurant.image !== ''
            ? (
              <CardImageContainer className="card-image">
                <CardImage src={restaurant.image}/>
              </CardImageContainer>
              )
            : (
              <></>
              )
          }
          <CardContent className="card-content">
            {forUser && restaurant !== null
              ? (
                <>
                  <NameAndZone>
                    <Name className={'restaurant-name'}>{restaurant.name}</Name>
                    {restaurantZoneName === ''
                      ? <></>
                      : <ZoneContainer className={'restaurant-zone'}>&#128205;{restaurantZoneName}</ZoneContainer>}
                  </NameAndZone>
                  <Detail>
                    {`${t('Reservation.for')} ${reservation.amount} ${t('Reservation.on')} ${formattedDateTime}`}
                  </Detail>
                  <Detail>
                    {`${reservation.comments}`}
                  </Detail>
                  <Detail>
                    {reservation.isConfirmed ? t('Reservation.isConfirmed') : t('Reservation.awaitingConfirmation')}
                  </Detail>
                  <Address>
                    {restaurant.address}
                  </Address>
                </>
                )
              : (
                <>
                  <NameAndZone>
                    <Name className={'restaurant-name'}>
                      { t('Reservation.forOwner') }
                      { reservation.ownerLastName !== undefined && reservation.ownerFirstName !== undefined
                        // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
                        ? ` ${reservation.ownerFirstName} ${reservation.ownerLastName}`
                        // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
                        : ` ${reservation.mail}` }
                    </Name>
                  </NameAndZone>
                  <Detail>
                    {`${t('Reservation.for')} ${reservation.amount} ${t('Reservation.on')} ${formattedDateTime}`}
                  </Detail>
                  <Detail>
                    {`${reservation.comments}`}
                  </Detail>
                  <Detail>
                    {reservation.isConfirmed ? t('Reservation.isConfirmed') : t('Reservation.awaitingConfirmation')}
                  </Detail>
                </>
                )}

            { Date.parse(reservation.dateTime) > Date.now()
              ? (
                <ActionButtonsContainer>
                  <DeleteButton
                    className="delete-button"
                    onClick={openDeleteModal}
                    disabled={isLoadingDelete}>
                    <DeleteIcon/>
                  </DeleteButton>
                  { !reservation.isConfirmed && !forUser
                    ? (
                      <>
                        <ConfirmButton
                          className="delete-button"
                          onClick={openConfirmModal}
                          disabled={isLoadingConfirm}>
                          <CheckIcon/>
                        </ConfirmButton>
                        <ConfirmationModal
                          title={t('Reservation.confirmConfirm')}
                          open={isConfirmModalOpen}
                          onClose={closeConfirmModal}
                          onConfirm={onConfirmReservation}/>
                      </>
                      )
                    : (
                    <></>
                      )}
                  <ConfirmationModal
                    title={t('Reservation.confirmDelete')}
                    open={isDeleteModalOpen}
                    onClose={closeDeleteModal}
                    onConfirm={onDeleteReservation}/>
                </ActionButtonsContainer>
                )
              : (
                <></>
                )
            }
          </CardContent>
        </ReservationWhiteBoxContainer>
        )
    }
    </>
  )
}

export default ReservationCard
