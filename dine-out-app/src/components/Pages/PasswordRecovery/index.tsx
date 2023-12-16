import React, { useState } from 'react'
import { useRecoverPassword } from '@/hooks/auth/useRecoverPassword'
import { HttpStatusCode } from 'axios'
import { enqueueSnackbar } from 'notistack'
import { useTranslation } from 'react-i18next'
import { useUpdatePassword } from '@/hooks/auth/useUpdatePassword'
import { RecoveryForm, RecoveryWhiteBoxContainer, Header, RedirectionFooter, LinkTo } from '@/components/Pages/PasswordRecovery/styles'
import { Button, Checkbox, FormControl, FormControlLabel, TextField } from '@mui/material'
import { MyContainer, Title } from '@/components/Elements/utils/styles'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { useForm } from 'react-hook-form'
import { Link } from 'react-router-dom'

const PasswordRecovery: React.FC = () => {
  enum Status {
    SubmittingEmail,
    SubmittingToken,
    Success
  }

  const submissionHook = useRecoverPassword()
  const updateHook = useUpdatePassword()
  const { t } = useTranslation()
  const [currentStatus, setCurrentStatus] = useState<Status>(Status.SubmittingEmail)
  const [showPassword, setShowPassword] = useState<boolean>(false)

  const emailSchema = z.object({
    username: z.string()
      .nonempty(t('FormErrors.notEmpty').toString())
      .max(64, t('FormErrors.tooLong').toString())
      .email(t('FormErrors.invalidEmail').toString())
  })

  const passwordSchema = z.object({
    token: z.string()
      .nonempty(t('FormErrors.notEmpty').toString()),
    password: z.string()
      .nonempty(t('FormErrors.notEmpty').toString())
      .max(64, t('FormErrors.tooLong').toString())
      .refine((password) => {
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/
        return regex.test(password)
      }, t('FormErrors.invalidPassword').toString()),
    confirmPassword: z.string()
      .nonempty(t('FormErrors.notEmpty').toString())
      .max(64, t('FormErrors.tooLong').toString())
  }).refine((data) => {
    return data.password === data.confirmPassword
  }, {
    message: t('FormErrors.passwordMismatch').toString(),
    path: ['confirmPassword']
  })

  const emailForm = useForm({
    resolver: zodResolver(emailSchema)
  })
  const passwordForm = useForm({
    resolver: zodResolver(passwordSchema)
  })

  const onSubmitEmail = (data: any): void => {
    submissionHook.passwordRecovery(data.username)
      .then((response) => {
        if (response.status === HttpStatusCode.Ok) {
          enqueueSnackbar(t('Recovery.mailSent'), {
            variant: 'success'
          })
          setCurrentStatus(Status.SubmittingToken)
        } else if (response.status === HttpStatusCode.BadRequest) {
          enqueueSnackbar(t('Recovery.emailNotFound'), {
            variant: 'warning'
          })
        }
      })
      .catch((e) => {
        enqueueSnackbar(t('Errors.oops'), {
          variant: 'error'
        })
      })
  }

  const onSubmitPassword = (data: any): void => {
    updateHook.updatePassword(data.password, data.token)
      .then((response) => {
        if (response.status === HttpStatusCode.Ok) {
          enqueueSnackbar(t('Recovery.passwordUpdated'), {
            variant: 'success'
          })
          setCurrentStatus(Status.Success)
        } else if (response.status === HttpStatusCode.BadRequest) {
          enqueueSnackbar(t('Recovery.invalidToken'), {
            variant: 'warning'
          })
        }
      })
      .catch((e) => {
        enqueueSnackbar(t('Errors.oops'), {
          variant: 'error'
        })
      })
  }

  const handlePasswordVisibilityChange = (): void => {
    setShowPassword(!showPassword)
  }

  const renderStatus = (status: Status): React.JSX.Element => {
    switch (status) {
      case Status.SubmittingEmail:
        return (
          <RecoveryForm>
            <Header>{ t('Recovery.submitEmailPrompt') }</Header>
            {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
            <FormControl component="form" onSubmit={emailForm.handleSubmit(onSubmitEmail)}
                         sx={{
                           '& .MuiButton-root': {
                             width: '40%',
                             marginTop: '25px'
                           }
                         }}>
              <TextField
                label={t('email')}
                fullWidth
                margin="normal"
                {...emailForm.control.register('username')}
                variant="standard"
                error={emailForm.formState.errors?.username?.message != null}
                helperText={emailForm.formState.errors?.username?.message?.toString()}
              />
              <Button type="submit" variant="contained" color="primary">
                {t('Recovery.sendEmail')}
              </Button>
            </FormControl>
          </RecoveryForm>
        )
      case Status.SubmittingToken:
        return (
          <RecoveryForm>
            <Header>{ t('Recovery.submitTokenPrompt') }</Header>
            {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
            <FormControl component="form" onSubmit={passwordForm.handleSubmit(onSubmitPassword)}
                         sx={{
                           '& .MuiButton-root': {
                             width: '40%',
                             marginTop: '25px'
                           }
                         }}>
              <TextField
                label={t('Recovery.token')}
                fullWidth
                margin="normal"
                {...passwordForm.control.register('token')}
                variant="standard"
                error={passwordForm.formState.errors?.token?.message != null}
                helperText={passwordForm.formState.errors?.token?.message?.toString()}
              />
              <TextField
                label={t('Recovery.password')}
                type={ showPassword ? 'text' : 'password' }
                fullWidth
                margin="normal"
                {...passwordForm.control.register('password')}
                variant="standard"
                error={passwordForm.formState.errors?.password?.message != null}
                helperText={passwordForm.formState.errors?.password?.message?.toString()}
              />
              <TextField
                label={t('Recovery.confirmPassword')}
                fullWidth
                type={ showPassword ? 'text' : 'password' }
                margin="normal"
                {...passwordForm.control.register('confirmPassword')}
                variant="standard"
                error={passwordForm.formState.errors?.confirmPassword?.message != null}
                helperText={passwordForm.formState.errors?.confirmPassword?.message?.toString()}
              />
              <FormControlLabel
                control={<Checkbox
                  checked={showPassword}
                  color="secondary"
                  onChange={handlePasswordVisibilityChange}
                />}
                label={t('Recovery.showPassword')}
              />
              <Button type="submit" variant="contained" color="primary">
                {t('Recovery.updatePassword')}
              </Button>
            </FormControl>
          </RecoveryForm>
        )
      case Status.Success:
        return (
          <>
            <Header>{ t('Recovery.success') }</Header>
            <RedirectionFooter>
              <LinkTo as={Link} to="/login">{t('Recovery.goToLogin')}</LinkTo>
            </RedirectionFooter>
          </>
        )
    }
  }

  return (
    <MyContainer>
      <Title>{ t('Recovery.title') }</Title>
      <RecoveryWhiteBoxContainer>
        { renderStatus(currentStatus) }
      </RecoveryWhiteBoxContainer>
    </MyContainer>
  )
}

export default PasswordRecovery
