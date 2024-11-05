import {useEffect, useState} from 'react'
import {NextSeo} from 'next-seo'
import {observer} from 'mobx-react-lite'
import dynamic from 'next/dynamic'
import {
  Upload,
  Input,
  Button,
  Card,
  Form,
  Typography,
  Popconfirm,
  Table,
  InputNumber,
  Select,
} from 'antd'
import {UserOutlined, UploadOutlined} from '@ant-design/icons'
import {useRouter} from 'next/router'
import {useStore} from '../../context/mobxStore'
import {useSession} from 'next-auth/react'

const PageHeader = dynamic(() =>
  import('../../components/elements/pageHeader')
)

const UpdateBook = observer(() => {
  const [form] = Form.useForm()
  const oxfordthinkersStore = useStore('oxfordThinkersStore')
  const referenceDataStore = useStore('referenceDataStore')
  const router = useRouter()
  const {data: session} = useSession()

  const [selectedBook, _setSelectedBook] = useState([
    {
      label: 'Oxford thinkers 1',
      key: 'Oxford thinkers 1',
      icon: <UserOutlined />,
    },
    {
      label: 'Oxford thinkers 2',
      key: 'Oxford thinkers 2',
      icon: <UserOutlined />,
    },
    {
      label: 'Oxford thinkers 3',
      key: 'Oxford thinkers 3',
      icon: <UserOutlined />,
    },
    {
      label: 'Oxford thinkers 4',
      key: 'Oxford thinkers 4',
      icon: <UserOutlined />,
    },
    {
      label: 'Oxford thinkers 5',
      key: 'Oxford thinkers 5',
      icon: <UserOutlined />,
    },
    {
      label: 'Oxford thinkers 6',
      key: 'Oxford thinkers 6',
      icon: <UserOutlined />,
    },
  ])
  const [selectedBookType, _setSelectedBookType] = useState('Сонгох')

  useEffect(() => {
    referenceDataStore.fetchSelect(
      {typeCode: 'OXFORDTHINKERS_TYPE'},
      session?.token
    )
  }, [])

  useEffect(() => {
    oxfordthinkersStore.fetchOne(session?.token, router.query.id)
  }, [])

  const handleRegisterOxford = async (values) => {
    console.log('Submitting:', values)
    try {
      const response = await oxfordthinkersStore.update(session?.token, values)
      if (response.ok) {
        router.push('/course')
      } else {
        console.error('Failed to register:', await response.text())
      }
    } catch (error) {
      console.error('Error creating book:', error)
    }
  }

  const onFinish = (values) => {
    console.log('Form values:', values)
    const formData = {
      ...values,
      name: selectedBook,
      type: selectedBookType,
      lessons: data,
    }
    handleRegisterOxford(formData)
  }

  const handleCancel = () => {
    router.push('/course')
  }

  const EditableCell = ({
    editing,
    dataIndex,
    title,
    inputType,
    children,
    ...restProps
  }) => {
    const inputNode = inputType === 'number' ? <InputNumber /> : <Input />
    return (
      <td {...restProps}>
        {editing ? (
          <Form.Item
            name={dataIndex}
            style={{margin: 0}}
            rules={[{required: true, message: `Please Input ${title}!`}]}
          >
            {inputNode}
          </Form.Item>
        ) : (
          children
        )}
      </td>
    )
  }

  const [data, setData] = useState([])
  const [editingKey, setEditingKey] = useState('')
  const isEditing = (record) => record.key === editingKey

  const edit = (record) => {
    form.setFieldsValue({
      topic: '',
      lesson: '',
      exercise: '',
      audio: '',
      video: '',
      ...record,
    })
    setEditingKey(record.key)
  }

  const cancel = () => {
    setEditingKey('')
  }

  const handleDelete = (key) => {
    const newData = data.filter((item) => item.key !== key)
    setData(newData)
  }

  const save = async (key) => {
    try {
      const row = await form.validateFields()
      const newData = [...data]
      const index = newData.findIndex((item) => key === item.key)
      if (index > -1) {
        const item = newData[index]
        newData.splice(index, 1, {...item, ...row})
        setData(newData)
        setEditingKey('')
      } else {
        newData.push(row)
        setData(newData)
        setEditingKey('')
      }
    } catch (errInfo) {
      console.log('Validate Failed:', errInfo)
    }
  }

  const columns = [
    {title: 'Сэдэв', dataIndex: 'topic', width: '20%', editable: true},
    {title: 'Хичээл', dataIndex: 'lesson', width: '15%', editable: true},
    {title: 'Дасгал', dataIndex: 'exercise', width: '15%', editable: true},
    {
      title: 'Аудио',
      dataIndex: 'audio',
      width: '15%',
      render: (_, record) =>
        isEditing(record) ? (
          <Upload
            accept='audio/mp3,audio/wav,audio/mpeg'
            beforeUpload={() => false}
            onChange={(info) => {
              const file = info.fileList[0]?.originFileObj
              const newData = [...data]
              const index = newData.findIndex(
                (item) => item.key === record.key
              )
              if (index > -1) {
                newData[index].audio = file ? file.name : ''
                setData(newData)
              }
            }}
          >
            <Button icon={<UploadOutlined />}>Upload Audio</Button>
          </Upload>
        ) : (
          record.audio
        ),
    },
    {
      title: 'Видео',
      dataIndex: 'video',
      width: '27%',
      render: (_, record) =>
        isEditing(record) ? (
          <Upload
            accept='video/mp4,video/avi,video/quicktime'
            beforeUpload={() => false}
            onChange={(info) => {
              const file = info.fileList[0]?.originFileObj
              const newData = [...data]
              const index = newData.findIndex(
                (item) => item.key === record.key
              )
              if (index > -1) {
                newData[index].video = file ? file.name : ''
                setData(newData)
              }
            }}
          >
            <Button icon={<UploadOutlined />}>Upload Video</Button>
          </Upload>
        ) : (
          record.video
        ),
    },
    {
      title: 'Үйлдэл',
      dataIndex: 'operation',
      render: (_, record) => {
        const editable = isEditing(record)
        return editable ? (
          <span>
            <Typography.Link
              onClick={() => save(record.key)}
              style={{marginRight: 20}}
            >
							Save
            </Typography.Link>
            <Popconfirm title='Sure to cancel?' onConfirm={cancel}>
              <a>Cancel</a>
            </Popconfirm>
          </span>
        ) : (
          <span>
            <Typography.Link
              disabled={editingKey !== ''}
              onClick={() => edit(record)}
              style={{marginRight: 20}}
            >
							Edit
            </Typography.Link>
            <Popconfirm
              title='Are you sure to delete?'
              onConfirm={() => handleDelete(record.key)}
            >
              <a style={{color: 'red'}}>Delete</a>
            </Popconfirm>
          </span>
        )
      },
    },
  ]

  const mergedColumns = columns.map((col) => {
    if (!col.editable) {
      return col
    }
    return {
      ...col,
      onCell: (record) => ({
        record,
        inputType: col.dataIndex === 'Сэдэв' ? 'number' : 'text',
        dataIndex: col.dataIndex,
        title: col.title,
        editing: isEditing(record),
      }),
    }
  })

  const addLine = () => {
    form.resetFields()
    const newKey = (data.length + 1).toString()
    const newRow = {
      key: newKey,
      topic: '',
      lesson: '',
      exercise: '',
      audio: '',
      video: '',
    }
    setData([...data, newRow])
    setEditingKey(newKey)
  }

  const {...initialValues} = oxfordthinkersStore?.current
  console.log('initialValues ', initialValues)
  return (
    <>
      <NextSeo title='Сургалт - UniCourse' />
      <PageHeader
        routes={[{title: 'Нүүр', link: '/'}]}
        title='Хичээл бүртгэх'
      />
      <Card title='Oxford Thinkers'>
        <Card>
          <Form
            form={form}
            layout='vertical'
            onFinish={onFinish}
            initialValues={initialValues && initialValues &&  initialValues}
          >
            <Form.Item name='name' label='Сургалтын нэр'>
              <Select>
                {selectedBook.map((item) => (
                  <Select.Option key={item.key}>{item.name}</Select.Option>
                ))}
              </Select>
              {/* <Dropdown overlay={bookMenu}>
								<Button>
									<Space>
										{selectedBook} <DownOutlined />
									</Space>
								</Button>
							</Dropdown> */}
            </Form.Item>

            <Form.Item name='type' label='Сургалтын төрөл'>
              <Input />
              {/* <Dropdown overlay={bookTypeMenu}>
								<Button>
									<Space>
										{selectedBookType} <DownOutlined />
									</Space>
								</Button>
							</Dropdown> */}
            </Form.Item>
            <Button
              onClick={addLine}
              type='primary'
              style={{marginBottom: 16}}
            >
              Хичээл нэмэх
            </Button>

            <Table
              components={{
                body: {cell: EditableCell},
              }}
              bordered
              dataSource={data}
              columns={mergedColumns}
              rowClassName='editable-row'
              pagination={{onChange: cancel}}
            />
            <Button
              type='primary'
              htmlType='submit'
              style={{marginTop: '10px'}}
            >
              Хадгалах
            </Button>
            <Button
              type='default'
              onClick={handleCancel}
              style={{marginLeft: '10px'}}
            >
              Цуцлах
            </Button>
          </Form>
        </Card>
      </Card>
    </>
  )
})

export default UpdateBook
