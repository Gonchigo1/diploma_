import {useEffect, useState} from 'react'
import {useSession} from 'next-auth/react'
import {useRouter} from 'next/router'
import {NextSeo} from 'next-seo'
import {observer} from 'mobx-react-lite'
import dynamic from 'next/dynamic'
import {Button, Card, Form, Input, Select, Row, Col, Upload} from 'antd'
import {PlusOutlined,UploadOutlined} from '@ant-design/icons'

import {normFile} from '../../common/util/file'
import {getCdnUploadUrl} from '../../common/base'
import {useStore} from '../../context/mobxStore'

const FormItem = Form.Item

const PageHeader = dynamic(() =>
  import('../../components/elements/pageHeader')
)
const createBook = observer(({}) => {
  const router = useRouter()
  const {data: session} = useSession()
  const [form] = Form.useForm()
  const bookStore = useStore('bookStore')
  const bookTypeStore = useStore('bookTypeStore')
  const {icon} = editData || {}

  const [_previewOpen, setPreviewOpen] = useState(false)
  const [_previewImage, setPreviewImage] = useState('')
  const [_fileList, setFileList] = useState([
  ])
  const [_bookData, setBookData] = useState({
    image: null,
    name: '',
    type: '',

  })

  useEffect(() => {
    bookTypeStore.fetchSelect({typeCode: 'BOOK_TYPE'}, session?.token)
  }, [])

  const getBase64 = (file) =>
    new Promise((resolve, reject) => {
      const reader = new FileReader()
      reader.readAsDataURL(file)
      reader.onload = () => resolve(reader.result)
      reader.onerror = (error) => reject(error)
    })

  const handleCreate = (payload, form) => {
    bookStore.create(session?.token, payload).then((response) => {
      if (response && response.result) {
        message.success('Мэдээлэл амжилттай бүртгэлээ')
        form.resetFields()
        closeModal()
        if (refreshTable)
          refreshTable()
      }
    })
  }

  const _handleFileChange = (event) => {
    const file = event.target.files[0]
    setBookData((prevData) => ({
      ...prevData,
      image: file,
    }))
  }

  const _handleChange = ({fileList: newFileList}) => {
    setFileList(newFileList)
    if (newFileList.length > 0) {
      const file = newFileList[newFileList.length - 1].originFileObj
      setBookData((prevData) => ({
        ...prevData,
        image: file,
      }))
      console.log('File selected:', file)
    } else {
      setBookData((prevData) => ({
        ...prevData,
        image: null,
      }))
    }
  }

  const createUpdate = (payload, form) => {
    const newPayload = Object.assign({...editData, ...payload})
    if (newPayload?.id) {
      handleUpdate(newPayload, form)
    } else {
      handleCreate(newPayload, form)
    }
  }

  const _handlePreview = async (file) => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj)
    }
    setPreviewImage(file.url || file.preview)
    setPreviewOpen(true)
  }

  const _uploadButton = (
    <button
      style={{
        border: 0,
        background: 'none',
      }}
      type='button'
    >
      <PlusOutlined />
      <div
        style={{
          marginTop: 8,
        }}
      >
        Upload
      </div>
    </button>
  )

  const submitHandle = () => {
    form
      .validateFields()
      .then((fieldsValue) => {
        const {icon, ...rest} = fieldsValue

        let payload = {}
        let correctIcon = {}
        if (icon && icon.length !== 0) {
          icon.map((item) => {
            if (item.response && item.status === 'done') {
              console.log('session token:', session?.token)

              console.log('item,',item)
              correctIcon = {
                uid: item.uid,
                name: item.name,
                url: item.response,
              }
            } else {
              correctIcon = {}
            }
          })
          payload = Object.assign({icon: correctIcon})
        }

        payload = Object.assign(payload, {...rest})
        createUpdate(payload, form)
      })
      .catch((errorInfo) => {
        console.error(
          'Maroonj4f ! --> ~ file: createUpdateModal.js ~ line 102 ~ submitHandle ~ errorInfo',
          errorInfo
        )
      })
  }

  const _handleRegisterBook = async (values) => {
    console.log('test', values)
    bookStore.create(session?.token, values)
    router.push('/bookCategory')
  }
  
  const handleCancel = () => {
    router.push('/bookCategory')
  }

  return (
    <>
      <NextSeo title='Сургалт - UniCourse' />
      <PageHeader
        routes={[
          {
            title: 'Нүүр',
            link: '/',
          },
        ]}
        title='Сургалт'
        subTitle='Сургалт бүртгэх'
      />
      <Card title='Сургалт'>
        <Form
          title='Сургалт'
          onCancel={handleCancel}
          footer={null}
          form={form}
          layout='vertical'
          onFinish={submitHandle}
        >
          <Row gutter={20}>
            <Col span={5}>
              <Form.Item
                name='name'
                label='Сургалтын нэр'
                rules={[{required: true, message: 'Сургалтын нэрийг оруулна уу!'}]}
              >
                <Input name='name' />
              </Form.Item>
             
              <Form.Item
                name='type'
                label='Сургалтын төрөл'
                rules={[{required: true, message: 'Сургалтын зургийг оруулна уу!'}]}
              >
                <Select allowClear allowSearch placeholder='Төрлийг сонгоно уу'>
                  {bookTypeStore?.selectData?.map((item) => (
                    <Select.Option key={item?.id} value={item.code}>
                      {item.name}
                    </Select.Option>
                  ))}
                </Select>
              </Form.Item>
              <FormItem
                noStyle
                shouldUpdate={(prevValues, curValues) => prevValues.icon !== curValues.icon}
              >
                {({getFieldValue}) => {
                  const _imageList = getFieldValue('image') || [] // get the image field from form values

                  return <>
                    <FormItem
                      label='Зураг'
                      name='image'
                      valuePropName='fileList'
                      getValueFromEvent={normFile}
                      initialValue={icon && [icon] || []}
                      className='mb10'
                    >
                      <Upload
                        name='file'
                        accept='image/*'
                        listType='picture-card'
                        headers={{'X-Auth-Token': session?.token}}
                        data={{entity: 'referenceIcon', entityId: Math.random().toString(36).substring(2)}}
                        action={getCdnUploadUrl()}
                        multiple={false} // Only allow one file

                      >
                        {getFieldValue('icon') && getFieldValue('icon').length > 0 ? null :
                          <UploadOutlined/>
                        }
                      </Upload>
                    </FormItem>
                  </>
                }}
              </FormItem>
            </Col>
          </Row>

          <Form.Item>
            <Button type='primary' htmlType='submit' style={{marginLeft: '202px'}}>
              Бүртгэх
            </Button>
            <Button
              type='default'
              onClick={handleCancel}
              style={{marginLeft: '10px'}}
            >
              Цуцлах
            </Button>
          </Form.Item>
        </Form>
      </Card>

    </>
  )
})

export default createBook