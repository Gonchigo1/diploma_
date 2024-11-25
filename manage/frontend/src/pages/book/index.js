import {useEffect, useState} from 'react'
import {NextSeo} from 'next-seo'
import {observer} from 'mobx-react-lite'
import dynamic from 'next/dynamic'
import {useRouter} from 'next/router'
import {useSession} from 'next-auth/react'
import {Button, Table, Popconfirm, Form,Card, message, Image, Tooltip, Divider, Tag, Input} from 'antd'
import {
  ArrowRightOutlined,
  CloseCircleOutlined, DeleteTwoTone,
  EyeOutlined, PlusOutlined,
  SaveOutlined
} from '@ant-design/icons'
import numeral from 'numeral'

import {useStore} from '../../context/mobxStore'

const PageHeader = dynamic(() => import('../../components/elements/pageHeader'))

const CreateBookModal = dynamic(() => import('./create'))
const {Item: FormItem} = Form

const Book = observer(() => {
  const router = useRouter()
  const {data: session = {}} = useSession()
  const [form] = Form.useForm()
  const bookStore = useStore('bookStore')
  const referenceDataStore = useStore('referenceDataStore')
  const {data, loading, searchFormValues} = bookStore
  const {list, pagination} = data

  const [editingKey, setEditingKey] = useState('')
  const [isModalVisible, setIsModalVisible] = useState(false)
  const [selectedEditData, setSelectedEditData] = useState(null)

  const isEditing = (record) => record.key === editingKey
  const PreviewMask = () => <><EyeOutlined /> Томруулах</>

  useEffect(() => {
    if (session?.token) {
      refreshTable()
    }
  }, [session])

  const refreshTable = (params) => {
    // referenceDataStore.fetchSelect({typeCode: 'BOOK_TYPE'}, session?.token)
    bookStore?.fetchList(session?.token, params)
  }
  const handleOpenModal = (editData = null) => {
    setSelectedEditData(editData)
    setIsModalVisible(true)
  }

  const handleCloseModal = () => {
    setIsModalVisible(false)
    setSelectedEditData(null)
  }
 
  const handleDeleteTopic = (record) => {
    bookStore.deleteOne(record.id, session?.token)
      .then((response) => {
        if (response.result === true) {
          message.success('Сургалт амжилттай устгалаа')
          bookStore.fetchList(session?.token)
        } else {
          message.error(`Сургалт устгахад алдаа гарлаа: ${response.message}`)
        }
      })
      .catch((e) => {
        message.error(`Сургалт устгахад алдаауу гарлаа: ${e.message}`)
      })
  }

  const save = async (key) => {
    try {
      const row = await form.validateFields()
      const newData = [...bookStore.data.list]
      const index = newData.findIndex((item) => key === item.key)

      if (index > -1) {
        const item = newData[index]
        newData.splice(index, 1, {...item, ...row})
        bookStore.data.list = newData
        setEditingKey('')
      } else {
        newData.push(row)
        bookStore.data.list = newData
        setEditingKey('')
      }
    } catch (errInfo) {
      console.log('Validate Failed:', errInfo)
    }
  }

  const handleTableChange = (pagination) => {
    const params = {
      ...searchFormValues,
      currentPage: pagination.current,
      pageSize: pagination.pageSize
    }

    bookStore.setSearchFormValues(params)
    refreshTable(params)
  }

  const columns = [
    {
      title: '№',
      dataIndex: '',
      align: 'center',
      render: (_, __, index) => index + 1 + pagination.currentPage * pagination.pageSize,
      width: 50
    },
    {
      title: 'Зураг',
      dataIndex: 'image',
      width: 200,
      align: 'center',
      render: (images) => {
        if (images && images.length > 0) {
          const imageUrl = images[0].url
          return (
            <Image
              src={imageUrl}
              alt='bookImage'
              preview={{
                mask: <PreviewMask />
              }}
              width='100px'
            />
          )
        }
        return 'No Image'
      },
    },
    {
      title: 'Нэр',
      dataIndex: 'name',
      width: 200,
    },
    {
      title: 'Төрөл',
      dataIndex: 'type',
      width: 180,
      align: 'center',
      render: (text) => {
        return (
          <Tag color='blue'>
            {text}
          </Tag>
        )
      }
    },
    {
      title: 'Нийт сэдвийн тоо',
      dataIndex: 'countTopics',
      width: 180,
      align: 'center',
    },
    {
      title: 'Нийт хичээлийн тоо',
      dataIndex: 'countLessons',
      width: 180,
      align: 'center',
    },
    {
      title: 'Нийт дасгалын тоо',
      dataIndex: 'countExercises',
      width: 180,
      align: 'center',
    },
    {
      title: 'Үйлдэл',
      dataIndex: 'operation',
      align: 'center',
      width: 180,
      render: (_, record) => {
        const editable = isEditing(record)
        return editable ? (
          <>
            <Tooltip title='Хадгалах'>
              <Button
                type='link'
                icon={<SaveOutlined />}
                onClick={(e) => {
                  e.stopPropagation()
                  save(record.key)
                }}
              />
            </Tooltip>
            <Tooltip title='Цуцлах'>
              <Divider type={'vertical'} />
              <Popconfirm
                title='Цуцлах уу?'
                onConfirm={(e) => {
                  e.stopPropagation()
                  setEditingKey('')
                }}
                okText='Тийм'
                cancelText='Үгүй'>
                <Button
                  shape='circle'
                  icon={<CloseCircleOutlined />}
                  onClick={(e) => e.stopPropagation()}
                />
              </Popconfirm>
            </Tooltip>
          </>
        ) : (
          <>
            <Tooltip title={'Дэлгэрэнгүй харах'}>
              <Button
                type='link'
                onClick={(e) => {
                  handleViewDetails(record.id)
                  e.stopPropagation()
                }}
                icon={<ArrowRightOutlined />}
              />
            </Tooltip>
            <Tooltip title='Устгах'>
              <Divider type={'vertical'} />
              <Popconfirm
                title='Устгах уу?'
                onConfirm={(e) => {
                  e.stopPropagation()
                  handleDeleteTopic(record)
                }}
                okText='Тийм'
                cancelText='Үгүй'
              >
                <Button
                  shape='circle'
                  // type='dashed'
                  icon={<DeleteTwoTone twoToneColor={'red'} />}
                  onClick={(e) => e.stopPropagation()}
                />
              </Popconfirm>
            </Tooltip>
          </>
        )
      },
    },
  ]

  const handleViewDetails = (id) => {
    router.push(`/course/${id}`)
  }
  const handleFormReset = () => {
    form.resetFields()
    bookStore.setSearchFormValues({})
    refreshTable()
  }
  const handleSearch = () => {
    bookStore.setSearchFormValues(form.getFieldsValue())
    refreshTable(form.getFieldsValue())
  }
  const renderFilterForm = () => (
    <Form form={form} onFinish={handleSearch} layout='inline'>
      <FormItem
        label='Нэр'
        name='name'
        className='mb10'
      >
        <Input placeholder='Нэр' />
      </FormItem>
      <FormItem
        label='Төрөл'
        name='code'
        className='mb10'
      >
        <Input placeholder='Төрөл' />
      </FormItem>
      <FormItem>
        <Button type='primary' htmlType='submit'>
          Хайх
        </Button>
        <Button style={{marginLeft: 8}} onClick={handleFormReset}>
          Цэвэрлэх
        </Button>
      </FormItem>
    </Form>
  )
  return (
    <>
      <NextSeo title='Сургалтын жагсаалт - UniCourse' />
      <PageHeader
        routes={[
          {
            title: 'Нүүр',
            link: '/',
          },
          {
            title: 'Сургалт',
            link: '/book',
          },
        ]}
        title='Жагсаалт'
        action={
          <Button type='primary' onClick={() => handleOpenModal()} icon={<PlusOutlined />}>
            Сургалт бүртгэх
          </Button>
        }
      />
      <>
        <Card bordered={false}>
          {renderFilterForm()}
        </Card>
        <br />
        <>
          <Table
            size='middle'
            dataSource={list || []}
            columns={columns}
            loading={loading}
            onChange={handleTableChange}
            rowKey='id'
            pagination={{
              ...pagination,
              showQuickJumper: true,
              showSizeChanger: true,
              pageSizeOptions: ['20', '50', '100'],
              defaultPageSize: 100,
              locale: {
                items_per_page: '/ хуудас',
                jump_to: 'Шилжих',
                jump_to_confirm: 'Confirm',
                page: 'Хуудас',
                prev_page: 'Өмнөх',
                next_page: 'Дараах',
                prev_5: 'Өмнөх 5',
                next_5: 'Дараах 5',
                prev_3: 'Өмнөх 3',
                next_3: 'Дараах 3',
              },
            }}
            className='custom-table'
            title={() => (
              <div>
                Нийт сургалт: <b>{pagination?.total && numeral(pagination.total).format('0,0') || 0}</b>
              </div>
            )}
            onRow={(record) => {
              return {
                onClick: () => handleViewDetails(record.id)
              }
            }}
          />
          <CreateBookModal
            editData={selectedEditData}
            open={isModalVisible}
            onClose={handleCloseModal}
            title={selectedEditData ? 'Сургалт засах' : 'Сургалт бүртгэх'}
            refreshTable={() => {
              refreshTable(!refreshTable)
              bookStore.fetchList(session?.token)
            }}
          />
        </>
      </>
    </>
  )
})

export default Book
