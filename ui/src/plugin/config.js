export const adminOrigin = location.origin.indexOf('localhost') !== -1
  ? '47.94.2.0:8080'
  : location.origin

export const appContext = {
  apiUrl: '/esview',
}

export const appApi = {
  menu: adminOrigin + appContext.apiUrl + '/oauth/source/left_menu_list',
  info: adminOrigin + appContext.apiUrl + '/oauth/user_info',
  logout: adminOrigin + appContext.apiUrl + '/oauth/logout'
}

