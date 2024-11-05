export function getFileUrlForForm(e) {
  if (Array.isArray(e)) {
    return e
  }
  return e && e.file && e.file.response && e.file.response.data
}

export function normFile(e) {
  if (Array.isArray(e)) {
    return e
  }
  return e && e.fileList
}

export function isImage(file) {
  return file.type.startsWith('image/')
}

// файл алдаагүй орсон эсэхийг шалгана
export function checkFileUpload (rule, value) {
  if (value && Array.isArray(value) && value.length > 0) {
    const hasError = value.some((file) => file.status !== 'done')

    if (hasError) {
      return Promise.reject(new Error('Файл оруулахад алдаа гарлаа. Дахин оролдон уу'))
    }

    return Promise.resolve()
  }
  return Promise.resolve()
}
