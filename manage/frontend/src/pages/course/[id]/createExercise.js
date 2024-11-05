import {useSession} from 'next-auth/react'
import {UploadOutlined} from '@ant-design/icons'
import {Button, Form, Input, Modal, Upload} from 'antd'

import {getCdnUploadUrl} from '../../../common/base'
import {normFile} from '../../../common/util/file'

const CreateLesson = ({modalOpen, onClose, handleCreate}) => {
  const {data: session} = useSession()
  const [form] = Form.useForm()

  const onFinish = (values) => {
    handleCreate(values, form)
  }
  
  const handleUploadChange = (type) => ({fileList}) => {
    form.setFieldsValue({[type]: fileList})
  }

  const submitHandle = () => {
    form
      .validateFields()
      .then((fieldsValue) => {
        const {video, audio, ...rest} = fieldsValue

        const payload = {
          video: video ? video.filter(item => item.status === 'done').map(item => ({
            uid: item.uid,
            name: item.name,
            url: item.response,
          })) : [],
          audio: audio ? audio.filter(item => item.status === 'done').map(item => ({
            uid: item.uid,
            name: item.name,
            url: item.response,
          })) : [],
          ...rest,
        }
        handleCreate(payload, form)
      }).then(() => {
        onClose()
      })
      .catch((errorInfo) => {
        console.error('Error:', errorInfo)
      })
  }

  return (
    <Modal
      open={modalOpen}
      title='Дасгал бүртгэх'
      onCancel={onClose}
      onOk={submitHandle}
      okText={'Бүртгэх'}
      cancelText='Болих'
      maskClosable={false}
    >
      <Form form={form} layout='vertical' onFinish={onFinish}>
        <Form.Item
          name='exercise'
          label='Дасгалын нэр'
          onFinish={submitHandle}
          rules={[{required: true, message: 'Хичээл оруулна уу!'}]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          name='audio'
          label='Аудио'
          valuePropName='fileList'
          getValueFromEvent={normFile}
        >
          <Upload
            name='file'
            accept='audio/*'
            listType='picture'
            headers={{'X-Auth-Token': session?.token}}
            action={getCdnUploadUrl()}
            onChange={handleUploadChange('audio')}
          >
            {form.getFieldValue('audio')?.length >= 1 ? null : <Button icon={<UploadOutlined />}>Аудио оруулах</Button>}
          </Upload>
        </Form.Item>

        <Form.Item
          name='video'
          label='Видео'
          valuePropName='fileList'
          getValueFromEvent={normFile}
        >
          <Upload
            name='file'
            accept='video/*'
            listType='picture'
            headers={{'X-Auth-Token': session?.token}}
            action={getCdnUploadUrl()}
            onChange={handleUploadChange('video')}
          >
            {form.getFieldValue('video')?.length >= 1 ? null : <Button icon={<UploadOutlined />}>Видео оруулах</Button>}
          </Upload>
        </Form.Item>
      </Form>
    </Modal>
  )
}
export default CreateLesson