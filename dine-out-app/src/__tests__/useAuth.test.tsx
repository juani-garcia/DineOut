import { useAuth, AuthProvider } from '@/hooks/auth/useAuth'
import { LocalStorageMock } from '@/__tests__/mocks/LocalStorageMock'
import { fireEvent, render, screen } from '@testing-library/react'

Object.defineProperty(window, 'localStorage', {
  value: new LocalStorageMock()
})
const token = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb28uZGluZW91dC5kaW5lckBnbWFpbC5jb20iLCJleHAiOjE3MDMwMTUwMTIsInVzZXJJZCI6MTEsInJvbGVzIjoiRElORVI7In0.JpbSsLKi6yjUV9poTKgA5osDlz38PdvFLEhNjHoIejw'
const userString = '{"sub":"foo.dineout.diner@gmail.com","exp":1703015012,"userId":11,"roles":"DINER;"}'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
const CustomTest = () => {
  const { logout, setToken, getToken, user } = useAuth()

  return (
      // eslint-disable-next-line react/react-in-jsx-scope
      <div>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <div data-testid="getToken">{JSON.stringify(getToken())}</div>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <div data-testid="user">{JSON.stringify(user)}</div>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <button onClick={() => { setToken(token) }} aria-label="login">
          Login
        </button>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <button onClick={() => {
          logout()
        }} aria-label="logout">
          LogOut
        </button>
      </div>
  )
}

test('Should render initial values', () => {
  render(
      // eslint-disable-next-line react/react-in-jsx-scope
      <AuthProvider>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <CustomTest />
      </AuthProvider>
  )

  expect(screen.getByTestId('getToken')).toHaveTextContent('null')
  expect(screen.getByTestId('user')).toHaveTextContent('null')
})

test('Should Login', () => {
  render(
      // eslint-disable-next-line react/react-in-jsx-scope
      <AuthProvider>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <CustomTest />
      </AuthProvider>
  )
  const loginButton = screen.getByRole('button', { name: 'login' })
  fireEvent.click(loginButton)
  expect(screen.getByTestId('getToken')).toHaveTextContent(token)
  expect(screen.getByTestId('user')).toHaveTextContent(userString)
})

test('Should Logout', () => {
  render(
      // eslint-disable-next-line react/react-in-jsx-scope
      <AuthProvider>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <CustomTest />
      </AuthProvider>
  )
  const loginButton = screen.getByRole('button', { name: 'login' })
  fireEvent.click(loginButton)
  expect(screen.getByTestId('getToken')).toHaveTextContent(token)
  expect(screen.getByTestId('user')).toHaveTextContent(userString)

  const logoutButton = screen.getByRole('button', { name: 'logout' })
  fireEvent.click(logoutButton)
  expect(screen.getByTestId('getToken')).toHaveTextContent('null')
  expect(screen.getByTestId('user')).toHaveTextContent('null')
})
