import {getApiUrl} from '../../base'
import {jsonRequest} from '../../util/request'
import {toQueryString} from '../../util/queryString'

const baseUrl = '/v1/banner'
export async function list(params) {
  return jsonRequest(getApiUrl() + `${baseUrl}?${toQueryString(params)}`, 'GET')
}
