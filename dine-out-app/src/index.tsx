import * as React from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App'
import './theme'
import reportWebVitals from './reportWebVitals'

// import i18n (needs to be bundled ;))
import { I18nextProvider } from 'react-i18next'
import i18n from './common/i18n/i18n'
import { SnackbarProvider } from 'notistack'

const container = document.getElementById('app')
if (container != null) {
  const root = createRoot(container) // createRoot(container!) if you use TypeScript
  // TODO: REMOVE ON DEPLOY StrictMode
  root.render(
    // <React.StrictMode>
        <I18nextProvider i18n={i18n}>
            <SnackbarProvider>
                <App/>
            </SnackbarProvider>
        </I18nextProvider>
    // </React.StrictMode>
  )
}

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
