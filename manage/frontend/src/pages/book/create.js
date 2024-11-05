import {useEffect, useState} from 'react'
import {useSession} from 'next-auth/react'
import {useRouter} from 'next/router'
import {NextSeo} from 'next-seo'
import {observer} from 'mobx-react-lite'
import {Button, Form, Input, Select, Row, Col, Upload,Modal, message} from 'antd'
import {UploadOutlined} from '@ant-design/icons'

import {normFile} from '../../common/util/file'
import {getCdnUploadUrl} from '../../common/base'
import {useStore} from '../../context/mobxStore'

const FormItem = Form.Item

const createBook = observer(({editData, refreshTable,onClose, open, title}) => {
  const router = useRouter()
  const {data: session = {}} = useSession()
  const [form] = Form.useForm()
  const bookStore = useStore('bookStore')
  const bookTypeStore = useStore('bookTypeStore')

  const [previewTitle, setPreviewTitle] = useState('')
  const [previewOpen, setPreviewOpen] = useState(false)
  const [previewImage, setPreviewImage] = useState('')
  const [_fileList, setFileList] = useState([])
  
  const [_bookData, setBookData] = useState({
    image: null,
    name: '',
    type: '',
  })
  const {image} = editData || {}

  useEffect(() => {
    bookTypeStore.fetchSelect({typeCode: 'BOOK_TYPE'}, session?.token)
  }, [session])

  const getBase64 = (file) =>
    new Promise((resolve, reject) => {
      const reader = new FileReader()
      reader.readAsDataURL(file)
      reader.onload = () => resolve(reader.result)
      reader.onerror = (error) => reject(error)
    })

  const handleChange = ({fileList: newFileList}) => {
    setFileList(newFileList)
    if (newFileList.length > 0) {
      const updatedImages = newFileList.map((file) => ({
        uid: file.uid,
        name: file.name,
        url: file.response ? file.response.url : null,
        status: file.status,
        fileId: file.fileId || null,
      }))

      setBookData((prevData) => ({
        ...prevData,
        image: updatedImages,
      }))
      console.log('File selected:', updatedImages)
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
  const handlePreview = async (file) => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj)
    }
    setPreviewImage(file.url || file.preview)
    setPreviewOpen(true)
    setPreviewTitle(file.name || file.url.substring(file.url.lastIndexOf('/') + 1))

  }

  const submitHandle = () => {
    form
      .validateFields()
      .then((fieldsValue) => {
        const {image, ...rest} = fieldsValue
        let payload = {}
        let correctBook = []
        if (image && image.length !== 0) {
          image.forEach((item) => {
            if (item.response && item.status === 'done') {
              correctBook.push({
                uid: item.uid,
                name: item.name,
                url: item.response,
              })
            }
          })
        }
        payload = {image: correctBook, ...rest}
        createUpdate(payload, form)
        message.success('Сургалт амжилттай бүртгэгдлээ!')
        form.resetFields()

      })
      .then(() => {
        onClose()
        refreshTable()
      })
      .catch((errorInfo) => {
        console.error('Error:', errorInfo)
        message.error('Сургалт бүртгэхэд алдаа гарлаа.')

      })
  }

  const handleCreate = (payload, form) => {
    bookStore.create(session?.token, payload).then((response) => {
      if (response && response.result) {
        message.success('Мэдээлэл амжилттай бүртгэлээ')
        form.resetFields()
        onClose()
        refreshTable() 
      }
      router.push('/book') 
    }).catch((error) => {
      message.error('Бүртгэхэд алдаа гарлаа')
    })
  }
    
  const backHandle = () => {
    form.resetFields()
    onClose()
  }

  const handleCancelPreview = () => setPreviewOpen(false)

  return (
    <>
      <NextSeo title='Сургалт бүртгэх - UniCourse' />
      <Modal
        open={open}
        onCancel={backHandle}
        title={title}
        maskClosable={false}
        footer={
          <>
            <Button
              type='default'
              onClick={backHandle}
            >
                Цуцлах
            </Button>
            <Button
              type='primary'
              htmlType='submit'
              onClick={submitHandle}
            >
                Бүртгэх
            </Button>
          </>
        }
      >
        <Form
          title='Сургалт'
          onCancel={backHandle}
          footer={null}
          form={form}
          layout='vertical'
          onFinish={submitHandle}
        >
          <Row gutter={20}>
            <Col span={24}>
              <Form.Item
                name='name'
                label='Сургалтын нэр'
                rules={[
                  {required: true, message: 'Сургалтын нэрийг оруулна уу!'},
                ]}
              >
                <Input name='name' />
              </Form.Item>
              <Form.Item
                name='type'
                label='Сургалтын төрөл'
                rules={[
                  {required: true, message: 'Сургалтын төрлыг оруулна уу!'},
                ]}
              >
                <Select
                  allowClear
                  allowSearch
                  placeholder='Төрлийг сонгоно уу'
                >
                  {bookTypeStore?.selectData?.map((item) => (
                    <Select.Option key={item?.id} value={item.code}>
                      {item.name}
                    </Select.Option>
                  ))}
                </Select>
              </Form.Item>

              <FormItem
                noStyle
                shouldUpdate={(prevValues, curValues) =>
                  prevValues.image !== curValues.image
                }
              >
                {({getFieldValue}) => {
                  return (
                    <>
                      <FormItem
                        label='Зураг'
                        name='image'
                        valuePropName='fileList'
                        getValueFromEvent={normFile}
                        initialValue={(image && [image]) || []}
                        className='mb10'
                      >
                        <Upload
                          name='file'
                          accept='image/*'
                          listType='picture-card'
                          headers={{'X-Auth-Token': session?.token}}
                          data={{
                            entity: 'referenceIcon',
                            entityId: Math.random().toString(36).substring(2),
                          }}
                          action={getCdnUploadUrl()}
                          onPreview={handlePreview}
                          onChange={handleChange}
                        >
                          {getFieldValue('image') &&
                            getFieldValue('image').length > 0 ? null : (
                              <UploadOutlined />
                            )}
                        </Upload>
                      </FormItem>
                    </>
                  )
                }}
              </FormItem>
            </Col>
          </Row>
        </Form>
      </Modal>
      <Modal
        visible={previewOpen}
        title={previewTitle}
        footer={null}
        onCancel={handleCancelPreview}
      >
        <img alt='preview' style={{width: '100%'}} src={previewImage} />
      </Modal>
    </>
  )}
)

export default createBook