import {observer} from 'mobx-react-lite'
import dynamic from 'next/dynamic'
import {Button, Col, Form, Input, Modal, Row, Spin} from 'antd'
import {useStore} from '@context/mobxStore'
import {NextSeo} from 'next-seo'
import {useState} from 'react'
import {useRouter} from 'next/router'
import {ArrowLeftOutlined} from '@ant-design/icons'

import styles from './recoverPassword.module.scss'

const Wrapper = dynamic(() => import('../index'))

const RecoverPassword = observer(() => {
  const [form] = Form.useForm()
  const authStore = useStore('authStore')
  const router = useRouter();

  const [loading, setLoading] = useState(false)
  const [requestSent, setRequestSent] = useState(false)

  const submit = () => {
    form.validateFields()
      .then(fieldsValue => {
        setLoading(true)
        authStore.resetPassword(fieldsValue)
          .then(response => {
            if (response.result) {
              setRequestSent(true)
              Modal.success({
                title: 'Амжилттай',
                content: 'Нууц үг сэргээх холбоос таны имэйл рүү илгээгдлээ.',
                okText: 'Хаах',
                onOk: () => router.push('/auth/login')
              })
            } else {
              Modal.error({
                title: 'Алдаа',
                content: response.message,
                okText: 'Алдаа'
              })
            }
            setLoading(false)
          })
      })
      .catch(errorInfo => {
        console.log('password recovery error', errorInfo)
      })
  }

  return (
    <Wrapper>
      <NextSeo title='Нууц үг сэргээх'/>
      <Spin spinning={loading}>
        <div className={styles.content}>
          <h2>Нууц үг сэргээх</h2>
          <div className={styles.bottom}>
            <Form
              form={form}
              size='large'
              layout='vertical'
            >
              <Form.Item
                name='email'
                label='Имэйл'
                rules={[
                  {
                    required: true,
                    type: 'email',
                    message: 'Бүртгэлтэй имэйл хаягаа оруулна уу!'
                  }
                ]}
              >
                <Input placeHolder='Бүртгэлтэй имэйл хаягаа оруулна уу' />
              </Form.Item>
              <br/>
              <Form.Item>
                <Row gutter={15}>
                  <Col xs={24} sm={24} md={12}>
                    <Button
                      block
                      onClick={() => router.back()}
                    >
                      <ArrowLeftOutlined /> Буцах
                    </Button>
                  </Col>
                  <Col xs={24} sm={24} md={12}>
                    <Button
                      block
                      type='primary'
                      onClick={submit}
                      disabled={requestSent}
                    >
                      {requestSent ? 'Холбоос илгээгдсэн' : 'Илгээх'}
                    </Button>
                  </Col>
                </Row>
              </Form.Item>
            </Form>
          </div>
        </div>
      </Spin>
    </Wrapper>
  )
})

export default RecoverPassword