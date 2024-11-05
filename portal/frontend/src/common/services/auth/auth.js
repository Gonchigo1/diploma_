import {getApiUrl} from '../../base'
import {jsonRequest} from '@util/request'

const baseUrl = '/auth'

export async function logIn(username, password) {
  return jsonRequest(`${getApiUrl()}${baseUrl}/login`, 'POST', JSON.stringify({username, password}))
}
