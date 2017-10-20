export const adminOrigin = location.origin

export const appContext = {
  apiUrl: '/esview',
}

export const appApi = {
  menu: adminOrigin + appContext.apiUrl + '/auth/source/left_menu_list',
  logout: adminOrigin + appContext.apiUrl + '/auth/logout'
}

