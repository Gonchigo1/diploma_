import {useState, useEffect} from 'react'
import {NextSeo} from 'next-seo'
import dynamic from 'next/dynamic'
import {
  Button,
  Form,
  Input,
  Modal
} from 'antd'
import {UserOutlined, LockOutlined} from '@ant-design/icons'
import {signIn} from 'next-auth/react'
import {useRouter} from 'next/router'
import {observer} from 'mobx-react-lite'
import styles from '../../../layouts/login/loginLayout.module.scss'
import Link from 'next/link'

const Wrapper = dynamic(() => import('../index'))

const SignIn = observer(() => {
  const router = useRouter()
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    setLoading(false)
  }, [router])

  const onFinish = (values) => {
    setLoading(true)
    let cbUrl = router?.query?.callbackUrl || '/'
    const payload = Object.assign(values, {redirect: false, callbackUrl: cbUrl})
    signIn('credentials', payload)
      .then(response => {
        setLoading(false)
        if (response?.error) {
          Modal.error({
            title: 'Анхааруулга',
            content: response?.error,
            okText: 'Хаах'
          })
        } else {
          router.push('/')
        }
      })
  }

  return (
    <>
      <NextSeo title='Нэвтрэх - UniCourse'/>
      <Wrapper>
        <div className={styles.top}>
          <div className={styles.header}>
            <Link href='/'>
              <img alt='logo' className={styles.logo} src='/images/logo/main-logo.svg'/>
            </Link>
          </div>
        </div>
        <h2>Системд нэвтрэх</h2>
        <p>Хэрэглэгч та и-мейл хаяг болон нууц үгээ ашиглан системд нэвтэрнэ үү.</p>
        <Form
          size={'large'}
          onFinish={onFinish}
        >
          <Form.Item
            name='username'
            hasFeedback
            rules={[{required: true, message: 'Имэйл бичнэ үү'}]}
          >
            <Input
              size='large'
              prefix={<UserOutlined/>}
              // type='email'
              placeholder='Имэйл'
            />
          </Form.Item>
          <Form.Item
            name='password'
            hasFeedback
            rules={[{required: true, message: 'Нууц үг бичнэ үү'}]}
          >
            <Input.Password
              size='large'
              prefix={<LockOutlined/>}
              type='password'
              placeholder='Нууц үг'
            />
          </Form.Item>
          <Form.Item style={{marginBottom: 0}}>
            <Button type='primary' block htmlType='submit' loading={loading}>
              Нэвтрэх
            </Button>
          </Form.Item>
        </Form>
      </Wrapper>
    </>
  )
})

SignIn.layout = 'loginLayout'

export default SignIn
