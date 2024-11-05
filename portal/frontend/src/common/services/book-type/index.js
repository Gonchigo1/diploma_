import {getApiUrl} from '../../base'
import {jsonRequestWithToken} from '@util/request'
import {toQueryString} from '@util/queryString'

const baseUrl = '/book-type'

export async function getSelect(params, token) {
    return jsonRequestWithToken(getApiUrl() + `${baseUrl}/select?${toQueryString(params)}`, 'GET', token)
}

