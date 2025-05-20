import type { Ref } from 'vue'

const getVideos = async (id: string, showAllVideos: Ref) => {
  const url = showAllVideos.value
    ? 'http://localhost:8080/api/video/all'
    : `http://localhost:8080/api/video/subscription/${id}`

  
  if (localStorage.getItem('token')) { 
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    })
  
    return await response.json()
    
  }else{
    const response = await fetch(url, {
      method: 'GET'    })
  
    return await response.json()
  }
  
}

export default getVideos
