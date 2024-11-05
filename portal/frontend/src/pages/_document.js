import Document, {Html, Head, Main, NextScript} from 'next/document'

class StarterDocument extends Document {
  static async getInitialProps(ctx) {
    const initialProps = await Document.getInitialProps(ctx)
    return {...initialProps}
  }

  render() {
  return (
    <Html>
      <Head>
        <link rel='shortcut icon' href='/images/logo/main-logo.svg'/>
        <link
          rel='preload'
          href='/fonts/Montserrat/Montserrat-Bold.ttf'
          as='font'
          crossOrigin=''
        />
        <link
          rel='preload'
          href='/fonts/Montserrat/Montserrat-Medium.ttf'
          as='font'
          crossOrigin=''
        />
        <link
          rel='preload'
          href='/fonts/Montserrat/Montserrat-Regular.ttf'
          as='font'
          crossOrigin=''
        />
      </Head>
      <body>
        <Main/>
        <NextScript/>
      </body>
    </Html>
  )
  }
}

export default StarterDocument