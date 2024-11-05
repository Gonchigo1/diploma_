import {useEffect, useState} from 'react'
import {useSession} from 'next-auth/react'
import {observer} from 'mobx-react-lite'
import {useRouter} from 'next/router'
import {Button, Popconfirm, Tooltip, Divider, message, Modal, Table,Form,Input} from 'antd'
import {EditTwoTone, DeleteTwoTone, PlusCircleTwoTone, EyeTwoTone,CloseOutlined,
  SaveOutlined} from '@ant-design/icons'

import {useStore} from '../../context/mobxStore'
import {checkAuthRole} from '../../common/util/auth'
import CreateExercise from '../../pages/course/[id]/createExercise'
import dynamic from 'next/dynamic'

const UpdateExercise = dynamic(() => import('./updateExercise')) 
const LessonList = observer(({topicId}) => {
  const [form] = Form.useForm()
  const router = useRouter()
  const {data: session} = useSession()
  const {id: bookId} = router.query
  const exerciseStore = useStore('exerciseStore')
  const lessonStore = useStore('lessonStore')
  const [editingKey, setEditingKey] = useState('')
  const {searchFormValues} = lessonStore
  const {pagination, loading} = lessonStore.data
  const [modalOpen, setModalOpen] = useState(false)
  const [currentExercise, setCurrentExercises] = useState(null)
  const [showExercise, setShowExercise] = useState(false)
  const [modalOpenExercise, setModalOpenExercise] = useState(false)
  const [_modalOpenLesson, setModalOpenLesson] = useState(false)
  const [data, setData] = useState([])
  const [editData, setEditData] = useState(false)

  useEffect(() => {
    if(session?.token && topicId)
      if(topicId)
        refreshTable()
  }, [topicId])

  const refreshTable = (params) => {
    const payload = {
      ...params,
      topicId: topicId
    }
    lessonStore.fetchList(session?.token, payload)
      .then(response => {
        if(response?.result) {
          setData(response.data?.list)
        }
      })
  }

  const handleLessonTableChange = (pagination, filters, sorter) => {
    const params = {
      ...searchFormValues,
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
      ...filters,
      ...sorter
    }
    lessonStore.setSearchFormValues(params)
    refreshTable(params)
  }

  const handleExerciseTableChange = (pagination, filters, sorter) => {
    const params = {
      ...filters,
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
      ...sorter
    }

    if (currentExercise) {
      const exercisePayload = {
        bookId: bookId,
        topicId: currentExercise?.topicId,
        lessonId: currentExercise?.lessonId,
        ...params
      }
      exerciseStore.fetchAll(session?.token, exercisePayload)
    }
  }

  const handleShowExercise = (showAction, data) => {
    if (showAction && data != null) {
      setCurrentExercises(data)
      setShowExercise(true)
      const payload = {
        bookId: bookId,
        topicId: data?.topicId,
        lessonId: data?.id
      }
      exerciseStore.fetchAll(session.token, payload)
    }
  }

  const handleDeleteLesson = async (token, id) => {
    try {
      await lessonStore.delete(token, id)
      message.success('Lesson deleted successfully')
      setShowExercise(false)
      refreshTable()
    } catch (error) {
      console.error('Error deleting lesson:', error)
    }
  }

  const isEditing = (record) => record.key === editingKey

  const edit = (record) => {
    form.setFieldsValue({lesson: record.lesson})
    setEditingKey(record.key)
  }
  
  const editExercise = (exercise) => {
    setEditData(exercise)
    setModalOpen(true) 
  }
  
  const handleDelete = async (token, id) => {
    try {
      await exerciseStore.delete(token, id)
      message.success('Дасгал амжилттай устгагдлаа')
      setShowExercise(false)
      refreshTable()

      if (currentExercise) {
        handleShowExercise(true, currentExercise)
      }
    } catch (error) {
      console.error('Дасгал устгахад алдаа гарлаа:', error)
      message.error('Дасгал устгахад алдаа гарлаа')
    }
  }

  const cancel = () => {
    setEditingKey('')
  }

  const handleUpdate = async (key) => {
    try {
      const row = await form.validateFields()
      const newData = [...data]
      const index = newData.findIndex((item) => key === item.key)

      if (index > -1) {
        const item = newData[index]
        const updatedItem = {...item, ...row}
        const payload = {id: updatedItem?.id, lesson: updatedItem?.lesson}
        console.log('payload', payload)
        const response = await lessonStore.update(payload, session?.token)
        if (response.result === true) {
          newData.splice(index, 1, updatedItem)
          lessonStore.data.list = newData
          setEditingKey('')
          message.success('Lesson updated successfully')
          refreshTable()
        } else {
          message.error('Failed to update lesson.')
        }
      }
    } catch (errInfo) {
      console.log('Validate Failed:', errInfo)
      message.error('Failed to update lesson.')
    }
  }

  const handleCreateExercise = (fields, form) => {
    const payload = {
      topicId: currentExercise.topicId,
      bookId,
      lessonId: currentExercise?.id,
      exercise:currentExercise?.id,
      audio:'',
      video:'',
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

  const exerciseColumns = [
    {
      title: '№',
      dataIndex: '',
      align: 'center',
      render: (_, __, index) => index + 1 + exerciseStore?.data?.pagination.currentPage * exerciseStore?.data?.pagination.pageSize,
      width: 50
    },
    {
      title: 'Дасгалын нэр',
      dataIndex: 'exercise',
      key: 'exercise',
      width: 140,
      editable: true,
    },
    ...(checkAuthRole('ROLE_OXFORD_THINKERS_MANAGE', session?.applicationRoles)
      ? [
        {
          title: 'Аудио',
          dataIndex: 'audio',
          width: 300,
          render: (audios) => {
            if (audios && audios.length > 0 && audios[0].url) {
              return (
                <div style={{display: 'flex', alignItems: 'center', justifyContent: 'space-between'}}>
                  <audio controls controlsList='nodownload'>
                    <source src={audios[0].url} type='audio/mp3' />
                    Таны веб хөтөч тухайн аудио тоглуулахыг дэмжихгүй байна.
                  </audio>
                </div>
              )
            }
            return <span>Аудио байхгүй</span>
          },
        },
        {
          title: 'Видео',
          dataIndex: 'video',
          width: 300,
          render: (videos) => {
            if (videos && videos.length > 0 && videos[0].url) {
              return (
                <div style={{display: 'flex', alignItems: 'center', justifyContent: 'space-between'}}>
                  <video controls style={{width: '100%', height: '140px'}} controlsList='nodownload'>
                    <source src={videos[0].url} type='video/mp4' />
                    Таны веб хөтөч тухайн видео тоглуулахыг дэмжихгүй байна.
                  </video>
                </div>
              )
            }
            return <span>Видео байхгүй</span>
          },
        },
        {
          title: 'Үйлдэл',
          width: 300,
          align: 'center',
          dataIndex: 'operation',
          render: (_, record) => {
            const editable = isEditing(record)

            return editable ? (
              <>
                <Button
                  icon={<SaveOutlined />}
                  onClick={() => {
                    handleUpdate(record.key)
                  }}
                  style={{color: '#34d300'}}
                >
                  Хадгалах
                </Button>
                <Divider type={'vertical'} />
                <Button
                  icon={<CloseOutlined />}
                  onClick={(e) => {
                    e.stopPropagation()
                    cancel()
                  }}
                  style={{color: '#e20000'}}
                >
                  Цуцлах
                </Button>
              </>
            ) : (
              <>
                {checkAuthRole('ROLE_OXFORD_THINKERS_MANAGE', session?.applicationRoles) ? (
                  <>
                    <Tooltip title='Засах'>
                      <Button
                        shape='circle'
                        disabled={editingKey !== ''}
                        onClick={() => {
                          editExercise(record)
                        }}
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

                  </>
                ) : null}
              </>
            )
          },
        },
      ]
      : []),
  ]

  const columns = [
    {
      title: '№',
      dataIndex: '',
      align: 'center',
      render: (_, __, index) => index + 1 + pagination.currentPage * pagination.pageSize,
      width: 50
    },
    {
      title: 'Сэдэв',
      dataIndex: 'lesson',
      key: 'lesson',
      editable: true,
      width: 200,
    },
    {
      title: 'Нийт дасгалын тоо',
      dataIndex: 'countExercises',
      key: 'lessonCount',
      align: 'center',
      editable: false,
      width: 160,
    },
    {
      title: 'Үйлдэл',
      dataIndex: 'operation',
      width: 300,
      render: (_, record) => {
        const editable = isEditing(record)

        return editable ? (
          <>
            <Button
              icon={<SaveOutlined /> }
              onClick={() => handleUpdate(record.key)}
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
          </>
        ) : (
          <>
            {checkAuthRole('ROLE_OXFORD_THINKERS_MANAGE', session?.applicationRoles) && (
              <>
                <Tooltip title='Засах'>
                  <Button
                    shape='circle'
                    disabled={editingKey !== ''}
                    onClick={() => edit(record)}
                    icon={<EditTwoTone />}
                  />
                </Tooltip>
                <Tooltip title='Устгах'>
                  <Divider type='vertical' />
                  <Popconfirm
                    title='Устгах уу?'
                    onConfirm={() => handleDeleteLesson(session?.token, record.key)}
                    okText='Тийм'
                    cancelText='Үгүй'
                  >
                    <Button
                      shape={'circle'}
                      icon={<DeleteTwoTone twoToneColor={'red'} />}
                      onClick={(event) => {
                        event.stopPropagation()
                      }}
                    />
                  </Popconfirm>
                </Tooltip>
                <Tooltip title={'Дасгал бүртгэх'}>
                  <Divider type='vertical' />
                  <Button
                    shape={'circle'}
                    onClick={() => {
                      setCurrentExercises(record)
                      setModalOpenExercise(true)
                    }}
                    type='default'
                    icon={<PlusCircleTwoTone />}
                  />
                </Tooltip>
              </>
            )}
            <Tooltip title='Дасгал харах'>
              <Divider type='vertical' />
              <Button
                shape={'circle'}
                onClick={() => {
                  handleShowExercise(true, record)
                }}
                icon={<EyeTwoTone />}
              />
            </Tooltip>
          </>
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
        inputType: 'text',
        dataIndex: col.dataIndex,
        title: col.title,
        editing: isEditing(record)
      })
    }
  })
  const handleUpdateExercise = async (updatedExercise, form) => {
    try {
      const response = await exerciseStore.update(session?.token, updatedExercise)
      if (response.result) {
        message.success('Дасгал амжилттай шинэчлэгдлээ')
        setModalOpen(false)
        setShowExercise(false)
        refreshTable()
      } else {
        message.error('Дасгал шинэчлэгдэхэд алдаа гарлаа')
      }
    } catch (error) {
      console.error('Error updating exercise:', error)
      message.error('Дасгал шинэчлэгдэхэд алдаа гарлаа')
    }
  }
  
  return (
    <>
      <Form form={form} component={false}>
        <Table
          loading={loading}
          components={{
            body: {
              cell: EditableCell
            }
          }}
          size='middle'
          columns={mergedColumns}
          dataSource={data || []}
          rowKey={(lessonRecord) => lessonRecord.id}
          onChange={handleLessonTableChange}
          className='side-table'
          locale={{emptyText: 'Хичээл бүртгээгүй байна'}}
          pagination={pagination ? {...pagination, showQuickJumper: true, showSizeChanger: true} : {showQuickJumper: true, showSizeChanger: true}}
        />
      </Form>
      {modalOpenExercise && (
        <CreateExercise
          modalOpen={modalOpenExercise}
          onClose={() => setModalOpenExercise(false)}
          handleCreate={handleCreateExercise}
        />
      )}
      {showExercise &&
        <Modal
          title='Дасгалын мэдээлэл'
          open={showExercise}
          onCancel={() => setShowExercise(false)}
          footer={null}
          width={900}
          maskClosable={false}
        >

          {modalOpen && (
            <UpdateExercise
              modalOpen={modalOpen}
              onClose={() => setModalOpen(false)}
              handleCreate={handleCreateExercise}
              handleUpdate={handleUpdateExercise}
              editData={editData}
            />
          )}
 
          {showExercise && (
            <div
              className='ant-table-content'
              onContextMenu={(e) => e.preventDefault()}
            >
              <Table
                loading={exerciseStore.loading}
                columns={exerciseColumns}
                dataSource={exerciseStore.data.list}
                rowKey='id'
                size={'middle'}
                className='custom-table'
                locale={{emptyText: 'Дасгал бүртгээгүй байна'}}
                onChange={handleExerciseTableChange}
                pagination={exerciseStore?.data?.pagination &&
                  {...exerciseStore?.data?.pagination, showQuickJumper: true, showSizeChanger: true} || []
                }
              />
            </div>
          )}
        </Modal>
      }
    </>
  )
})

const EditableCell = ({editing, dataIndex, title, children, ...restProps}) => {
  return (
    <td {...restProps}>
      {editing ? (
        <Form.Item
          name={dataIndex}
          style={{margin: 0}}
          rules={[{required: true, message: `${title}! бөглөнө үү`}]}
        >
          <Input />
        </Form.Item>
      ) : (
        children
      )}
    </td>
  )
}

export default LessonList