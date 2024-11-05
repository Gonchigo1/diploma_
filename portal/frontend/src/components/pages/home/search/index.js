import {observer} from 'mobx-react-lite'
import dynamic from 'next/dynamic'
import {Button, Col, Form, Input, Row, Select} from 'antd'

import styles from './search.module.scss'

const MaterialForm = dynamic(() => import('@components/elements/materialForm'))

const Search = observer(() => {
  const [form] = Form.useForm()

  return (
    <>
      <div className={styles.wrapper}>
        <div className='container'>
          <Form
            form={form}
            layout='horizontal'
            className={styles.formWrapper}
          >
            <Row gutter={10}>
              <Col xs={20} sm={20} md={18} lg={20}>
                <Row gutter={0}>
                  <Col xs={12} sm={12} md={11} lg={11}>
                    <MaterialForm label='Ангилал сонгох'>
                      <Form.Item name='category'>
                        <Select
                          showSearch
                          placeholder="Ангилал сонгох" optionFilterProp="label"
                          filterSort={(optionA, optionB) => (optionA?.label ?? '').toLowerCase().localeCompare((optionB?.label ?? '').toLowerCase())
                          }
                          allowClear
                          options={[
                            {
                              value: '1',
                              label: 'Бестселлэр',
                            },
                            {
                              value: '2',
                              label: 'Монгол зохиолч, зураачдын ном',
                            },
                            {
                              value: '3',
                              label: 'Түүхэн ном',
                            },
                            {
                              value: '4',
                              label: 'Хүүхдийн ном',
                            },
                            {
                              value: '5',
                              label: 'Шинэ ном',
                            },
                            {
                              value: '6',
                              label: 'График роман',
                            },
                          ]}
                        />
                      </Form.Item>
                    </MaterialForm>
                  </Col>
                  <Col xs={12} sm={12} md={13} lg={13}>
                    <MaterialForm label='Номын нэрээ оруулна уу!'>
                      <Form.Item name='name'>
                        <Input placeHolder='Номын нэрээ оруулна уу!' allowClear/>
                      </Form.Item>
                    </MaterialForm>
                  </Col>
                </Row>
              </Col>
              <Col xs={4} sm={4} md={6} lg={4}>
                <Form.Item style={{marginInlineEnd: 0}}>
                  <Button
                    // type='primary'
                    htmlType='submit'
                    className={styles.searchBtn}
                  >
                    Хайх
                  </Button>
                </Form.Item>
              </Col>
            </Row>
          </Form>
        </div>
      </div>
    </>
  )
})

export default Search