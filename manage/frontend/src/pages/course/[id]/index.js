import {useEffect, useState} from 'react'
import {useRouter} from 'next/router'
import {useSession} from 'next-auth/react'
import {NextSeo} from 'next-seo'
import {observer} from 'mobx-react-lite'
import dynamic from 'next/dynamic'
import {
  Input,
  Button,
  Form,
  Popconfirm,
  Table,
  message,
  Modal, Tooltip, Divider, Row, Col
} from 'antd'
import {DeleteTwoTone, SaveOutlined,CloseOutlined,EditTwoTone, PlusCircleTwoTone, PlusOutlined
} from '@ant-design/icons'
import numeral from 'numeral'

import {useStore} from '../../../context/mobxStore'
import {checkAuthRole} from '../../../common/util/auth'

import styles from './id.module.scss'

const PageHeader = dynamic(() => import('../../../components/elements/pageHeader'))
const CreateLesson = dynamic(() => import('./createUpdateModal'))
const CreateTopic1 = dynamic(() => import('./createUpdate'))
const CreateExercise = dynamic(() => import('./createExercise'))
const LessonList = dynamic(() => import('../../../components/book/lessonList'))

const CreateTopic = observer(({editData}) => {
  const router = useRouter()
  const {data: session} = useSession()
  const {id: bookId} = router.query
  const [form] = Form.useForm()
  const oxfordThinkersStore = useStore('oxfordThinkersStore')
  const lessonStore = useStore('lessonStore')
  const bookStore = useStore('bookStore')
  const exerciseStore = useStore('exerciseStore')
  const {current} = bookStore
  const {data, loading, searchFormValues} = oxfordThinkersStore
  const {list, pagination} = data

  const [modalOpen, setModalOpen] = useState(false)
  const [modalOpenLesson, setModalOpenLesson] = useState(false)
  const [modalOpenExercise, setModalOpenExercise] = useState(false)
  const [currentTopicid, setCurrentTopic] = useState(null)
  const [currentExercise, _setCurrentExercises] = useState(null)
  const [editingKey, setEditingKey] = useState('')
  const [expandedRowKeys, setExpandedRowKeys] = useState([])
  const [showExercise, setShowExercise] = useState(false)
  const [_editingTopicName, setEditingTopicName] = useState('')

  useEffect(() => {
    if (bookId && session?.token) {
      fetchBookDetail()
      fetchBookTopics()
    }
  }, [bookId, session])

  const refreshTable = (params) => {
    if (bookId) {
      oxfordThinkersStore.fetchList(session?.token, {...params, bookId})
    }
  }
  const fetchBookDetail = async () => {
    bookStore.fetchOne(bookId)
  }

  const fetchBookTopics = async () => {
    if (bookId) {
      const params = { 
        ...searchFormValues,
        currentPage: pagination.current,
        pageSize: pagination.pageSize,
      }
      refreshTable(params)
    }
  }

  const handleTableChange = (pagination) => {
    const params ={ 
      ...searchFormValues,
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
    }
    oxfordThinkersStore.setSearchFormValues(params)
    refreshTable(params) 
  }

  const handleCreate = (fields, form) => {
    const payload = {
      bookId: bookId,
      ...fields,  
    }
    oxfordThinkersStore
      .create(session?.token, payload)
      .then((response) => {
        if (response.result === true && response.data) {
          message.success('Сургалтын сэдэв амжилттай бүртгэлээ')
          form.resetFields()
          setModalOpen(false)
          fetchBookTopics()

        } else {
          message.error(
            `Сургалтын сэдэв бүртгэхэд алдаа гарлаа: ${response.message}`
          )
        }
      })
      .catch((e) => {
        console.log(e)
        message.error(`Сургалтын сэдэв бүртгэхэд алдаа гарлаа: ${e.message}`)
      })
  }

  const handleCreate1 = (fields, form) => {
    const payload = {
      topicId: currentTopicid.id,
      bookId: bookId,
      lesson: '',
      ...fields,
    }
    lessonStore
      .create(session?.token, payload)
      .then((response) => {
        if (response.result === true && response.data) {
          message.success('Хичээл амжилттай бүртгэлээ')
          form.resetFields()
          setModalOpenLesson(false)
          refreshTable()
        } else {
          message.error(
            `Хичээл бүртгэхэд алдаа гарлаа: ${response.message}`
          )
        }
      })
      .catch((e) => {
        console.log(e)
        message.error(`Хичээл бүртгэхэд алдаа гарлаа: ${e.message}`)
      })
  }

  const handleCreateExercise = (fields, form) => {
    const payload = {
      topicId: currentExercise.topicId,
      bookId,
      lessonId: currentExercise?.id,
      exercise:currentExercise?.id,
      audio:'',
      video:'',
      pdf:'',
      ...fields,
    }
    exerciseStore
      .create(session?.token, payload)
      .then((response) => {
        if (response.result === true && response.data) {
          message.success('Дасгал амжилттай бүртгэлээ')
          form.resetFields()
          setModalOpenLesson(false)
        } else {
          message.error(
            `Дасгал бүртгэхэд алдаа гарлаа: ${response.message}`
          )
        }
      })
      .catch((e) => {
        console.log(e)
        message.error(`Дасгал бүртгэхэд алдаа гарлаа: ${e.message}`)
      })
  }
  
  const EditableCell = ({
    editing,
    dataIndex,
    title,
    children,
    ...restProps
  }) => {
    return (
      <td {...restProps}>
        {editing ? (
          <Form.Item
            name={dataIndex}
            style={{margin: 0}}
            rules={[{required: true, message: `Please Input ${title}!`}]}
          >
            <Input />
          </Form.Item>
        ) : (
          children
        )}
      </td>
    )
  }

  const isEditing = (record) => record.key === editingKey

  const cancel = () => {
    setEditingKey('')
  }
  const handleDelete = async (token, id) => {
    console.log('id ', id)
    try {
      await oxfordThinkersStore.delete(token, id) 
      console.log('Item deleted successfully')
      oxfordThinkersStore.fetchList(session?.token, {bookId})
    } catch (error) {
      console.error('Error deleting item:', error)
    }
  }

  const columns = [
    {
      title: '№',
      dataIndex: '',
      align: 'center',
      render: (_, __, index) => index + 1 + pagination.currentPage * pagination.pageSize,
      width: 50,
    },
    {
      title: 'Хичээл',
      dataIndex: 'name',
      editable: true,
      width: 200,
    },
    {
      title: 'Нийт сэдвийн тоо',
      dataIndex: 'countLessons',
      editable: false,
    },
    {
      title: 'Үйлдэл',
      width: 300,
      dataIndex: 'operation',
      render: (_, record) => {
        const editable = isEditing(record)
        return editable ? (
          <span>
            <Button
              icon={<SaveOutlined /> }
              onClick={() => {
                handleUpdate(record.key)
              }}
              style={{color: '#34d300'}}
            >
              Хадгалах
            </Button>
            <Divider type='vertical' />
            <Button
              icon={<CloseOutlined />}
              onClick={(e) => {
                e.stopPropagation()  
                cancel() 
              }}
              style={{color:'#e20000'}}
            >
               Цуцлах
            </Button>
          </span>
        ) : (
          <>
            {checkAuthRole('ROLE_OXFORD_THINKERS_MANAGE', session?.applicationRoles) ? (
              <>
                <Tooltip title='Засах'>
                  <Button
                    shape='circle'
                    disabled={editingKey !== ''}
                    
                    onClick={() => {edit(record) }}
                  
                    icon={<EditTwoTone />}
                  />
                </Tooltip>
                <Divider type='vertical' />
                <Tooltip title='Устгах'>
                  <Popconfirm
                    title='Устгах уу?'
                    onConfirm={() => handleDelete(session?.token, record.id)}
                    okText='Тийм'
                    cancelText='Үгүй'
                  >
                    <Button
                      shape='circle'
                      icon={<DeleteTwoTone twoToneColor='red' />}
                      onClick={(event) => {
                        event.stopPropagation()
                      }}
                    />
                  </Popconfirm>
                </Tooltip>
                <Divider type='vertical' />
                <Tooltip title='Сэдэв бүртгэх'>
                  <Button
                    shape='circle'
                    onClick={() => {
                      setCurrentTopic(record)
                      setModalOpenLesson(true)
                    }}
                    icon={<PlusCircleTwoTone style={{marginLeft: 1}} />}
                  />
                </Tooltip>
              </>
            ) : null}
          </>
        )
      },
    },
  ]

  const handleExpandRow = (record) => {
    const expandedKeys = expandedRowKeys.includes(record.key)
      ? expandedRowKeys.filter((key) => key !== record.key)
      : [...expandedRowKeys, record.key]

    setExpandedRowKeys(expandedKeys)
  }

  const exerciseColumns = [
    {
      title: 'Дасгалын нэр',
      dataIndex: 'exercise',
      key: 'exercise',
    },
    {
      title: 'Аудио',
      dataIndex: 'audio',
      render: (audios) => {
        if (audios && audios.length > 0 && audios[0].url) {
          return (
            <audio controls>
              <source src={audios[0].url} type='audio/mp3' />
              Таны веб хөтөч тухайн аудио тоглуулахыг дэмжихгүй байна.
            </audio>
          )
        }
        return <span>Аудио байхгүй</span>
      },
    },
    {
      title: 'Видео',
      dataIndex: 'video',
      render: (videos) => {
        if (videos && videos.length > 0 && videos[0].url) {
          return (
            <video controls style={{width: '200px' , height:'200px'}}>
              <source src={videos[0].url} type='video/mp4' />
              Таны веб хөтөч тухайн видео тоглуулахыг дэмжихгүй байна.
            </video>
          )
        }
        return <span>Видео байхгүй</span>
      },
    },
    {
      title: 'Pdf файл',
      dataIndex: 'pdf',
      render:(pdf) => {
        if (pdf && pdf.length > 0 && pdf[0].url) {
          return (
            <video controls style={{width: '200px' , height:'200px'}}>
              <source src={pdf[0].url} type='video/mp4' />
              Таны веб хөтөч тухайн видео тоглуулахыг дэмжихгүй байна.
            </video>
          )
        }
        return <span>Видео байхгүй</span>
      },
    },
  ]

  const paginationProps = {
    ...pagination,
    showQuickJumper: true,
    showSizeChanger: true,
    pageSizeOptions: [10, 20, 50, 100]
  }

  const edit = (record) => {
    form.setFieldsValue({name: record?.name})
    setEditingTopicName(record.name)
    setEditingKey(record.key)
  }

  const handleUpdate = async (key) => {
    try {
      const row = await form.validateFields()
      const newData = [...list]
      const index = newData.findIndex((item) => key === item.key)

      if (index > -1) {
        const item = newData[index]
        console.log('item ', item)
        const updatedItem = {...item, ...row}
        const payload = {id: updatedItem?.id, name: updatedItem?.name}

        const response = await oxfordThinkersStore.update(session?.token, payload)
        if (response.result === true) {
          newData.splice(index, 1, updatedItem) 
          oxfordThinkersStore.data.list = newData 
          setEditingKey('') 
          message.success('Сэдэв амжилттай шинэчлэгдлээ')
          refreshTable()
        } else {
          message.error('Сэдэв шинэчлэхэд алдаа гарлаа')
        }
      }
    } catch (errInfo) {
      console.log('Validate Failed:', errInfo)
      message.error('Сэдэв шинэчлэхэд алдаа гарлаа')
    }
  }
  
  const mergedColumns = columns.map((col) => {
    if (!col.editable) {
      return col
    }
    return {
      ...col,
      onCell: (record) => ({
        record,
        inputType: 'text', 
        dataIndex: col.dataIndex,
        title: col.title,
        editing: isEditing(record),
      })
    }
  })

  return (
    <>
      <NextSeo title='Сургалтын дэлгэрэнгүй - UniCourse' />
      <PageHeader
        hasBack
        routes={[{title: 'Нүүр', link: '/'}]}
        title='Сургалтын дэлгэрэнгүй'
        action={checkAuthRole('ROLE_OXFORD_THINKERS_MANAGE', session?.applicationRoles) && (
          <Button
            onClick={() => setModalOpen(true)}
            type='primary'
            icon={<PlusOutlined /> }
          >
            Хичээл бүртгэх
          </Button>
        )}
      />
      <Row gutter={25}>
        <Col xs={12} sm={12} md={5} lg={4}>
          {current ? (
            <div className={styles.itemHeader}>
              <div className={styles.image}>
                {
                  Array.isArray(current.image) && current.image.length > 0 ? (
                    <img
                      src={current.image[0].url}
                      alt={current.name}
                    />
                  ) : 'Байхгүй'
                }
              </div>
              <div className={styles.name}>Сургалтын нэр: <b>{current.name}</b></div>
              <div className={styles.type}>Сургалтын төрөл: <b>{current.type}</b></div>
            </div>
          ) : (
            <div className={styles.name}>Сургалтын мэдээлэл олдсонгүй</div>
          )}
        </Col>
        <Col xs={12} sm={12} md={19} lg={20}>
          <Form form={form} component={false}>
            <Table
              rowKey={(record) => record.id}
              size='middle'
              columns={mergedColumns}
              loading={loading}
              components={{
                body: {
                  cell: EditableCell,
                },
              }}
              dataSource={list}
              expandedRowKeys={expandedRowKeys}
              onExpand={(expanded, record) => handleExpandRow(record)}
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
              onChange={handleTableChange}
              locale={{emptyText: 'Хичээл бүртгээгүй байна'}}
              title={() => (
                <div>
                  Нийт хичээл: <b>{pagination?.total && numeral(pagination.total).format('0,0') || 0}</b>
                </div>
              )}
              expandable={{
                expandedRowRender: (record, _, __, expanded) => {
                  return (
                    <>
                      {expanded &&
                        <LessonList topicId={record?.id}/>
                      }
                    </>
                  )
                }
              }}
              // onRow={(record) => {
              //   return {
              //     onClick: () => handleExpandRow(record),
              //   }
              // }}
              className='custom-table'
            />
          </Form>
        </Col>
      </Row>
      <>
        {modalOpen && (
          <CreateTopic1
            modalOpen={modalOpen}
            onClose={() => setModalOpen(false)}
            handleCreate={handleCreate}
            handleUpdate={handleUpdate}
            editData={editData}
          />
        )}
        {modalOpenLesson && (
          <CreateLesson
            modalOpen={modalOpenLesson}
            onClose={() => setModalOpenLesson(false)}
            handleCreate={handleCreate1}
            handleUpdate={handleUpdate}
            editData={editData}
          />
        )}
        {modalOpenExercise && (
          <CreateExercise
            modalOpen={modalOpenExercise}
            onClose={() => setModalOpenExercise(false)}
            handleCreate={handleCreateExercise}
            handleUpdate={handleUpdate}
            editData={editData}
          />
        )}
        <div>
          {showExercise && (
            <Modal
              title='Дасгалын мэдээлэл'
              open={showExercise}
              onCancel={() => setShowExercise(false)}
              footer={null}
              width={800}
            >
              <Table
                columns={exerciseColumns}
                dataSource={exerciseStore.data.list}
                rowKey='id'
                size={'middle'}
              />
            </Modal>
          )}
        </div>
      </>
    </>
  )
})
export default CreateTopic