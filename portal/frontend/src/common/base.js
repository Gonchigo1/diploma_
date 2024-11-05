export function getLocale() {
  // TODO stores and impl later
  return 'mn'
}

export function getApiUrl() {
  return process.env.NEXT_PUBLIC_API_URL
}

export function getCdnUploadUrl() {
  return process.env.NEXT_PUBLIC_CDN_URL + '/upload'
}

export function getSocketUrl() {
  // console.log('bang2 ', process.env.NEXT_PUBLIC_SOCKET_URL)
  return process.env.NEXT_PUBLIC_SOCKET_URL
}