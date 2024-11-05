import {SessionProvider} from 'next-auth/react'
import dynamic from 'next/dynamic'
import {ConfigProvider} from 'antd'
import mnMn from 'antd/lib/locale/mn_MN'

import {MobxProvider} from '@context/mobxStore'
import CustomVars from '../styles/customVars.json'

import 'antd/dist/reset.css'
import '../styles/vars.scss'
import '../styles/global.scss'
import 'slick-carousel/slick/slick.scss'
import 'slick-carousel/slick/slick-theme.scss'
import Auth from "../middlewares/auth";

const LayoutWrapper = dynamic(() => import('../layouts'))

function StarterApp(
  {
    Component,
    pageProps: {session, ...pageProps},
    router
  }) {
  const canonicalUrl = `https://nepko.mn${router.asPath}`

  return (
    <MobxProvider>
      <SessionProvider session={session}>
        <ConfigProvider
          theme={CustomVars}
          locale={mnMn}
        >
          <LayoutWrapper layouts={Component?.layouts} layout={Component?.layout || 'defaultLayout'} {...pageProps}>
            {Component.appRoles ?
              <Auth appRoles={Component.appRoles}>
                <Component {...pageProps} />
              </Auth>
              :
              <Component {...pageProps} />
            }
          </LayoutWrapper>
        </ConfigProvider>
      </SessionProvider>
    </MobxProvider>
  )
}

export default StarterApp