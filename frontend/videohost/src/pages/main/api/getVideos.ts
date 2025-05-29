import type { Ref } from 'vue'

const getVideos = async (id: string, showAllVideos: Ref, page: Ref) => {
  const url = showAllVideos.value
    ? `http://localhost:8080/api/video/all?page=${page.value}&size=${5}`
    : `http://localhost:8080/api/video/subscription/${id}?page=${page.value}&size=${5}`

  
  if (localStorage.getItem('token')) { 
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    })
  
    const data = await response.json()
    return data.content
    
  }else{
    const response = await fetch(url, {
      method: 'GET'    })
  
    const data = await response.json()
    return data.content
  }
  
}

export default getVideos
