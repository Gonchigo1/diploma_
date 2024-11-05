import {useEffect} from 'react'
import {useSession} from 'next-auth/react'
import {Form, Input, Modal, Button} from 'antd'
import {observer} from 'mobx-react-lite'
import {Upload} from 'antd'
import {UploadOutlined} from '@ant-design/icons'
import {getCdnUploadUrl} from '../../common/base'
import {normFile} from '../../common/util/file'
import {useStore} from '../../context/mobxStore'

const FormItem = Form.Item
const UpdateExercise = observer(
  ({modalOpen, handleUpdate, handleCreate, editData, onClose}) => {
    const [form] = Form.useForm()
    const {data: session} = useSession()
    const exerciseStore = useStore('exerciseStore')

    useEffect(() => {
      if (modalOpen && session?.token) {
        form.resetFields()

        const initialValues = {
          ...editData,
          audio: editData?.audio ? editData.audio.map(file => ({
            uid: file.uid,
            name: file.name,
            url: file.url,
            status: 'done'
          })) : [],
          video: editData?.video ? editData.video.map(file => ({
            uid: file.uid,
            name: file.name,
            url: file.url,
            status: 'done'
          })) : [],
        }

        form.setFieldsValue(initialValues)
      }
    }, [editData, modalOpen, session?.token])

    const refreshTable = (params) => {
      exerciseStore?.fetchList(session?.token, params)
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
              url: item.url || item.response,
            })) : [],
            audio: audio ? audio.filter(item => item.status === 'done').map(item => ({
              uid: item.uid,
              name: item.name,
              url: item.url || item.response,
            })) : [],
            ...rest,
          }

          if (editData?.id) {
            return handleUpdate({...editData, ...payload}, form)
          } else {
            return handleCreate(payload, form)
          }
        })
        .then(() => {
          onClose()
          refreshTable()
        })
        .catch((errorInfo) => {
          console.error('Error:', errorInfo)
        })
    }

    const backHandle = () => {
      form.resetFields()
      onClose()
    }

    const handleUploadChange = (type) => ({fileList}) => {
      form.setFieldsValue({[type]: fileList})

      const uploadedFiles = fileList.filter(file => file.status === 'done')
      if (uploadedFiles.length > 0) {
        refreshTable()
      }
    }

    return (
      <Modal
        title={editData?.id ? 'Дасгал засварлах' : 'Дасгал үүсгэх'}
        open={modalOpen}
        onCancel={backHandle}
        onOk={submitHandle}
        footer={[
          <Button key='back' onClick={backHandle}>
                        Cancel
          </Button>,
          <Button key='submit' type='primary' onClick={submitHandle}>
            {editData?.id ? 'Шинэчлэх' : 'Үүсгэх'}
          </Button>
        ]}
      >
        <Form
          form={form}
          layout='vertical'
        >
          <FormItem
            name='exercise'
            label='Exercise Name'
            rules={[{required: true, message: 'Дасгалын нэрийг оруулна уу'}]}
          >
            <Input placeholder='Дасгалын нэр' />
          </FormItem>

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
              {form.getFieldValue('audio')?.length >= 1 ? null :
                <Button icon={<UploadOutlined />}>Аудио оруулах</Button>
              }
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
              {form.getFieldValue('video')?.length >= 1 ? null :
                <Button icon={<UploadOutlined />}>Видео оруулах</Button>
              }
            </Upload>
          </Form.Item>

        </Form>
      </Modal>
    )
  }
)

export default UpdateExercise
