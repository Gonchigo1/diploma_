import {observer} from 'mobx-react-lite'
import dynamic from 'next/dynamic'
import {NextSeo} from 'next-seo'
import {Button, Form, Input, Modal, Spin} from 'antd'
import {useEffect, useState} from 'react'
import {useRouter} from 'next/router'
import {signIn, useSession} from 'next-auth/react'
import {LockOutlined, UserOutlined} from '@ant-design/icons'

import styles from './login.module.scss'

const Wrapper = dynamic(() => import('../index'))

const LogIn = observer(() => {
  const { data: session } = useSession();
  const [form] = Form.useForm()
  const router = useRouter()
  const [loading, setLoading] = useState(false)

  useEffect(() => {
      if (session) {
          router.push('/teacher');
      }  }, [session, router])

  const submit = () => {
    form.validateFields()
      .then(fieldsValue => {
        setLoading(true)
        signIn('credentials', {...fieldsValue, redirect: false})
          .then(response => {
            setLoading(false)
            if (response?.error) {
              Modal.error({
                title: 'Анхааруулга',
                content: 'Нэвтрэх нэр эсвэл нууц үг буруу байна',
                okText: 'Хаах'
              })
            } else {
              router.push('/teacher')
            }
          })
            .catch(errorInfo => {
                console.error('login error', errorInfo)
            })
      })
        .catch(errorInfo => {
            console.error('login error', errorInfo)
        })
  }

  return (
    <Wrapper>
      <NextSeo title='Нэвтрэх'/>
      <Spin spinning={loading}>
        <div className={styles.content}>
          <h2>Нэвтрэх</h2>
          <div className={styles.bottom}>
            <Form
              form={form}
              size='large'
              layout='vertical'
            >
              <Form.Item
                name='username'
                hasFeedback
                label='Цахим хаяг'
                rules={[
                  {
                    required: true,
                    message: 'Имэйл хаяг оруулна уу'
                  },
                  {
                    type: 'email',
                    message: 'Имэйл хаягаа зөв оруулна уу',
                    warningOnly: true
                  }
                ]}
              >
                <Input
                  id='username'
                  prefix={<UserOutlined/>}
                  placeholder={'Цахим хаяг'}
                />
                </Form.Item>
                <Form.Item
                  name='password'
                  label='Нууц үг'
                  hasFeedback
                  rules={[
                    {
                      required: true,
                      message: 'Нууц үг оруулна үү!'
                    }
                  ]}
                >
                  <Input.Password
                    type='password'
                    placeHolder='Нууц үг'
                    prefix={<LockOutlined/>}
                  />
                </Form.Item>
                <br/>
                <Form.Item>
                  <Button block type='primary' onClick={submit}>
                    Нэвтрэх
                  </Button>
                </Form.Item>
            </Form>
          </div>
        </div>
      </Spin>
    </Wrapper>
  )
})

export default LogIn